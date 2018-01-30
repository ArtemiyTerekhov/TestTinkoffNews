package terekhov.artemiy.testtinkoffnews.domain.model;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class News {
    private String mId;
    private String mText;
    private long mDate;

    public News() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;

        News news = (News) o;

        if (getDate() != news.getDate()) return false;
        if (!getId().equals(news.getId())) return false;
        return getText().equals(news.getText());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getText().hashCode();
        result = 31 * result + (int) (getDate() ^ (getDate() >>> 32));
        return result;
    }

    public static List<News> sort(@NonNull List<News> src) {
        Collections.sort(src, (o1, o2) -> Long.compare(o2.getDate(), o1.getDate()));
        return src;
    }
}
