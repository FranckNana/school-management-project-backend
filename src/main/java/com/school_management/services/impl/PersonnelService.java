package com.school_management.services.impl;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import com.school_management.models.dto.PersonnelDTO;
import com.school_management.models.entities.PersonnelEntity;
import com.school_management.repository.PersonnelRepository;
import com.school_management.services.IPersonnelService;
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.PersonnelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonnelService implements IPersonnelService {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelService.class);
    private final PersonnelRepository personnelRepository;

    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public List<PersonnelDTO> getAll() {
        List<PersonnelEntity> all = this.personnelRepository.findAll();
        return all.stream().map(PersonnelMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public PersonnelDTO getById(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        PersonnelEntity personnel = this.personnelRepository.findById(id).orElse(null);

        if(Objects.isNull(personnel)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        return PersonnelMapper.toDTO(personnel);
    }

    @Override
    public PersonnelDTO create(PersonnelDTO personneldto) {
        if(Objects.isNull(personneldto)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        PersonnelEntity savePersonnel = this.personnelRepository.save(PersonnelMapper.toEntity(personneldto));

        return PersonnelMapper.toDTO(savePersonnel);
    }

    @Override
    public PersonnelDTO update(PersonnelDTO dto) {
        if(Objects.isNull(dto)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        PersonnelDTO existing = this.getById(dto.getId());

        if (dto.getNom() != null) existing.setNom(dto.getNom());
        if (dto.getPrenom() != null) existing.setPrenom(dto.getPrenom());
        if (dto.getDateNaissance() != null) existing.setDateNaissance(dto.getDateNaissance());
        if (dto.getTelephone() != null) existing.setTelephone(dto.getTelephone());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPoste() != null) existing.setPoste(dto.getPoste());
        if (dto.getDateEmbauche() != null) existing.setDateEmbauche(dto.getDateEmbauche());
        if (dto.getSalaire() != null) existing.setSalaire(dto.getSalaire());
        if (dto.getMatieres() != null) existing.setMatieres(dto.getMatieres());

        PersonnelEntity savePersonnel = this.personnelRepository.save(PersonnelMapper.toEntity(existing));
        return PersonnelMapper.toDTO(savePersonnel);
    }

    @Override
    public void delete(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.personnelRepository.deleteById(id);
    }

}
