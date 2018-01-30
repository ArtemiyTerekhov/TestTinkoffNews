package terekhov.artemiy.testtinkoffnews.presentation.news.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import terekhov.artemiy.testtinkoffnews.R;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private List<News> mItems = new ArrayList<>();
    private final CompositeDisposable mDisposables;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter() {
        mDisposables = new CompositeDisposable();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = mItems.get(position);
        holder.bind(news, getItemClickListener(news));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setNews(List<News> news) {
        mItems = news;
    }

    private static List<News> cloneList(List<News> list) {
        return (List<News>) ((ArrayList<News>)list).clone();
    }

    public void updateWithDiff(
            @NonNull List<News> items,
            @NonNull Scheduler subscribeScheduler,
            @NonNull Scheduler observeScheduler, Action completed) {
        Pair<List<News>, DiffUtil.DiffResult> initialPair = Pair.create(cloneList(mItems), null);
        addDisposable(Flowable.just(items)
                .onBackpressureLatest()
                .scan(initialPair, (pair, next) -> {
                    final NewsDiffCallback diffCallback = new NewsDiffCallback(pair.first, next);
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
                    return Pair.create(next, result);
                })
                .skip(1)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .doAfterTerminate(this::clear)
                .subscribe(diffResultPair -> {
                    addToBottom(diffResultPair.first, true);
                    diffResultPair.second.dispatchUpdatesTo(this);
                    if (completed != null) {
                        completed.run();
                    }
                }));
    }

    public void removeAll() {
        int count = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    private void clear() {
        mDisposables.clear();
    }

    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    private void addToBottom(@NonNull List<News> items, boolean clean) {
        if (clean) {
            mItems.clear();
        }
        mItems.addAll(items);
    }

    private void notifyItemClicked(News item) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(item);
        }
    }

    private View.OnClickListener getItemClickListener(final News item) {
        return view -> notifyItemClicked(item);
    }
}
