package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.model.News;
import terekhov.artemiy.testtinkoffnews.domain.news.GetNewsUseCase;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsPresenter implements NewsContract.Presenter {
    private static final String TAG = NewsPresenter.class.getSimpleName();

    private NewsContract.View mView;
    private GetNewsUseCase mGetNewsUseCase;
    private SchedulerProvider mSchedulerProvider;

    public NewsPresenter() {
        mSchedulerProvider = App.getAppComponent().schedulerProvider();
        mGetNewsUseCase = new GetNewsUseCase(mSchedulerProvider);
    }

    @Override
    public void subscribe(NewsContract.View view) {
        mView = view;

        mGetNewsUseCase.getObservable(this::onError)
                .subscribe(pair -> onUpdate(pair.first, pair.second));

        if (mView.getItemsCount() == 0) {
            fetchNews(false);
        }
    }

    @Override
    public void unsubscribe() {
        mView = null;
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
        fetchNews(true);
    }

    @Override
    public void fetchNews(boolean isRefresh) {
        showLoadingProgress();

        if (mGetNewsUseCase != null) {
            mGetNewsUseCase.execute(
                    new GetNewsUseCase.Request.Builder()
                            .clean(isRefresh).build());
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

    private void onUpdate(List<News> items, boolean isRefresh) {
        if (isViewBound()) {
            mView.updateList(items != null && !items.isEmpty()
                    ? items : Collections.emptyList(), isRefresh);
        }
        hideLoadingProgress();
    }
}
