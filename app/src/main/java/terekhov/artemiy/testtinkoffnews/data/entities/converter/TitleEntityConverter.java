package terekhov.artemiy.testtinkoffnews.data.entities.converter;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.objectbox.converter.PropertyConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.TitleEntity;

/**
 * Created by Artemiy Terekhov on 04.03.2018.
 */

public class TitleEntityConverter implements PropertyConverter<TitleEntity, String> {
    private final GsonBuilder builder;

    public TitleEntityConverter() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(TitleEntity.class, new TitleEntitySerializer());
        builder.registerTypeAdapter(TitleEntity.class, new TitleEntityDeserializer());
        builder.setPrettyPrinting();
    }

    @Override
    public TitleEntity convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        return builder.create().fromJson(databaseValue, TitleEntity.class);
    }

    @Override
    public String convertToDatabaseValue(TitleEntity entityProperty) {
        return entityProperty == null ? null : builder.create().toJson(entityProperty);
    }
}