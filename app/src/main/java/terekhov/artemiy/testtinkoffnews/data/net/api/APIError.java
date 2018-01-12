package terekhov.artemiy.testtinkoffnews.data.net.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class APIError {
    private int mStatusCode;
    @SerializedName("error")
    private String mMessage;

    public APIError() {
    }

    public APIError(int statusCode) {
        mStatusCode = statusCode;
    }

    public APIError(int statusCode, String message) {
        mStatusCode = statusCode;
        mMessage = message;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
