package terekhov.artemiy.testtinkoffnews.presentation.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment
        implements PresenterFactory<P> {

    private static final String TAG_NAME = "tag_name";
    private static final String PRESENTER_PROVIDER = "presenter_provider";

    protected static final ButterKnife.Setter<View, Boolean> ENABLE
            = (view, value, index) -> view.setEnabled(value);

    protected P mPresenter;
    protected Unbinder mButterKnifeUnbinder;

    public static Bundle getBundle(final String tagName, final PresenterProvider provider) {
        final Bundle bundle = new Bundle();
        bundle.putString(TAG_NAME, tagName);
        bundle.putSerializable(PRESENTER_PROVIDER, provider);
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenterWithInit((PresenterHolder) getActivity());
    }

    @Override
    @CallSuper
    @SuppressWarnings("unchecked")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.subscribe((V) this);
        }
    }

    @Override
    @CallSuper
    @SuppressWarnings("unchecked")
    public void onStart() {
        super.onStart();
        if (mPresenter != null && !mPresenter.isViewBound() && getView() != null) {
            mPresenter.subscribe((V) this);
        }
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        if (mPresenter == null) {
            throw new IllegalStateException("Presenter can't be null!");
        }
        mPresenter.unsubscribe();
        super.onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        if (mPresenter.isViewBound()) {
            mPresenter.unsubscribe();
        }
        super.onDestroyView();
    }

    @SuppressWarnings("unchecked")
    public final P getPresenterWithInit(@Nullable PresenterHolder holder) {
        if (mPresenter != null) {
            return mPresenter;
        }
        final Bundle args = getArguments();
        if (args == null || !args.containsKey(TAG_NAME) || !args.containsKey(PRESENTER_PROVIDER)) {
            throw new IllegalStateException("Can't find TAG_NAME or PRESENTER_PROVIDER!");
        }
        if (holder == null) {
            holder = (PresenterHolder) getActivity();
            if (holder == null) {
                throw new IllegalStateException("PresenterHolder can't be null!");
            }
        }
        final String containerName = args.getString(TAG_NAME);
        final PresenterProvider<P> provider = (PresenterProvider<P>) args.getSerializable(PRESENTER_PROVIDER);
        mPresenter = provider.getPresenterOf(holder, this, containerName);
        return mPresenter;
    }

    protected final void destroyPresenter(PresenterFactory<?> factory, String tagPrefix) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).destroyPresenter(factory, tagPrefix);
        }
    }

    protected void setDefaultActionBar(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setHomeAsUpIndicator(null);
        }
    }
}
