package terekhov.artemiy.testtinkoffnews.data.entities;

import android.support.annotation.CallSuper;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class BaseEntity {
    public static final String SN_PRIMARY_ID = "primaryId";

    @Id (assignable = true)
    @SerializedName(SN_PRIMARY_ID)
    protected long primaryId;

    public static <T> String toJson(T object, Class<T> clazz) {
        return new Gson().toJson(object, clazz);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }
}
