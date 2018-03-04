package terekhov.artemiy.testtinkoffnews.data.entities;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.DataEntityConverter;
import terekhov.artemiy.testtinkoffnews.data.entities.converter.EntityConverter;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Entity
public class NewsEntity extends BaseEntity implements Comparable<NewsEntity> {
    @SerializedName("id")
    @Index
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("text")
    private String text;
    @SerializedName("publicationDate")
    @Convert(converter = DataEntityConverter.class, dbType = Long.class)
    private DateEntity publicationDate;
    @SerializedName("bankInfoTypeId")
    private Long bankInfoTypeId;

    //@Backlink
    private ToOne<NewsContentEntity> newsContentRelation;

    public NewsEntity() {
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
        return Long.compare(o2.getPublicationDate().getDate(), o1.getPublicationDate().getDate());
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
