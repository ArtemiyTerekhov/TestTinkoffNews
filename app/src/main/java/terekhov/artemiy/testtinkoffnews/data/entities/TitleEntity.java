package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Transient;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class TitleEntity extends BaseEntity {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("publicationDate")
    @Expose
    @Transient
    private DateEntity publicationDate;
    @SerializedName("bankInfoTypeId")
    @Expose
    private Long bankInfoTypeId;

    public TitleEntity() {
        super();
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
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        TitleEntity entity = (TitleEntity) obj;

        if (entity.getId() != null) {
            id = entity.getId();
        }

        if (entity.getName() != null) {
            name = entity.getName();
        }

        if (entity.getText() != null) {
            text = entity.getText();
        }

        if (entity.getPublicationDate() != null) {
            if (publicationDate == null) {
                publicationDate = entity.getPublicationDate();
            } else {
                publicationDate.mergeWith(entity.getPublicationDate());
            }
        }

        if (entity.getBankInfoTypeId() != null) {
            bankInfoTypeId = entity.getBankInfoTypeId();
        }
    }
}
