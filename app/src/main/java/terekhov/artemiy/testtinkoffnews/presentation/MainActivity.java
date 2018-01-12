package terekhov.artemiy.testtinkoffnews.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.R;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BaseActivity;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.OnBackClickListener;
import terekhov.artemiy.testtinkoffnews.presentation.news.NewsContentContact;
import terekhov.artemiy.testtinkoffnews.presentation.news.NewsContentFragment;
import terekhov.artemiy.testtinkoffnews.presentation.news.NewsFragment;
import terekhov.artemiy.testtinkoffnews.presentation.routing.INavigation;
import terekhov.artemiy.testtinkoffnews.presentation.routing.base.BaseSupportActivityNavigation;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getName();

    private INavigation mNavigation = new BaseSupportActivityNavigation(
            getSupportFragmentManager(), R.id.content_layout) {
        @Override
        protected Fragment createFragment(String screenTag, Object param) {
            if (screenTag.equals(Screens.TAG_SCREEN_NEWS)) {
                NewsFragment fragment = NewsFragment
                        .create(MainActivity.TAG, MainActivity.this.mPresenterProvider);
                return fragment;
            } else if (screenTag.equals(Screens.TAG_SCREEN_NEWS_CONTENT)) {
                NewsContentFragment fragment = NewsContentFragment
                        .create(MainActivity.TAG, MainActivity.this.mPresenterProvider);
                NewsContentContact.Presenter presenter =
                        fragment.getPresenterWithInit(MainActivity.this);
                presenter.setId((String) param);
                return fragment;
            }
            return null;
        }

        @Override
        protected Intent createActivityIntent(String screenTag, Object param) {
            return null;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected ActivityOptionsCompat createActivityOptions(List<Pair<View, String>> transitions) {
            return null;
        }

        @Override
        protected Activity getActivityContext() {
            return MainActivity.this;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (savedInstanceState == null) {
            ((App) App.getAppComponent().app()).getNavigationManager().navigateToNewsScreen();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((App) App.getAppComponent().app()).subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((App) App.getAppComponent().app()).getNavigationManager().remove();
        ((App) App.getAppComponent().app()).unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((App) App.getAppComponent().app()).getNavigationManager().set(mNavigation);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_layout);
        if (!(fragment != null && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackPressed())) {
            finish();
        }
    }
}
