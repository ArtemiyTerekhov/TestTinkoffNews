package terekhov.artemiy.testtinkoffnews.data.db;

import com.snappydb.DB;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public final class RxSnappyClient extends BaseSnappyClient {
    public RxSnappyClient(DB db) {
        super(db);
    }

    public RxSnappyClient() {
        super(RxSnappy.mDatabase);
    }

    public Observable<Boolean> isCached(final String key) {
        return Observable.fromCallable(() -> isInCache(key));
    }

    public Observable<Boolean> exists(final String key) {
        return Observable.fromCallable(() -> existsKey(key));
    }

    public Observable<Boolean> getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Observable<Boolean> getBoolean(final String key, final Long cacheTime) {
        return Observable.fromCallable(() -> getBooleanValue(key, cacheTime));
    }

    public Observable<Boolean> setBoolean(String key, Boolean value) {
        return setBoolean(key, value, false);
    }

    public Observable<Boolean> setBoolean(final String key, final Boolean value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public Observable<String> getString(String key) {
        return getString(key, null);
    }

    public Observable<String> getString(final String key, final Long cacheTime) {
        return Observable.fromCallable(() -> getStringValue(key, cacheTime));
    }

    public Observable<String> setString(final String key, final String value) {
        return setString(key, value, false);
    }

    public Observable<String> setString(final String key, final String value, boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public Observable<Long> setLong(final String key, final Long value) {
        return setLong(key, value, false);
    }

    public Observable<Long> setLong(final String key, final Long value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public Observable<Long> getLong(String key) {
        return getLong(key, null);
    }

    public Observable<Long> getLong(final String key, final Long cacheTime) {
        return Observable.fromCallable(() -> getLongValue(key, cacheTime));
    }

    public Observable<Integer> setInteger(final String key, final Integer value) {
        return setInteger(key, value, false);
    }

    public Observable<Integer> setInteger(final String key, final Integer value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public Observable<Integer> getInteger(String key) {
        return getInteger(key, null);
    }

    public Observable<Integer> getInteger(final String key, final Long cacheTime) {
        return Observable.fromCallable(() -> getIntegerValue(key, cacheTime));
    }

    public Observable<List<String>> setStringList(final String key, final List<String> value) {
        return setStringList(key, value, false);
    }

    public Observable<List<String>> setStringList(final String key, final List<String> value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setStringListValue(key, value, ignoreCache));
    }

    public Observable<List<String>> getStringList(String key) {
        return getStringList(key, null);
    }

    public Observable<List<String>> getStringList(final String key, final Long cacheTime) {
        return Observable.fromCallable(() -> getStringListValue(key, cacheTime));
    }

    public <T> Observable<List<T>> setList(final String key, final List<T> value) {
        return setList(key, value, false);
    }

    public <T> Observable<List<T>> setList(final String key, final List<T> value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public <T> Observable<List<T>> getList(String key, Class<T> selectedClass) {
        return getList(key, null, selectedClass);
    }

    public <T> Observable<List<T>> getList(final String key, final Long cacheTime, final Class<T> selectedClass) {
        return Observable.fromCallable(() -> getObjectList(key, cacheTime, selectedClass));
    }

    public <T> Observable<T> setObject(final String key, final T value) {
        return setObject(key, value, false);
    }

    public <T> Observable<T> setObject(final String key, final T value, final boolean ignoreCache) {
        return Observable.fromCallable(() -> setValue(key, value, ignoreCache));
    }

    public <T> Observable<T> getObject(String key, Class<T> selectedClass) {
        return getObject(key, null, selectedClass);
    }

    public <T> Observable<T> getObject(final String key, final Long cacheTime, final Class<T> selectedClass) {
        return Observable.fromCallable(() -> getObjectValue(key, cacheTime, selectedClass));
    }

    public Observable<Boolean> deleteCache(final String key) {
        return Observable.fromCallable(() -> removeCacheForKey(key));
    }

    public Observable<String[]> findKeys(final String key) {
        return Observable.fromCallable(() -> fndKeys(key));
    }

    public Observable<Integer> countKeys(final String key) {
        return Observable.fromCallable(() -> cntKeys(key));
    }
}
