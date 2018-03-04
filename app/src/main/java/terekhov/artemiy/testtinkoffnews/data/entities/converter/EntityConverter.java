package terekhov.artemiy.testtinkoffnews.data.entities.converter;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.objectbox.converter.PropertyConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.TitleEntity;

/**
 * Created by Artemiy Terekhov on 04.03.2018.
 */

public class EntityConverter<T> implements PropertyConverter<T, String> {
    private final GsonBuilder builder;

    public EntityConverter() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(TitleEntity.class, new TitleEntitySerializer());
        builder.registerTypeAdapter(TitleEntity.class, new TitleEntityDeserializer());
        builder.setPrettyPrinting();
    }

    @Override
    public T convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        Type type = new TypeToken<T>() {
        }.getType();
        return builder.create().fromJson(databaseValue, type);
    }

    @Override
    public String convertToDatabaseValue(T entityProperty) {
//        Type type = new TypeToken<T>() {
//        }.getType();
        return entityProperty == null ? null : builder.create().toJson(entityProperty);
    }
}
