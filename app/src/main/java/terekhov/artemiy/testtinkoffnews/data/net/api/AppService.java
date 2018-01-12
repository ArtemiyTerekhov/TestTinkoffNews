package terekhov.artemiy.testtinkoffnews.data.net.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.Utils;
import terekhov.artemiy.testtinkoffnews.data.entities.BaseEntity;
import terekhov.artemiy.testtinkoffnews.data.net.NetworkHelper;
import terekhov.artemiy.testtinkoffnews.data.net.exception.ClientException;
import terekhov.artemiy.testtinkoffnews.data.net.exception.ResponseException;
import terekhov.artemiy.testtinkoffnews.data.net.exception.ServerException;
import terekhov.artemiy.testtinkoffnews.data.net.exception.UnexpectedException;
import terekhov.artemiy.testtinkoffnews.data.net.interceptor.HeaderInterceptor;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class AppService implements ApiBase {
    private static final String CACHE_DIR_NAME = "AllAboutMeHttpCacheDir";
    private static final int CACHE_DIR_SIZE = 10 * 1024 * 1024; // 10 Mb
    private static final int MAX_RETRIES = 1;
    private static final long RETRY_DELAY_MILLIS = 1000;

    private static final int TIMEOUT_CONNECT = 60;
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_WRITE = 60;

    private static final int CONNECTION_MAX = 5;
    private static final int CONNECTION_KEEP_ALIVE_DURATION = 5000;

    private static final String HTTP_LOG = "OkHttp";

    private final Application mApplication;
    private Retrofit mRetrofit;

    public AppService() {
        mApplication = App.getAppComponent().app();
    }

    protected Retrofit getRetrofit(String host) {
        if (mRetrofit == null) {
            mRetrofit = getRetrofitBuilder().baseUrl(host).build();
        }
        return mRetrofit;
    }

    private Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient());
    }

    private static Gson getGson() {
        Type collectionType = new TypeToken<List<BaseEntity>>() {
        }.getType();
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(collectionType, (JsonSerializer<List<?>>) (list, t, jsc) -> {
                    if (list.size() == 1) {
                        // Don't put single element lists in a json array
                        return new Gson().toJsonTree(list.get(0));
                    } else {
                        return new Gson().toJsonTree(list);
                    }
                })
                .create();
    }

    private OkHttpClient getHttpClient() {
        ConnectionPool connectionPool =
                new ConnectionPool(CONNECTION_MAX, CONNECTION_KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(connectionPool);
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        if (Utils.isDebug()) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.addInterceptor(new HeaderInterceptor(NetworkHelper.getUserAgent(mApplication)));

        builder.cache(getCache());
        return builder.build();
    }

    private Cache getCache() {
        File httpCacheDir = new File(mApplication.getApplicationContext().getCacheDir(), CACHE_DIR_NAME);
        return new Cache(httpCacheDir, CACHE_DIR_SIZE);
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<T> parseResponse(retrofit2.Response<ApiResponse<T>> response) {
        int code = response.code();
        String message = response.message();

        try {
            if (response.isSuccessful() /*code >= 200 && code < 300*/) {
                ApiResponse<T> result = ApiResponse.class.cast(response.body());
                T payload = result.getPayload();
                if (payload == null) {
                    return Observable.error(new ResponseException(
                            new APIError(code, message)));
                }

                return Observable.just(result.getPayload());
            } else if (code >= 400 && code < 500) {
                return Observable.error(new ClientException(
                        ErrorUtils.parseError(mRetrofit, response)));
            } else if (code >= 500 && code < 600) {
                return Observable.error(new ServerException(
                        ErrorUtils.parseError(mRetrofit, response)));
            } else {
                return Observable.error(new UnexpectedException(
                        ErrorUtils.parseError(mRetrofit, response)));
            }
        } catch (Exception e) {
            return Observable.error(new UnexpectedException(
                    ErrorUtils.parseError(mRetrofit, response)));
        }
    }
}
