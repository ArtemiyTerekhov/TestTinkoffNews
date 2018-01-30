package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class DateEntity extends BaseEntity {
    @SerializedName("milliseconds")
    @Expose
    private Long date;

    public DateEntity() {
        super();
    }

    public DateEntity(Long date) {
        super();
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        DateEntity entity = (DateEntity) obj;

        if (entity.getDate() != null) {
            date = entity.getDate();
        }
    }
}
