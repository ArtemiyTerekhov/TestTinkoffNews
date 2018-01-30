package terekhov.artemiy.testtinkoffnews.data.entities;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class NewsEntity extends BaseEntity implements Comparable<NewsEntity> {
    @SerializedName("id")
    @Expose
    @Index
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

    @Backlink
    private ToOne<NewsContentEntity> newsContentRelation;
    private ToOne<DateEntity> publicationDateRelation;

    public NewsEntity() {
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

        NewsEntity entity = (NewsEntity) obj;

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

    public ToOne<DateEntity> getPublicationDateRelation() {
        return publicationDateRelation;
    }

    public void setPublicationDateRelation() {
        publicationDateRelation.setTarget(publicationDate);
    }

    public ToOne<NewsContentEntity> getNewsContentRelation() {
        return newsContentRelation;
    }

    public void setNewsContentRelation(ToOne<NewsContentEntity> newsContentRelation) {
        this.newsContentRelation = newsContentRelation;
    }

    @Override
    public int compareTo(@NonNull NewsEntity o) {
        return id.compareTo(o.id);
    }

    public static int compare(@NonNull NewsEntity o1, @NonNull NewsEntity o2) {
        if (o1.getPublicationDate() == null || o2.getPublicationDate() == null) {
            return 0;
        }
        return o2.getPublicationDate().getDate().compareTo(o1.getPublicationDate().getDate());
    }

    public static List<NewsEntity> sort(@NonNull List<NewsEntity> src) {
        Collections.sort(src, NewsEntity::compare);
        return src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (!id.equals(that.id)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (publicationDate != null ? !publicationDate.equals(that.publicationDate) : that.publicationDate != null)
            return false;
        return bankInfoTypeId != null ? bankInfoTypeId.equals(that.bankInfoTypeId) : that.bankInfoTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (bankInfoTypeId != null ? bankInfoTypeId.hashCode() : 0);
        return result;
    }
}
