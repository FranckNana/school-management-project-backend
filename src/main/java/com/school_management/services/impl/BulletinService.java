package com.school_management.services.impl;

import com.lowagie.text.Font;
import com.lowagie.text.*;
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
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.BulletinMapper;
import com.school_management.utils.mappers.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BulletinService implements IBulletinService {

    private static final Logger logger = LoggerFactory.getLogger(BulletinService.class);
    private final BulletinRepository bulletinRepository;
    private final StudentRepository studentRepository;

    public BulletinService(BulletinRepository bulletinRepository, StudentRepository studentRepository) {
        this.bulletinRepository = bulletinRepository;
        this.studentRepository = studentRepository;
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

        if (bulletinDTO.getRang() != null) {
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

    public byte[] generatePDF(Long bulletinId) {
        BulletinEntity bulletin = bulletinRepository.findById(bulletinId)
                .orElseThrow(() -> new NotFoundException("Bulletin non trouvé"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph title = new Paragraph("Bulletin Scolaire", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // espace

            document.add(new Paragraph("Nom de l'élève : " + bulletin.getNotes()
                    .stream().map(b -> b.getStudent().getNom()).findFirst().get(), bodyFont));
            document.add(new Paragraph("Prénom de l'élève : " + bulletin.getNotes()
                    .stream().map(b -> b.getStudent().getPrenom()).findFirst().get(), bodyFont));
            document.add(new Paragraph("Classe : " + bulletin.getClasse(), bodyFont));
            document.add(new Paragraph("Trimestre : " + bulletin.getTrimestre(), bodyFont));
            document.add(new Paragraph("Année scolaire : " + bulletin.getAnnee(), bodyFont));
            document.add(new Paragraph("Date : " + bulletin.getDateGeneration().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), bodyFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2, 4});

            Stream.of("Matière", "Note", "Coefficient", "Appréciation")
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
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Moyenne Générale : " + bulletin.getMoyenneGenerale(), bodyFont));
            document.add(new Paragraph("Appréciation : " + bulletin.getAppreciation(), bodyFont));
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        return out.toByteArray();
    }
}
