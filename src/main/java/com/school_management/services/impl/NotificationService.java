package com.school_management.services.impl;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.models.dto.NotificationDTO;
import com.school_management.models.entities.NotificationEntity;
import com.school_management.repository.NotificationRepository;
import com.school_management.services.INotificationService;
import com.school_management.utils.Constants;
import com.school_management.utils.mappers.NotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationDTO> getAll() {
        List<NotificationEntity> all = this.notificationRepository.findAll();
        return all.stream().map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO create(NotificationDTO notificationDTO) {
        if(Objects.isNull(notificationDTO)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        NotificationEntity newNotification = this.notificationRepository.save(NotificationMapper.toEntity(notificationDTO));

        return NotificationMapper.toDto(newNotification);
    }

    @Override
    public void delete(Long id) {
        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }
        this.notificationRepository.deleteById(id);
    }
}
