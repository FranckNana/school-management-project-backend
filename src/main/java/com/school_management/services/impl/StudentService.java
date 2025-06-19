package com.school_management.services.impl;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import com.school_management.models.dto.PersonnelDTO;
import com.school_management.models.dto.StudentDTO;
import com.school_management.models.entities.*;
import com.school_management.repository.StudentRepository;
import com.school_management.services.IStudentService;
import com.school_management.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        if(Objects.isNull(studentDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        String numeroMatricule = this.generateNumeroMatricule();
        StudentEntity studentEntity = StudentMapper.toEntity(studentDTO);
        studentEntity.setNumeroMatricule(numeroMatricule);

        StudentEntity saveStudent = this.studentRepository.save(studentEntity);

        return StudentMapper.toDto(saveStudent);
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        if(Objects.isNull(dto)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        StudentEntity existingEntity = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Étudiant introuvable avec l'ID : " + dto.getId()));

        // Mise à jour des champs simples
        if (dto.getNom() != null) existingEntity.setNom(dto.getNom());
        if (dto.getPrenom() != null) existingEntity.setPrenom(dto.getPrenom());
        if (dto.getDateNaissance() != null) existingEntity.setDateNaissance(dto.getDateNaissance());
        if (dto.getTelephone() != null) existingEntity.setTelephone(dto.getTelephone());
        if (dto.getEmail() != null) existingEntity.setEmail(dto.getEmail());
        if (dto.getClasse() != null) existingEntity.setClasse(dto.getClasse());
        if (dto.getAdresse() != null) existingEntity.setAdresse(dto.getAdresse());
        if (dto.getNumeroMatricule() != null) existingEntity.setNumeroMatricule(dto.getNumeroMatricule());
        if (dto.getNomParent() != null) existingEntity.setNomParent(dto.getNomParent());
        if (dto.getTelephoneParent() != null) existingEntity.setTelephoneParent(dto.getTelephoneParent());

        // Mise à jour des listes : on les remplace complètement
        if (dto.getNotes() != null) {
            existingEntity.getNotes().clear();
            dto.getNotes().forEach(noteDto -> {
                NoteEntity noteEntity = NoteMapper.toEntity(noteDto);
                noteEntity.setStudent(existingEntity); // important
                existingEntity.getNotes().add(noteEntity);
            });
        }

        if (dto.getPresences() != null) {
            existingEntity.getPresences().clear();
            dto.getPresences().forEach(presenceDto -> {
                PresenceEntity presence = PresenceMapper.toEntity(presenceDto);
                presence.setStudent(existingEntity);
                existingEntity.getPresences().add(presence);
            });
        }

        if (dto.getPaiements() != null) {
            existingEntity.getPaiements().clear();
            dto.getPaiements().forEach(paiementDto -> {
                PaiementEntity paiement = PaiementMapper.toEntity(paiementDto);
                paiement.setStudent(existingEntity);
                existingEntity.getPaiements().add(paiement);
            });
        }


        StudentEntity updatedStudent = this.studentRepository.save(existingEntity);
        return StudentMapper.toDto(updatedStudent);
    }

    @Override
    public StudentDTO getById(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        StudentEntity student = this.studentRepository.findById(id).orElse(null);

        if(Objects.isNull(student)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        return StudentMapper.toDto(student);
    }

    @Override
    public List<StudentDTO> getAll() {
        List<StudentEntity> all = this.studentRepository.findAll();
        return all.stream().map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.studentRepository.deleteById(id);
    }

    private String generateNumeroMatricule() {
        int currentYear = LocalDate.now().getYear();
        long countForYear = studentRepository.countByNumeroMatriculeStartingWith(currentYear + "-");
        return String.format("%d-%04d", currentYear, countForYear + 1);
    }
}
