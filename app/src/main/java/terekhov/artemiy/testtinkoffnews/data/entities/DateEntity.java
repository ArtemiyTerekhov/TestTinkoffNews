package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class DateEntity extends BaseEntity {
    @SerializedName("milliseconds")
    @Expose
    private Long mDate;

    public DateEntity() {
        super();
    }

    public DateEntity(Long date) {
        super();
        mDate = date;
    }

    public Long getDate() {
        return mDate;
    }

    public void setDate(Long date) {
        this.mDate = date;
    }

    @Override
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        DateEntity entity = (DateEntity) obj;

        if (entity.getDate() != null) {
            mDate = entity.getDate();
        }
    }
}
