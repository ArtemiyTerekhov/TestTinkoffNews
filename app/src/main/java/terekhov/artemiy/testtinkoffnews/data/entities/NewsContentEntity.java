package terekhov.artemiy.testtinkoffnews.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.DataEntityConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.EntityConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.TitleEntityConverter;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class NewsContentEntity extends BaseEntity {
    @SerializedName("title")
    @Convert(converter = TitleEntityConverter.class, dbType = String.class)
    private TitleEntity title;
    @SerializedName("creationDate")
    @Convert(converter = DataEntityConverter.class, dbType = Long.class)
    private DateEntity creationDate;
    @SerializedName("lastModificationDate")
    @Convert(converter = DataEntityConverter.class, dbType = Long.class)
    private DateEntity lastModificationDate;
    @SerializedName("content")
    private String content;
    @SerializedName("bankInfoTypeId")
    private Long bankInfoTypeId;
    @SerializedName("typeId")
    private String typeId;

    //@Backlink
    private ToOne<NewsEntity> newsRelation;

    public NewsContentEntity() {
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

    public ToOne<NewsEntity> getNewsRelation() {
        return newsRelation;
    }

    public void setNewsRelation(ToOne<NewsEntity> newsRelation) {
        this.newsRelation = newsRelation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsContentEntity that = (NewsContentEntity) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null)
            return false;
        if (lastModificationDate != null ? !lastModificationDate.equals(that.lastModificationDate) : that.lastModificationDate != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (bankInfoTypeId != null ? !bankInfoTypeId.equals(that.bankInfoTypeId) : that.bankInfoTypeId != null)
            return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        return newsRelation != null ? newsRelation.equals(that.newsRelation) : that.newsRelation == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastModificationDate != null ? lastModificationDate.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (bankInfoTypeId != null ? bankInfoTypeId.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (newsRelation != null ? newsRelation.hashCode() : 0);
        return result;
    }
}
