package terekhov.artemiy.testtinkoffnews.data.net.news;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.net.api.ApiResponse;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface INewsAPI {
    String GET_NEWS = "news/";
    String GET_NEWS_CONTENT = "news_content/";

    String PARAM_QUERY_ID = "id";

    @GET(GET_NEWS)
    Observable<Response<ApiResponse<List<NewsEntity>>>> getNews();

    @GET(GET_NEWS_CONTENT)
    Observable<Response<ApiResponse<NewsContentEntity>>> getNewsContent(
            @Query(PARAM_QUERY_ID) String id);
}
