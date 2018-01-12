package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.R;
import terekhov.artemiy.testtinkoffnews.domain.model.News;
import terekhov.artemiy.testtinkoffnews.presentation.MainActivity;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BaseFragment;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.OnBackClickListener;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.PresenterProvider;
import terekhov.artemiy.testtinkoffnews.presentation.news.adapter.NewsAdapter;
import terekhov.artemiy.testtinkoffnews.presentation.news.adapter.OnItemClickListener;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsFragment extends BaseFragment<NewsContract.View, NewsContract.Presenter>
        implements NewsContract.View, OnBackClickListener, OnItemClickListener {
    public static final String TAG = NewsFragment.class.getName();

    @BindView(R.id.listOfNews)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsAdapter mAdapter;

    public static NewsFragment create(final String tagName, final PresenterProvider provider) {
        final NewsFragment fragment = new NewsFragment();
        fragment.setArguments(getBundle(tagName, provider));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        mButterKnifeUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        setDefaultActionBar((AppCompatActivity) getActivity());
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshNews);
    }

    @Override
    public void onDestroyView() {
        mButterKnifeUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public NewsContract.Presenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    public String getPresenterTag() {
        return TAG;
    }

    @Override
    public boolean onBackPressed() {
        destroyPresenter(this, MainActivity.TAG);
        return false;
    }

    private void initRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new NewsAdapter();
            mAdapter.setOnItemClickListener(this);
        }

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // item decorator don't use in test app
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshNews() {
        mPresenter.swipeToRefresh();
    }

    @Override
    public void loadingStarted() {
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadingFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void updateList(List<News> items, boolean isRefresh) {
        if (isRefresh && mAdapter.getItemCount() > 0) {
            mAdapter.removeAll();
        }
        mAdapter.updateWithDiff(items,
                mPresenter.getSchedulerProvider().computation(),
                mPresenter.getSchedulerProvider().ui());
    }

    @Override
    public int getItemsCount() {
        return mAdapter != null ? mAdapter.getItemCount() : 0;
    }

    @Override
    public void onItemClick(News item) {
        ((App) App.getAppComponent().app()).getNavigationManager()
                .navigateToNewsContentScreen(item.getId());
    }

    @Override
    protected void setDefaultActionBar(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setHomeAsUpIndicator(null);
        }
    }
}
