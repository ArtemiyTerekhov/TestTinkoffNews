package terekhov.artemiy.testtinkoffnews.data.db.exception;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class KeyIsNullException extends RxSnappyException {
    public KeyIsNullException() {
        super("Cannot save null key!");
    }
}
