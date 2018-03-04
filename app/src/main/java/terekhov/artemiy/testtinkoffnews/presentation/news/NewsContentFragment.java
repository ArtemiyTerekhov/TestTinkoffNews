package terekhov.artemiy.testtinkoffnews.presentation.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.R;
import terekhov.artemiy.testtinkoffnews.domain.model.NewsContent;
import terekhov.artemiy.testtinkoffnews.presentation.MainActivity;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BaseFragment;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.OnBackClickListener;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.PresenterProvider;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsContentFragment extends BaseFragment<NewsContentContact.View, NewsContentContact.Presenter>
        implements NewsContentContact.View, OnBackClickListener {
    public static final String TAG = NewsContentFragment.class.getName();

    @BindView(R.id.titleOfNews)
    TextView mTitleTextView;

    @BindView(R.id.dateOfNews)
    TextView mDateTextView;

    @BindView(R.id.contentOfNews)
    TextView mContentTextView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBarView;

    public static NewsContentFragment create(final String tagName, final PresenterProvider provider) {
        final NewsContentFragment fragment = new NewsContentFragment();
        fragment.setArguments(getBundle(tagName, provider));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_content, container, false);
        mButterKnifeUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultActionBar((AppCompatActivity) getActivity());
    }

    @Override
    public void onDestroyView() {
        mButterKnifeUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public NewsContentContact.Presenter createPresenter() {
        return new NewsContentPresenter();
    }

    @Override
    public String getPresenterTag() {
        return TAG;
    }

    @Override
    public boolean onBackPressed() {
        ((App) App.getAppComponent().app()).getNavigationManager().navigateToBack();
        destroyPresenter(this, MainActivity.TAG);
        return true;
    }

    @Override
    public void loadingStarted() {
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingFinished() {
        mProgressBarView.setVisibility(View.GONE);
    }

    @Override
    public void update(NewsContent content) {
        mTitleTextView.setText(Html.fromHtml(content.getTitle()));
        mDateTextView.setText(DateFormat.format("dd.MM.yyyy HH:mm:ss", content.getDate()));
        mContentTextView.setText(Html.fromHtml(content.getContent()));
        mContentTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mTitleTextView.setOnClickListener((view) -> {
            mPresenter.testChangeItem();
            showError("Text was changed");
        });
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
