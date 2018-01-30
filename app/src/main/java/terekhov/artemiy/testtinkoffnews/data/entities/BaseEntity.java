package terekhov.artemiy.testtinkoffnews.data.entities;

import android.support.annotation.CallSuper;

import com.google.gson.Gson;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class BaseEntity {
    @Id (assignable = true)
    protected long primaryId;

    public static <T> String toJson(T object, Class<T> clazz) {
        return new Gson().toJson(object, clazz);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    @CallSuper
    public <T> void mergeWith(T obj) {
        if (this == obj) {
            return;
        }
        BaseEntity entity = (BaseEntity) obj;

        // save base data
    }

    public static <T extends BaseEntity> T merge(T saved, T input) {
        saved.mergeWith(input);
        return saved;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }
}
