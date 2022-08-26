package me.longli.demo.jpa.entity.converter;

import me.longli.demo.jpa.entity.sub.QualityLevelEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class QualityLevelConverter implements AttributeConverter<QualityLevelEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QualityLevelEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public QualityLevelEnum convertToEntityAttribute(Integer dbData) {
        return QualityLevelEnum.getByValue(dbData);
    }
}
