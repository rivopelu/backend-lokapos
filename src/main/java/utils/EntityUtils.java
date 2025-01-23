package utils;


import com.lokapos.entities.BaseEntity;

import java.util.Date;
import java.util.UUID;

public class EntityUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
    public static <T extends BaseEntity> T created(T entity, String userId) {
        if (entity.getId() == null || entity.getId().isBlank())
            entity.setId(getUUID());
        if (entity.getCreatedBy() == null || entity.getCreatedBy().isBlank())
            entity.setCreatedBy(userId);
        if (entity.getCreatedDate() == null)
            entity.setCreatedDate(new Date().getTime());
        return entity;
    }


    public static <T extends BaseEntity> T updated(T entity, String userId) {
        entity.setUpdatedBy(userId);
        entity.setUpdatedDate(new Date().getTime());
        return entity;
    }
}


