package terekhov.artemiy.testtinkoffnews.data.net.api;

import terekhov.artemiy.testtinkoffnews.BuildConfig;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface ApiBase {
    String APP_HOST = BuildConfig.REST_API_HOST;
    String NAMESPACE_V1 = "/v1/";
    String APP_HTTP_ABSOLUTE_URL_V1 = APP_HOST;
    String APP_HTTP_BASE_URL_V1 = APP_HTTP_ABSOLUTE_URL_V1 + NAMESPACE_V1;
}
