package terekhov.artemiy.testtinkoffnews.data.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class HeaderInterceptor implements Interceptor {
    private static final String USER_AGENT = "User-Agent";
    private String mUserAgent;

    public HeaderInterceptor(String userAgent) {
        mUserAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header(USER_AGENT, mUserAgent)
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        return chain.proceed(request);
    }
}
