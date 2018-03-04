package terekhov.artemiy.testtinkoffnews.data.entities.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import terekhov.artemiy.testtinkoffnews.data.entities.DateEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.TitleEntity;

/**
 * Created by Artemiy Terekhov on 04.03.2018.
 */

public class TitleEntityDeserializer implements JsonDeserializer<TitleEntity> {
    @Override
    public TitleEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();

        TitleEntity entity = new TitleEntity();

        if (jObject.has(TitleEntity.SN_ID)) {
            entity.setId(jObject.get(TitleEntity.SN_ID).getAsString());
        }

        if (jObject.has(TitleEntity.SN_NAME)) {
            entity.setName(jObject.get(TitleEntity.SN_NAME).getAsString());
        }

        if (jObject.has(TitleEntity.SN_TEXT)) {
            entity.setText(jObject.get(TitleEntity.SN_TEXT).getAsString());
        }

        if (jObject.has(TitleEntity.SN_PUBLICATION_DATE)) {
            DateEntity dateEntity = new DateEntity();
            dateEntity.setDate(jObject.get(TitleEntity.SN_PUBLICATION_DATE).getAsLong());
            entity.setPublicationDate(dateEntity);
        }

        if (jObject.has(TitleEntity.SN_BANK_INFO_TYPE_ID)) {
            entity.setBankInfoTypeId(jObject.get(TitleEntity.SN_BANK_INFO_TYPE_ID).getAsLong());
        }

        return entity;
    }
}
