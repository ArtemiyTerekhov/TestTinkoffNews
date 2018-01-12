package terekhov.artemiy.testtinkoffnews.data.net.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class ApiResponse<T> {
    @SerializedName("payload")
    @Expose
    private T mPayload;

    @SerializedName("resultCode")
    @Expose
    private String mResultCode;

    @SerializedName("trackingId")
    @Expose
    private String mTrackingId;

    public ApiResponse() {
    }

    public T getPayload() {
        return mPayload;
    }

    public void setPayload(T payload) {
        mPayload = payload;
    }

    public String getResultCode() {
        return mResultCode;
    }

    public void setResultCode(String resultCode) {
        mResultCode = resultCode;
    }

    public String getTrackingId() {
        return mTrackingId;
    }

    public void setTrackingId(String trackingId) {
        mTrackingId = trackingId;
    }
}
