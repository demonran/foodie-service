package com.foodie.portal.activity.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.foodie.portal.activity.model.ServiceScheduling;
import com.foodie.portal.commons.utils.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ServiceTimeConverter implements AttributeConverter<List<ServiceScheduling>, String> {

    @Override
    public String convertToDatabaseColumn(List<ServiceScheduling> attribute) {
        return JsonUtils.toJsonStr(attribute);
    }

    @Override
    public List<ServiceScheduling> convertToEntityAttribute(String dbData) {
        return JsonUtils.toBean(dbData, new TypeReference<List<ServiceScheduling>>() {
        });
    }
}
