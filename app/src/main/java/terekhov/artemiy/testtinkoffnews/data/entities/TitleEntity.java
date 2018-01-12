package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class TitleEntity extends BaseEntity {
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("text")
    @Expose
    private String mText;
    @SerializedName("publicationDate")
    @Expose
    private DateEntity mPublicationDate;
    @SerializedName("bankInfoTypeId")
    @Expose
    private Long mBankInfoTypeId;

    public TitleEntity() {
        super();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public DateEntity getPublicationDate() {
        return mPublicationDate;
    }

    public void setPublicationDate(DateEntity publicationDate) {
        this.mPublicationDate = publicationDate;
    }

    public Long getBankInfoTypeId() {
        return mBankInfoTypeId;
    }

    public void setBankInfoTypeId(Long bankInfoTypeId) {
        this.mBankInfoTypeId = bankInfoTypeId;
    }

    @Override
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        TitleEntity entity = (TitleEntity) obj;

        if (entity.getId() != null) {
            mId = entity.getId();
        }

        if (entity.getName() != null) {
            mName = entity.getName();
        }

        if (entity.getText() != null) {
            mText = entity.getText();
        }

        if (entity.getPublicationDate() != null) {
            if (mPublicationDate == null) {
                mPublicationDate = entity.getPublicationDate();
            } else {
                mPublicationDate.mergeWith(entity.getPublicationDate());
            }
        }

        if (entity.getBankInfoTypeId() != null) {
            mBankInfoTypeId = entity.getBankInfoTypeId();
        }
    }
}
