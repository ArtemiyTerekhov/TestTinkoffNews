package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;

import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.model.NewsContent;
import terekhov.artemiy.testtinkoffnews.domain.news.GetNewsContentUseCase;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsContentPresenter implements NewsContentContact.Presenter {
    private static final String TAG = NewsContentPresenter.class.getSimpleName();

    private static final String KEY_STATE_ID = TAG.concat("key_state_id");

    private NewsContentContact.View mView;
    private String mId;
    private GetNewsContentUseCase mGetNewsContentUseCase;
    private SchedulerProvider mSchedulerProvider;

    public NewsContentPresenter() {
        mSchedulerProvider = App.getAppComponent().schedulerProvider();
        mGetNewsContentUseCase = new GetNewsContentUseCase(mSchedulerProvider);
    }

    @Override
    public void subscribe(NewsContentContact.View view) {
        mView = view;

        mGetNewsContentUseCase.getObservable(this::onError)
                .subscribe(this::onUpdate);

        fetchNewsContent();
    }

    @Override
    public void unsubscribe() {
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

        if (mGetNewsContentUseCase != null) {
            mGetNewsContentUseCase.execute(
                    new GetNewsContentUseCase.Request.Builder(mId).build());
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

    private void onUpdate(NewsContent content) {
        if (isViewBound()) {
            mView.update(content);
        }
        hideLoadingProgress();
    }
}
