package terekhov.artemiy.testtinkoffnews.data.net.exception;

import terekhov.artemiy.testtinkoffnews.data.net.api.APIError;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class ServerException extends BaseException {
    public ServerException(APIError error) {
        super(error);
    }
}
