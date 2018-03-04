package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.mapper.NewsEntityDataMapper;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.model.News;
import terekhov.artemiy.testtinkoffnews.domain.news.LoadNewsUseCase;
import terekhov.artemiy.testtinkoffnews.domain.news.SyncNewsUseCase;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsPresenter implements NewsContract.Presenter {
    private static final String TAG = NewsPresenter.class.getSimpleName();

    private NewsContract.View mView;
    private LoadNewsUseCase mLoadNewsUseCase;
    private SyncNewsUseCase mSyncNewsUseCase;
    private SchedulerProvider mSchedulerProvider;

    public NewsPresenter() {
        mSchedulerProvider = App.getAppComponent().schedulerProvider();

        mSyncNewsUseCase = new SyncNewsUseCase(mSchedulerProvider);
        mLoadNewsUseCase = new LoadNewsUseCase(mSchedulerProvider);
    }

    @Override
    public void subscribe(NewsContract.View view) {
        mView = view;

        mLoadNewsUseCase.getObservable(this::onLoadError, null)
                .subscribe(this::onLoadNews);

        mSyncNewsUseCase.getObservable(this::onSyncError)
                .subscribe(this::onSyncNews);

        fetchNews(false);
    }

    private void onLoadNews(@NonNull List<NewsEntity> list) {
        if (list.isEmpty()) {
            showLoadingProgress();
            swipeToRefresh();
        } else {
            onUpdate(NewsEntityDataMapper.transform(NewsEntity.sort(list)), false);
        }
    }

    private void onSyncNews(@NonNull Boolean result) {
        hideLoadingProgress();
    }

    @Override
    public void unsubscribe() {
        mView = null;
        cancelSyncCountries();
    }

    public void cancelSyncCountries() {
        if (isViewBound()) {
            mView.loadingFinished();
        }
        if (mLoadNewsUseCase != null) {
            mLoadNewsUseCase.unregisterObserver();
        }
        if (mSyncNewsUseCase != null) {
            mSyncNewsUseCase.clear();
        }
    }

    @Override
    public void restoreState(Bundle state) {
        if (state != null) {
            //read from state
        }
    }

    @Override
    public void saveState(Bundle state) {
        if (state != null) {
            //save to state
        }
    }

    @Override
    public boolean isViewBound() {
        return mView != null;
    }

    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void destroy() {
        unsubscribe();
    }

    @Override
    public void swipeToRefresh() {
        mSyncNewsUseCase.execute(null);
    }

    @Override
    public void fetchNews(boolean isRefresh) {
        if (mLoadNewsUseCase != null) {
            mLoadNewsUseCase.registerObserver();
        }
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    private void showLoadingProgress() {
        if (isViewBound()) {
            mView.loadingStarted();
        }
    }

    private void hideLoadingProgress() {
        if (isViewBound()) {
            mView.loadingFinished();
        }
    }

    private void onLoadError(Throwable throwable) {
        if (isViewBound()) {
            if (throwable instanceof MissingDataException) {
                showLoadingProgress();
                swipeToRefresh();
                return;
            }
            // Don't check exception type in test app
            mView.showError(throwable.getMessage());
//            if (throwable instanceof NoConnectivityException
//                    || throwable instanceof ConnectException) {
//              todo
//            } else if (throwable instanceof ClientException) {
//                todo
//            } else {
//                todo
//            }
        }
        hideLoadingProgress();
    }

    private void onSyncError(Throwable throwable) {
        if (isViewBound()) {
            mView.showError(throwable.getMessage());
        }
        hideLoadingProgress();
    }

    private void onUpdate(List<News> items, boolean isRefresh) {
        if (isViewBound()) {
            mView.updateList(items != null && !items.isEmpty()
                    ? items : Collections.emptyList(), isRefresh);
        }
        hideLoadingProgress();
    }
}
