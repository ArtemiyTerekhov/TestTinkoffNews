package terekhov.artemiy.testtinkoffnews.data.db;

import android.content.Context;

import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import timber.log.Timber;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public final class RxSnappy {
    private static final String TAG = RxSnappy.class.getSimpleName();
    static DB mDatabase;

    private RxSnappy() {
    }

    public static void init(Context context) {
        if (mDatabase == null) {
            try {
                mDatabase = DBFactory.open(context);
            } catch (SnappydbException e) {
                Timber.tag(TAG).e(e, "Failed to open database");
            }
        }
    }

    public static void init(Context context, Kryo kryo) {
        if (mDatabase == null) {
            try {
                mDatabase = DBFactory.open(context, kryo);
            } catch (SnappydbException e) {
                Timber.tag(TAG).e(e, "Failed to open database");
            }
        }
    }

    public static void closeDatabase() {
        if (mDatabase != null) {
            try {
                mDatabase.close();
            } catch (SnappydbException e) {
                Timber.tag(TAG).e(e, "Failed to close database");
            }
        }
    }

    public static void destroyDatabase() {
        if (mDatabase != null) {
            try {
                mDatabase.destroy();
                mDatabase = null;
            } catch (SnappydbException e) {
                Timber.tag(TAG).e(e, "Failed to destroy database");
            }
        }
    }

    public static void resetDatabase(Context context) {
        if (mDatabase != null) {
            try {
                mDatabase.destroy();
                mDatabase = null;
                init(context);
            } catch (SnappydbException e) {
                Timber.tag(TAG).e(e, "Failed to destroy database");
            }
        } else {
            Timber.tag(TAG).d("Database is null. Nothing to do.");
        }
    }
}
