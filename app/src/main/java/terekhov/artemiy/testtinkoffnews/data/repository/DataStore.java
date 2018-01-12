package terekhov.artemiy.testtinkoffnews.data.repository;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface DataStore {
    String KEY_DELIMITER = "#";

    int TYPE_REQUEST_LOCAL = 1;
    int TYPE_REQUEST_REMOTE = 2;

    @IntDef({TYPE_REQUEST_LOCAL, TYPE_REQUEST_REMOTE})
    @Retention(RetentionPolicy.SOURCE)
    @interface RequestType {
    }
}
