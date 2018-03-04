package terekhov.artemiy.testtinkoffnews.data.entities.converter;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.objectbox.converter.PropertyConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.DateEntity;

/**
 * Created by Artemiy Terekhov on 04.03.2018.
 */

public class DataEntityConverter implements PropertyConverter<DateEntity, Long> {
    public DataEntityConverter() {
    }

    @Override
    public DateEntity convertToEntityProperty(Long databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        DateEntity entity = new DateEntity();
        entity.setDate(databaseValue);
        return entity;
    }

    @Override
    public Long convertToDatabaseValue(DateEntity entityProperty) {
        return entityProperty == null ? null : entityProperty.getDate();
    }
}
