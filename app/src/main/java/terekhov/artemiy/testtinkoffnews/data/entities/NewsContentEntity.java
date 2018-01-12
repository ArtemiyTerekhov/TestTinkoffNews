package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsContentEntity extends BaseEntity {
    @SerializedName("title")
    @Expose
    private TitleEntity mTitle;
    @SerializedName("creationDate")
    @Expose
    private DateEntity mCreationDate;
    @SerializedName("lastModificationDate")
    @Expose
    private DateEntity mLastModificationDate;
    @SerializedName("content")
    @Expose
    private String mContent;
    @SerializedName("bankInfoTypeId")
    @Expose
    private Long mBankInfoTypeId;
    @SerializedName("typeId")
    @Expose
    private String mTypeId;

    public NewsContentEntity() {
        super();
    }

    public TitleEntity getTitle() {
        return mTitle;
    }

    public void setTitle(TitleEntity title) {
        this.mTitle = title;
    }

    public DateEntity getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(DateEntity creationDate) {
        this.mCreationDate = creationDate;
    }

    public DateEntity getLastModificationDate() {
        return mLastModificationDate;
    }

    public void setLastModificationDate(DateEntity lastModificationDate) {
        this.mLastModificationDate = lastModificationDate;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public Long getBankInfoTypeId() {
        return mBankInfoTypeId;
    }

    public void setBankInfoTypeId(Long bankInfoTypeId) {
        this.mBankInfoTypeId = bankInfoTypeId;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        this.mTypeId = typeId;
    }

    @Override
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        NewsContentEntity entity = (NewsContentEntity) obj;

        if (entity.getTitle() != null) {
            if (mTitle == null) {
                mTitle = entity.getTitle();
            } else {
                mTitle.mergeWith(entity.getTitle());
            }
        }

        if (entity.getCreationDate() != null) {
            if (mCreationDate == null) {
                mCreationDate = entity.getCreationDate();
            } else {
                mCreationDate.mergeWith(entity.getCreationDate());
            }
        }

        if (entity.getLastModificationDate() != null) {
            if (mLastModificationDate == null) {
                mLastModificationDate = entity.getLastModificationDate();
            } else {
                mLastModificationDate.mergeWith(entity.getLastModificationDate());
            }
        }

        if (entity.getContent() != null) {
            mContent = entity.getContent();
        }

        if (entity.getTypeId() != null) {
            mTypeId = entity.getTypeId();
        }

        if (entity.getBankInfoTypeId() != null) {
            mBankInfoTypeId = entity.getBankInfoTypeId();
        }
    }
}
