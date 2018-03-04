package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;

import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.mapper.NewsContentEntityDataMapper;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.news.LoadNewsContentUseCase;
import terekhov.artemiy.testtinkoffnews.domain.news.SyncNewsContentUseCase;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsContentPresenter implements NewsContentContact.Presenter {
    private static final String TAG = NewsContentPresenter.class.getSimpleName();

    private static final String KEY_STATE_ID = TAG.concat("key_state_id");

    private NewsContentContact.View mView;
    private String mId;
    private LoadNewsContentUseCase mLoadNewsContentUseCase;
    private SyncNewsContentUseCase mSyncNewsContentUseCase;
    private SchedulerProvider mSchedulerProvider;

    public NewsContentPresenter() {
        mSchedulerProvider = App.getAppComponent().schedulerProvider();
        mLoadNewsContentUseCase = new LoadNewsContentUseCase(mSchedulerProvider);
        mSyncNewsContentUseCase = new SyncNewsContentUseCase(mSchedulerProvider);
    }

    @Override
    public void subscribe(NewsContentContact.View view) {
        mView = view;

        mLoadNewsContentUseCase.getObservable(this::onError)
                .subscribe(this::onUpdate);

        mSyncNewsContentUseCase.getObservable(this::onError)
                .subscribe((result) -> {});

        fetchNewsContent();
    }

    @Override
    public void unsubscribe() {
        if (mLoadNewsContentUseCase != null) {
            mLoadNewsContentUseCase.unregisterObserver();
        }
        mView = null;
    }

    @Override
    public void restoreState(Bundle state) {
        if (state != null) {
            mId = state.getString(KEY_STATE_ID);
        }
    }

    @Override
    public void saveState(Bundle state) {
        if (state != null) {
            state.putString(KEY_STATE_ID, mId);
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
    public void setId(String id) {
        mId = id;
    }

    @Override
    public void fetchNewsContent() {
        showLoadingProgress();

        if (mLoadNewsContentUseCase != null) {
            mLoadNewsContentUseCase.registerObserver(
                    new LoadNewsContentUseCase.Request.Builder(mId).build());
        }
    }

    @Override
    public void testChangeItem() {
        mLoadNewsContentUseCase.testChangeItem();
    }

    public void syncNewsContent() {
        if (mSyncNewsContentUseCase != null) {
            mSyncNewsContentUseCase.execute(mId);
        }
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

    private void onError(Throwable throwable) {
        if (isViewBound()) {
            if (throwable instanceof MissingDataException) {
                syncNewsContent();
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

    private void onUpdate(NewsContentEntity content) {
        if (isViewBound()) {
            mView.update(NewsContentEntityDataMapper.transform(content));
        }
        hideLoadingProgress();
    }
}
