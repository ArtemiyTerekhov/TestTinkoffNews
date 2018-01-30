package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class NewsContentEntity extends BaseEntity {
    @SerializedName("title")
    @Expose
    @Transient
    private TitleEntity title;
    @SerializedName("creationDate")
    @Expose
    @Transient
    private DateEntity creationDate;
    @SerializedName("lastModificationDate")
    @Expose
    @Transient
    private DateEntity lastModificationDate;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("bankInfoTypeId")
    @Expose
    private Long bankInfoTypeId;
    @SerializedName("typeId")
    @Expose
    private String typeId;

    private ToOne<NewsEntity> newsRelation;

    private ToOne<TitleEntity> titleRelation;
    private ToOne<DateEntity> creationDateRelation;
    private ToOne<DateEntity> lastModificationDateRelation;

    public NewsContentEntity() {
        super();
    }

    public TitleEntity getTitle() {
        return title;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }

    public DateEntity getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateEntity creationDate) {
        this.creationDate = creationDate;
    }

    public DateEntity getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(DateEntity lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(Long bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public <T> void mergeWith(T obj) {
        super.mergeWith(obj);
        if (this == obj) {
            return;
        }

        NewsContentEntity entity = (NewsContentEntity) obj;

        if (entity.getTitle() != null) {
            if (title == null) {
                title = entity.getTitle();
            } else {
                title.mergeWith(entity.getTitle());
            }
        }

        if (entity.getCreationDate() != null) {
            if (creationDate == null) {
                creationDate = entity.getCreationDate();
            } else {
                creationDate.mergeWith(entity.getCreationDate());
            }
        }

        if (entity.getLastModificationDate() != null) {
            if (lastModificationDate == null) {
                lastModificationDate = entity.getLastModificationDate();
            } else {
                lastModificationDate.mergeWith(entity.getLastModificationDate());
            }
        }

        if (entity.getContent() != null) {
            content = entity.getContent();
        }

        if (entity.getTypeId() != null) {
            typeId = entity.getTypeId();
        }

        if (entity.getBankInfoTypeId() != null) {
            bankInfoTypeId = entity.getBankInfoTypeId();
        }
    }

    public ToOne<TitleEntity> getTitleRelation() {
        return titleRelation;
    }

    public ToOne<DateEntity> getCreationDateRelation() {
        return creationDateRelation;
    }

    public ToOne<DateEntity> getLastModificationDateRelation() {
        return lastModificationDateRelation;
    }

    public void setTitleRelation() {
        titleRelation.setTarget(title);
    }

    public void setCreationDateRelation() {
        creationDateRelation.setTarget(creationDate);
    }

    public void setLastModificationDateRelation() {
        lastModificationDateRelation.setTarget(lastModificationDate);
    }

    public ToOne<NewsEntity> getNewsRelation() {
        return newsRelation;
    }

    public void setNewsRelation(ToOne<NewsEntity> newsRelation) {
        this.newsRelation = newsRelation;
    }
}
