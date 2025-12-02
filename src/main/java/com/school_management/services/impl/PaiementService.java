package com.school_management.services.impl;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.dto.SalaryDTO;
import com.school_management.models.entities.*;
import com.school_management.repository.*;
import com.school_management.services.IPaiementService;
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.PaiementMapper;
import com.school_management.utils.mappers.SalaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaiementService implements IPaiementService {

    private static final Logger logger = LoggerFactory.getLogger(PaiementService.class);
    private final PaiementRepository paiementRepository;
    private final StudentRepository studentRepository;
    private final SalaryRepository salaryRepository;
    public PaiementService(PaiementRepository paiementRepository, StudentRepository studentRepository,
                           SalaryRepository salaryRepository) {
        this.paiementRepository = paiementRepository;
        this.studentRepository = studentRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<PaiementDTO> getAll() {
        List<PaiementEntity> all = this.paiementRepository.findAll();
        return all.stream().map(PaiementMapper::toDto)
                    .collect(Collectors.toList());
    }

    @Override
    public PaiementDTO create(PaiementDTO paiementDTO) {
        if(Objects.isNull(paiementDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        StudentEntity studentEntity = this.studentRepository.findById(paiementDTO.getEleve()).orElse(null);

        if(Objects.isNull(studentEntity)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        // Collect et sommes des paiements déjà fait pour déterminer la somme restante
        List<PaiementEntity> paiements = studentEntity.getPaiements();
                //.stream()
                //.filter(p -> p.getDate().getYear() == LocalDate.now().getYear())
               // .toList();

        double totalPaiements = 0;
        if(!paiements.isEmpty()) {
            for (PaiementEntity paiementEntity : paiements) {
                String currentAcademicYear = getCurrentAcademicYear(paiementEntity.getDate());
                if(this.isAnneeScolaire(currentAcademicYear)) {
                    totalPaiements += paiementEntity.getMontant();
                }
            }
            totalPaiements+=paiementDTO.getMontant();
        }else{
            totalPaiements = paiementDTO.getMontant();
        }

        double montantRestant = studentEntity.getPrixScholarite() - totalPaiements;
        studentEntity.setResteApayer(montantRestant);

        studentEntity = this.studentRepository.save(studentEntity);

        PaiementEntity paiement = this.paiementRepository.save(PaiementMapper.toEntity(paiementDTO, studentEntity));

        return PaiementMapper.toDto(paiement);

    }


    @Override
    public SalaryDTO createSalary(SalaryDTO salaryDTO) {
        if(Objects.isNull(salaryDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        SalaryEntity salary = this.salaryRepository.save(SalaryMapper.toEntity(salaryDTO));

        if(Objects.isNull(salary)) {
            logger.error(Constants.NOT_SAVED_EXCEPTION);
            throw new NotFoundException(Constants.NOT_SAVED_EXCEPTION);
        }

        return SalaryMapper.toDto(salary);
    }

    @Override
    public List<SalaryDTO> getAllSalaries() {
        List<SalaryEntity> allSalaries = this.salaryRepository.findAll();
        return allSalaries.stream().map(SalaryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryDTO getSalaryByID(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        SalaryEntity salary = this.salaryRepository.findById(id).orElse(null);

        if(Objects.isNull(salary)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        return SalaryMapper.toDto(salary);
    }


    @Override
    public void delete(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.salaryRepository.deleteById(id);
    }

    @Override
    public SalaryDTO updateSalary(SalaryDTO salaryDTO) {
        if (salaryDTO.getId() == null) {
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        SalaryEntity existing = salaryRepository.findById(salaryDTO.getId())
                .orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND_EXCEPTION + salaryDTO.getId()));

        if (salaryDTO.getDate() != null) existing.setDate(salaryDTO.getDate());
        if (salaryDTO.getEmploye() != null) existing.setEmploye(salaryDTO.getEmploye());
        if (salaryDTO.getMontant() != null) existing.setMontant(salaryDTO.getMontant());
        if (salaryDTO.getMois() != null) existing.setMois(salaryDTO.getMois());
        if (salaryDTO.getPoste() != null) existing.setPoste(salaryDTO.getPoste());

        SalaryEntity saved = salaryRepository.save(existing);
        return SalaryMapper.toDto(saved);
    }

    @Override
    public double getSolde() {
        return (this.getSoldeBefore() - this.getAllDepense());
    }

    @Override
    public double getSoldeBefore(){
        List<PaiementEntity> allPaiements = this.paiementRepository.findAll();
        double solde = 0D;
        for(PaiementEntity paiementEntity : allPaiements) {
            String currentAcademicYear = this.getCurrentAcademicYear(paiementEntity.getDate());
            if(this.isAnneeScolaire(currentAcademicYear)) {
                solde += paiementEntity.getMontant();
            }
        }
        return solde;
    }

    @Override
    public double getRecettes() {
        double recette = 0.0;
        List<PaiementDTO> paiements = this.getAll();
        for(PaiementDTO paiement : paiements) {
            String currentAcademicYear = this.getCurrentAcademicYear(paiement.getDate());
            if(this.isAnneeScolaire(currentAcademicYear)) {
                if(paiement.getDate().getMonth().equals(LocalDate.now().getMonth()) &&
                        paiement.getDate().getYear() == LocalDate.now().getYear()) {
                    recette += paiement.getMontant();
                }
            }
        }
        return recette;
    }

    @Override
    public double getAllRecettes() {
        double recette = 0.0;
        List<PaiementDTO> paiements = this.getAll();
        for(PaiementDTO paiement : paiements) {
            String currentAcademicYear = this.getCurrentAcademicYear(paiement.getDate());
            if(this.isAnneeScolaire(currentAcademicYear)) {
                recette += paiement.getMontant();
            }
        }
        return recette;
    }

    @Override
    public double getDepenses() {
        double depenses = 0D;
        List<SalaryEntity> allDepenses = this.salaryRepository.findAll();
        for(SalaryEntity depense : allDepenses) {
            String currentAcademicYear = this.getCurrentAcademicYear(depense.getDate());
            if(this.isAnneeScolaire(currentAcademicYear)) {
                if(depense.getDate().getMonth().equals(LocalDate.now().getMonth()) &&
                        depense.getDate().getYear() == LocalDate.now().getYear()) {
                    depenses += depense.getMontant();
                }
            }
        }
        return depenses;
    }

    @Override
    public double getAllDepense() {
        double depenses = 0D;
        List<SalaryEntity> allDepenses = this.salaryRepository.findAll();
        for (SalaryEntity depense : allDepenses) {
            String currentAcademicYear = this.getCurrentAcademicYear(depense.getDate());
            if(this.isAnneeScolaire(currentAcademicYear)) {
                depenses += depense.getMontant();
            }
        }
        return depenses;
    }

    private boolean isAnneeScolaire(String anneeScolaire) {
        LocalDate today = LocalDate.now();

        // Sépare les deux années
        String[] parts = anneeScolaire.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format invalide. Utiliser 'YYYY - YYYY'.");
        }

        int startYear = Integer.parseInt(parts[0].trim());
        int endYear = Integer.parseInt(parts[1].trim());

        // Définition des bornes exactes
        LocalDate startDate = LocalDate.of(startYear, 9, 1);  // 1er septembre startYear
        LocalDate endDate = LocalDate.of(endYear, 8, 31);     // 31 août endYear

        // Vérifie l'appartenance
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    //obtenir l'année academique à partir de la date courante
    private static String getCurrentAcademicYear(LocalDate today) {
        int year = today.getYear();

        if (today.getMonthValue() < 9) {
            return (year - 1) + " - " + year;
        }

        return year + " - " + (year + 1);
    }


}
