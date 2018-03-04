package terekhov.artemiy.testtinkoffnews.data.entities.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import terekhov.artemiy.testtinkoffnews.data.entities.TitleEntity;

/**
 * Created by Artemiy Terekhov on 04.03.2018.
 */

public class TitleEntitySerializer implements JsonSerializer<TitleEntity> {
    @Override
    public JsonElement serialize(TitleEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        if (src.getId() != null) {
            obj.addProperty(TitleEntity.SN_ID, src.getId());
        }
        if (src.getName() != null) {
            obj.addProperty(TitleEntity.SN_NAME, src.getName());
        }
        if (src.getText() != null) {
            obj.addProperty(TitleEntity.SN_TEXT, src.getText());
        }
        if (src.getPublicationDate() != null && src.getPublicationDate() != null) {
            obj.addProperty(TitleEntity.SN_PUBLICATION_DATE, src.getPublicationDate().getDate());
        }
        if (src.getBankInfoTypeId() != null) {
            obj.addProperty(TitleEntity.SN_BANK_INFO_TYPE_ID, src.getBankInfoTypeId());
        }
        return obj;
    }
}
