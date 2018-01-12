package terekhov.artemiy.testtinkoffnews.presentation.mvp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class BaseActivity extends AppCompatActivity implements PresenterHolder {
    protected static final ButterKnife.Setter<View, Boolean> ENABLE
            = (view, value, index) -> view.setEnabled(value);

    private PresenterHolder mPresenterHolder;
    private PresenterHolderFragment mFragmentHolder;
    protected PresenterProvider mPresenterProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        mPresenterProvider = new PresenterProvider();
        setPresenterHolderFragment();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof PresenterHolder) {
            mPresenterHolder = (PresenterHolder) fragment;
        }
    }

    private void setPresenterHolderFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentHolder = (PresenterHolderFragment) fragmentManager.findFragmentByTag(PresenterHolderFragment.TAG);
        if (mFragmentHolder == null) {
            mFragmentHolder = new PresenterHolderFragment();

            fragmentManager.beginTransaction()
                    .add(mFragmentHolder, PresenterHolderFragment.TAG)
                    .commitNowAllowingStateLoss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    protected void setDefaultActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setSubtitle("");
            actionBar.setHomeAsUpIndicator(null);
        }
    }

    @Override
    public <T extends BasePresenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory, String tagPrefix) {
        return mPresenterHolder.getOrCreatePresenter(presenterFactory, tagPrefix);
    }

    @Override
    public void destroyPresenter(PresenterFactory<?> presenterFactory, String tagPrefix) {
        mPresenterHolder.destroyPresenter(presenterFactory, tagPrefix);
    }

    public static class PresenterProvider implements terekhov.artemiy.testtinkoffnews.presentation.mvp.PresenterProvider {
        private static final long serialVersionUID = -5986444973089471288L;

        @Override
        public BasePresenter<?> getPresenterOf(PresenterHolder holder, PresenterFactory factory, String tagName) {
            return holder.getOrCreatePresenter(factory, tagName);
        }
    }
}
