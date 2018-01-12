package terekhov.artemiy.testtinkoffnews.presentation.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class PresenterHolderFragment extends Fragment implements PresenterHolder {
    public static final String TAG = PresenterHolderFragment.class.getName();
    public static final String STATE_PRESENTERS = TAG.concat(".presenters");

    // NOTE: Integer - reference counting for Presenters
    private final Map<String, Pair<BasePresenter<?>, Integer>> mPresenterMap = new HashMap<>();
    private Bundle mPresentersStates;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
        if (state != null) {
            mPresentersStates = state.getBundle(STATE_PRESENTERS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Bundle presentersStates = new Bundle();
        for (Map.Entry<String, Pair<BasePresenter<?>, Integer>> entry : mPresenterMap.entrySet()) {
            Bundle presenterState = new Bundle();
            Pair<BasePresenter<?>, Integer> pairValue = entry.getValue();
            pairValue.first.saveState(presenterState);
            presenterState.putInt(entry.getKey() + "###", pairValue.second);
            presentersStates.putBundle(entry.getKey(), presenterState);
        }
        state.putBundle(STATE_PRESENTERS, presentersStates);
    }

    @Override
    public void onDestroy() {
        for (Pair<BasePresenter<?>, Integer> presenterPair : mPresenterMap.values()) {
            presenterPair.first.destroy();
        }
        mPresenterMap.clear();
        super.onDestroy();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BasePresenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory, String tagPrefix) {
        String tag = tagPrefix + ":" + presenterFactory.getPresenterTag();
        Pair<BasePresenter<?>, Integer> presenterPair = mPresenterMap.get(tag);
        if (presenterPair == null) {
            T presenter = presenterFactory.createPresenter();
            int count = 1;
            if (mPresentersStates != null && mPresentersStates.containsKey(tag)) {
                Bundle presenterState = mPresentersStates.getBundle(tag);
                if (presenterState != null) {
                    count = presenterState.getInt(tag + "###", 1);
                    presenter.restoreState(presenterState);
                }
            }
            presenterPair = new Pair<>(presenter, count);
            mPresenterMap.put(tag, presenterPair);
            return presenter;
        } else {
            Pair<BasePresenter<?>, Integer> newPresenterPair =
                    new Pair<>(presenterPair.first, presenterPair.second + 1);
            mPresenterMap.remove(tag);
            mPresenterMap.put(tag, newPresenterPair);
            return (T) presenterPair.first;
        }
    }

    @Override
    public void destroyPresenter(PresenterFactory<?> presenterFactory, String tagPrefix) {
        String tag = tagPrefix + ":" + presenterFactory.getPresenterTag();
        Pair<BasePresenter<?>, Integer> presenterPair = mPresenterMap.get(tag);
        if (presenterPair != null) {
            int count = presenterPair.second - 1;
            if (count > 0) {
                mPresenterMap.put(tag, new Pair<>(presenterPair.first, presenterPair.second - 1));
                return;
            }
            presenterPair.first.destroy();
        }
        mPresenterMap.remove(tag);
    }
}
