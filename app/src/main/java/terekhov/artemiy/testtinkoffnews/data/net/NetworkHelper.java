package terekhov.artemiy.testtinkoffnews.data.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NetworkHelper {
    private static final String TAG = NetworkHelper.class.getSimpleName();

    public static String getUserAgent(Context context) {
        String userAgentTemplate = "TestTinkoffNews-%s-AndroidOs-%s";
        String buildVersion = Build.VERSION.RELEASE;
        String versionString = null;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionString = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            Timber.tag(TAG).e(ex, "Cannot find registered package for running application.");
        }

        return String.format(Locale.US, userAgentTemplate, versionString, buildVersion);
    }
}
