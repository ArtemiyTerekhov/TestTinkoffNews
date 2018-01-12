package terekhov.artemiy.testtinkoffnews.presentation.news.adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsDiffCallback extends DiffUtil.Callback {
    private List<News> mCurrent;
    private List<News> mNext;

    public NewsDiffCallback(List<News> current, List<News> next) {
        this.mCurrent = current;
        this.mNext = next;
    }

    @Override
    public int getOldListSize() {
        return mCurrent.size();
    }

    @Override
    public int getNewListSize() {
        return mNext.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        News currentItem = mCurrent.get(oldItemPosition);
        News nextItem = mNext.get(newItemPosition);
        return currentItem.getId().equals(nextItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        News currentItem = mCurrent.get(oldItemPosition);
        News nextItem = mNext.get(newItemPosition);
        return currentItem.equals(nextItem);
    }
}
