package com.school_management.services.impl;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import com.school_management.models.dto.ScheduleDTO;
import com.school_management.models.entities.ScheduleEntity;
import com.school_management.repository.ScheduleRepository;
import com.school_management.services.IScheduleService;
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.ScheduleMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements IScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public List<ScheduleDTO> getAll() {
        List<ScheduleEntity> all = this.scheduleRepository.findAll();
        return all.stream().map(ScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO getById(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        ScheduleEntity schedule = this.scheduleRepository.findById(id).orElse(null);

        if(Objects.isNull(schedule)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        return ScheduleMapper.toDTO(schedule);
    }

    @Override
    public ScheduleDTO create(ScheduleDTO scheduleDTO) {
        if(Objects.isNull(scheduleDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        ScheduleEntity scheduleEntity = this.scheduleRepository.save(ScheduleMapper.toEntity(scheduleDTO));

        return ScheduleMapper.toDTO(scheduleEntity);
    }

    @Override
    public ScheduleDTO update(ScheduleDTO scheduleDTO) {

        if(Objects.isNull(scheduleDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        ScheduleEntity existing = scheduleRepository.findById(scheduleDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Aucun emploi du temps trouvé avec l’ID " + scheduleDTO.getId()));

        existing.setClasse(scheduleDTO.getClasse());
        existing.setJour(scheduleDTO.getJour());
        existing.setHeureDebut(scheduleDTO.getHeureDebut());
        existing.setHeureFin(scheduleDTO.getHeureFin());
        existing.setMatiere(scheduleDTO.getMatiere());
        existing.setEnseignant(scheduleDTO.getEnseignant());
        existing.setSalle(scheduleDTO.getSalle());

        ScheduleEntity updated = scheduleRepository.save(existing);
        return ScheduleMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.scheduleRepository.deleteById(id);
    }
}
