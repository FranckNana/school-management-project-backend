package com.school_management.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import com.school_management.models.dto.BulletinDTO;
import com.school_management.models.dto.NoteDTO;
import com.school_management.models.entities.BulletinEntity;
import com.school_management.models.entities.NoteEntity;
import com.school_management.models.entities.StudentEntity;
import com.school_management.repository.BulletinRepository;
import com.school_management.repository.StudentRepository;
import com.school_management.services.IBulletinService;
import com.school_management.services.ITokenService;
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.BulletinMapper;
import com.school_management.utils.mappers.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BulletinService implements IBulletinService {

    private static final Logger logger = LoggerFactory.getLogger(BulletinService.class);
    private final BulletinRepository bulletinRepository;
    private final StudentRepository studentRepository;
    private final ITokenService tokenService;

    @Value("${config.url.base.addr}")
    private String URL_BASE_ADDR;

    @Value("${server.port}")
    private String SERVER_PORT;

    private static final String IMAGE_SOURCE = "src/main/resources/images/";
    private static final String ENDPOINT_VERIFY_QRCODE = "/api/v1/bulletins/verify/";

    public BulletinService(BulletinRepository bulletinRepository, StudentRepository studentRepository, ITokenService tokenService) {
        this.bulletinRepository = bulletinRepository;
        this.studentRepository = studentRepository;
        this.tokenService = tokenService;
    }


    @Override
    public List<BulletinDTO> getAll() {
        List<BulletinEntity> all = this.bulletinRepository.findAll();
        return all.stream().map(BulletinMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BulletinDTO getById(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        BulletinEntity bulletin = this.bulletinRepository.findById(id).orElse(null);

        if(Objects.isNull(bulletin)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        return BulletinMapper.toDto(bulletin);
    }

    @Override
    public List<BulletinDTO> getByEleve(Long idEleve) {
        if(Objects.isNull(idEleve)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        List<BulletinEntity> bulletins = this.bulletinRepository.findByEleveId(idEleve).orElse(null);

        if(Objects.isNull(bulletins)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        List<BulletinDTO> listOfBulletins = bulletins.stream().map(b -> BulletinMapper.toDto(b))
                .collect(Collectors.toList());

        return listOfBulletins;
    }

    @Override
    public BulletinDTO create(BulletinDTO bulletinDTO) {
        if(Objects.isNull(bulletinDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        StudentEntity studentEntity = this.studentRepository.findById(bulletinDTO.getEleveId())
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_EXCEPTION));
        BulletinEntity bulletinEntity = BulletinMapper.toEntity(bulletinDTO);
        List<NoteEntity> notes = bulletinEntity.getNotes();

        for(NoteEntity note : notes) {
            note.setStudent(studentEntity);
            note.setTrimestre(bulletinDTO.getTrimestre());
            for(NoteDTO noteDTO : bulletinDTO.getNotes()) {
                if (noteDTO.getMatiere().equals(note.getMatiere())) {
                    note.setAppreciation(noteDTO.getAppreciation());
                }
            }
        }

        BulletinEntity bulletin = this.bulletinRepository.save(bulletinEntity);

        calculDeRang(bulletinDTO);

        return BulletinMapper.toDto(bulletin);
    }

    @Override
    public BulletinDTO update(BulletinDTO bulletinDTO) {
        if (bulletinDTO == null || bulletinDTO.getId() == null) {
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        StudentEntity studentEntity = this.studentRepository.findById(bulletinDTO.getEleveId())
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_EXCEPTION));

        BulletinEntity entity = bulletinRepository.findById(bulletinDTO.getId())
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_EXCEPTION));

        List<NoteEntity> notes = entity.getNotes();

        if (bulletinDTO.getTrimestre() != 0) {
            entity.setTrimestre(bulletinDTO.getTrimestre());
            for(NoteEntity note : notes) {
                note.setStudent(studentEntity);
                note.setTrimestre(bulletinDTO.getTrimestre());
            }
        }

        if (bulletinDTO.getAnnee() != null) {
            entity.setAnnee(bulletinDTO.getAnnee());
        }

        if (bulletinDTO.getAppreciation() != null) {
            entity.setAppreciation(bulletinDTO.getAppreciation());
        }

        if (bulletinDTO.getMoyenneGenerale() != 0) {
            entity.setMoyenneGenerale(bulletinDTO.getMoyenneGenerale());
        }

        if (bulletinDTO.getRang()!=0) {
            entity.setRang(bulletinDTO.getRang());
        }

        if (bulletinDTO.getDateGeneration() != null) {
            entity.setDateGeneration(bulletinDTO.getDateGeneration());
        }

        // Gérer les notes
        Map<Long, NoteDTO> existingNotesById = bulletinDTO.getNotes().stream()
                .filter(note -> note.getId() != null)
                .collect(Collectors.toMap(NoteDTO::getId, note -> note));

        List<NoteEntity> updatedNotes = new ArrayList<>();

        for (NoteDTO noteDTO : bulletinDTO.getNotes()) {
            if (noteDTO.getId() != null && existingNotesById.containsKey(noteDTO.getId())) {
                // Mettre à jour la note existante
                NoteEntity existingNote = NoteMapper.toEntity(existingNotesById.get(noteDTO.getId()));
                existingNote.setMatiere(noteDTO.getMatiere());
                existingNote.setNote(noteDTO.getNote());
                existingNote.setCoefficient(noteDTO.getCoefficient());
                existingNote.setAppreciation(noteDTO.getAppreciation());
                existingNote.setStudent(studentEntity);
                updatedNotes.add(existingNote);
                existingNotesById.remove(noteDTO.getId()); // Elle reste donc on la retire du "à supprimer"
            } else {
                // Nouvelle note
                NoteEntity newNote = new NoteEntity();
                newNote.setMatiere(noteDTO.getMatiere());
                newNote.setNote(noteDTO.getNote());
                newNote.setCoefficient(noteDTO.getCoefficient());
                newNote.setAppreciation(noteDTO.getAppreciation());
                newNote.setTrimestre(bulletinDTO.getTrimestre());
                newNote.setStudent(studentEntity);
                updatedNotes.add(newNote);
            }
        }

        // Supprimer les anciennes notes non présentes dans la mise à jour
        bulletinDTO.getNotes().removeIf(note -> existingNotesById.containsKey(note.getId()));

        // Mettre les notes à jour
        BulletinEntity bulletinEntity = BulletinMapper.toEntity(bulletinDTO);
        bulletinEntity.setNotes(updatedNotes);

        BulletinEntity updated = this.bulletinRepository.save(bulletinEntity);

        calculDeRang(bulletinDTO);

        return BulletinMapper.toDto(updated);

    }

    @Override
    public void delete(Long idBulletin) {
        if(Objects.isNull(idBulletin)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.bulletinRepository.deleteById(idBulletin);
    }

    @Override
    public byte[] generatePDF(Long bulletinId) {
        BulletinEntity bulletin = bulletinRepository.findById(bulletinId)
                .orElseThrow(() -> new NotFoundException("Bulletin non trouvé"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // -------------------------------
            // FONTS
            // -------------------------------
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            // -------------------------------
            // LOGO + INFORMATIONS ÉCOLE
            // -------------------------------
            PdfPTable logoTable = new PdfPTable(2);
            logoTable.setWidthPercentage(100);
            logoTable.setWidths(new int[]{2, 5});

            // --- Logo ---
            Image logo = Image.getInstance(this.IMAGE_SOURCE.concat("logo.png"));
            logo.scaleToFit(80, 80);
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            logoTable.addCell(logoCell);

            // --- Infos établissement ---
            Paragraph schoolInfo = new Paragraph(
                    "Collège de l'Avenir Saint Judicaël de Sonré (CASJS)\n" +
                            "Téléphone : +226 76 67 88 34\n" +
                            "Email : contact@casjs.com",
                    bodyFont
            );
            schoolInfo.setAlignment(Element.ALIGN_RIGHT);

            PdfPCell infoCell = new PdfPCell(schoolInfo);
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            logoTable.addCell(infoCell);

            document.add(logoTable);
            document.add(new Paragraph(" "));

            // -------------------------------
            // TITRE
            // -------------------------------
            Paragraph title = new Paragraph("Bulletin Scolaire", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // -------------------------------
            // INFOS ÉLÈVE + QR CODE
            // -------------------------------
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new int[]{6, 2}); // 6 pour infos, 2 pour QR Code

            // Colonne gauche : infos de l'élève
            Paragraph eleveInfo = new Paragraph();
            eleveInfo.add(new Phrase("Nom : " + bulletin.getNotes().stream()
                    .map(b -> b.getStudent().getNom()).findFirst().get() + "\n", bodyFont));
            eleveInfo.add(new Phrase("Prénom : " + bulletin.getNotes().stream()
                    .map(b -> b.getStudent().getPrenom()).findFirst().get() + "\n", bodyFont));
            eleveInfo.add(new Phrase("Classe : " + bulletin.getClasse() + "\n", bodyFont));
            eleveInfo.add(new Phrase("Trimestre : " + bulletin.getTrimestre() + "\n", bodyFont));
            eleveInfo.add(new Phrase("Année scolaire : " +
                    bulletin.getNotes().stream().map(b -> b.getStudent().getAnneeScolaire())
                            .findFirst().get() + "\n", bodyFont));
            eleveInfo.add(new Phrase("Date : " +
                    bulletin.getDateGeneration().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n", bodyFont));

            PdfPCell leftCell = new PdfPCell(eleveInfo);
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            infoTable.addCell(leftCell);

            // Colonne droite : QR Code
            String token = tokenService.generateToken(bulletinId);
            //System.out.println("TOKEN******* "+token);

            String url =URL_BASE_ADDR.concat(":").concat(SERVER_PORT).concat(ENDPOINT_VERIFY_QRCODE )+ bulletin.getId();
            url = url.concat("?t=").concat(token);
            Image qr = generateQRCode(url); // Méthode qui génère un Image QR Code
            qr.scaleToFit(100, 100);

            PdfPCell qrCell = new PdfPCell(qr);
            qrCell.setBorder(Rectangle.NO_BORDER);
            qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            qrCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            infoTable.addCell(qrCell);

            document.add(infoTable);
            document.add(new Paragraph(" "));

            // -------------------------------
            // TABLEAU DES NOTES
            // -------------------------------
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2, 4, 4});

            Stream.of("Matière", "Note", "Coefficient", "Appréciation", "Signature Professeur")
                    .forEach(header -> {
                        PdfPCell headerCell = new PdfPCell();
                        headerCell.setBackgroundColor(Color.LIGHT_GRAY);
                        headerCell.setPhrase(new Phrase(header, headerFont));
                        table.addCell(headerCell);
                    });

            for (NoteEntity note : bulletin.getNotes()) {
                table.addCell(new Phrase(note.getMatiere(), bodyFont));
                table.addCell(new Phrase(String.valueOf(note.getNote()), bodyFont));
                table.addCell(new Phrase(String.valueOf(note.getCoefficient()), bodyFont));
                table.addCell(new Phrase(note.getAppreciation(), bodyFont));

                PdfPCell signatureCell = new PdfPCell(new Phrase(" "));
                signatureCell.setFixedHeight(25);
                table.addCell(signatureCell);
            }

            document.add(table);

            // -------------------------------
            // MOYENNE + APPRÉCIATION GÉNÉRALE
            // -------------------------------
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Moyenne Générale : " + bulletin.getMoyenneGenerale(), bodyFont));
            document.add(new Paragraph("Appréciation : " + bulletin.getAppreciation(), bodyFont));
            document.add(new Paragraph("Rang : " +
                    bulletin.getRang() + "e sur " + getNbStudentByClass(bulletin), bodyFont));
            document.add(new Paragraph(" "));

            // -------------------------------
            // SIGNATURE DIRECTEUR + CACHET
            // -------------------------------
            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(100);
            signTable.setWidths(new int[]{3, 2});

            Paragraph signatureDirecteur = new Paragraph("Le Directeur:", headerFont);
            PdfPCell signTextCell = new PdfPCell(signatureDirecteur);
            signTextCell.setBorder(Rectangle.NO_BORDER);
            signTextCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            signTable.addCell(signTextCell);

            Image cachet = Image.getInstance(this.IMAGE_SOURCE.concat("cachet.png"));
            cachet.scaleToFit(100, 100);
            PdfPCell cachetCell = new PdfPCell(cachet);
            cachetCell.setBorder(Rectangle.NO_BORDER);
            cachetCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            signTable.addCell(cachetCell);

            document.add(signTable);

            // -------------------------------
            // FOOTER
            // -------------------------------
            ColumnText.showTextAligned(
                    writer.getDirectContent(),
                    Element.ALIGN_CENTER,
                    new Phrase("Bulletin généré automatiquement - © Collège de l'Avenir Saint Judicaël de Sonré (CASJS)", bodyFont),
                    PageSize.A4.getWidth() / 2,
                    20, // position en bas
                    0
            );

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        return out.toByteArray();
    }


    private void calculDeRang(BulletinDTO bulletinDTO) {
        List<BulletinEntity> bulletins = bulletinRepository.findAllByTrimestreOrderByMoyenneGeneraleDesc(bulletinDTO.getTrimestre());

        double prevMoyenne = -1;
        int rang = 0;

        for (BulletinEntity b : bulletins) {
            if (b.getMoyenneGenerale() != prevMoyenne) {
                rang++;
                prevMoyenne = b.getMoyenneGenerale();
            }
            b.setRang(rang);
        }

        this.bulletinRepository.saveAll(bulletins);
    }
    
    private int getNbStudentByClass(BulletinEntity bulletin) {
        return this.studentRepository.findAll().stream()
                .filter(s -> s.getClasse().equals(bulletin.getClasse()))
                .toList().size();
    }

    //obtenir l'année academique à partir de la date courante
    private static String getCurrentAcademicYear(LocalDate today) {
        int year = today.getYear();

        if (today.getMonthValue() < 9) {
            return (year - 1) + " - " + year;
        }

        return year + " - " + (year + 1);
    }

    private Image generateQRCode(String content) throws Exception {
        int size = 150;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size);

        ByteArrayOutputStream pngOutput = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutput);

        Image qrImage = Image.getInstance(pngOutput.toByteArray());
        qrImage.scaleToFit(100, 100);
        return qrImage;
    }
}
