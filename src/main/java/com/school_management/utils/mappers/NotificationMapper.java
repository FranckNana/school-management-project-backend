package com.school_management.utils.mappers;

import com.school_management.models.dto.NotificationDTO;
import com.school_management.models.entities.NotificationEntity;

public class NotificationMapper {

    public static NotificationDTO toDto(NotificationEntity item) {
        if (item == null) {
            return null;
        }

        return new NotificationDTO(
                item.getId(),
                item.getIcon(),
                item.getColor(),
                item.getImportance(),
                item.getTitle(),
                item.getDescription(),
                item.getDateTime()
        );
    }

    public static NotificationEntity toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        NotificationEntity item = new NotificationEntity();
        item.setId(dto.getId());          // si record, sinon dto.getId()
        item.setIcon(dto.getIcon());
        item.setColor(dto.getColor());
        item.setImportance(dto.getImportance());
        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());
        item.setDateTime(dto.getDateTime());
        return item;
    }
}
