package terekhov.artemiy.testtinkoffnews.data.net.exception;

import java.io.IOException;

import terekhov.artemiy.testtinkoffnews.data.net.api.APIError;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class BaseException extends IOException {
    private APIError mAPIError;

    public BaseException(APIError error) {
        mAPIError = error;
    }

    public APIError getError() {
        return mAPIError;
    }
}
