package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class DateEntity {
    @SerializedName("milliseconds")
    @Expose
    private long date;

    public DateEntity() {
        date = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateEntity that = (DateEntity) o;

        return date == that.date;
    }

    @Override
    public int hashCode() {
        return (int) (date ^ (date >>> 32));
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
