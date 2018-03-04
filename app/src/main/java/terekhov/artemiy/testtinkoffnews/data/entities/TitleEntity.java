package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Transient;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.DataEntityConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.EntityConverter;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class TitleEntity {
    public static final String SN_ID = "id";
    public static final String SN_NAME = "name";
    public static final String SN_TEXT = "text";
    public static final String SN_PUBLICATION_DATE = "publicationDate";
    public static final String SN_BANK_INFO_TYPE_ID = "bankInfoTypeId";

    @SerializedName(SN_ID)
    private String id;
    @SerializedName(SN_NAME)
    private String name;
    @SerializedName(SN_TEXT)
    private String text;
    @SerializedName(SN_PUBLICATION_DATE)
    @Convert(converter = DataEntityConverter.class, dbType = Long.class)
    private DateEntity publicationDate;
    @SerializedName(SN_BANK_INFO_TYPE_ID)
    private Long bankInfoTypeId;

    public TitleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DateEntity getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateEntity publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(Long bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleEntity that = (TitleEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (publicationDate != null ? !publicationDate.equals(that.publicationDate) : that.publicationDate != null)
            return false;
        return bankInfoTypeId != null ? bankInfoTypeId.equals(that.bankInfoTypeId) : that.bankInfoTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (bankInfoTypeId != null ? bankInfoTypeId.hashCode() : 0);
        return result;
    }
}
