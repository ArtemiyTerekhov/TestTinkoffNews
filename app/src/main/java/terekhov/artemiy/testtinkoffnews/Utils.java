package terekhov.artemiy.testtinkoffnews;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class Utils {
    public static boolean isRelease() {
        return BuildConfig.BUILD_TYPE.equals("release");
    }

    public static boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equals("debug");
    }
}
