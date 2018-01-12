package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface NewsDataStore extends DataStore {
    String KEY_NEWS_RELATIONS = "key_news_relations" + KEY_DELIMITER;
    String KEY_NEWS_ENTITY = "key_news_entity" + KEY_DELIMITER;
    String KEY_NEWS_CONTENT_ENTITY = "key_news_content_entity" + KEY_DELIMITER;

    Observable<List<NewsEntity>> getNews();

    Observable<List<NewsEntity>> saveNews(@NonNull final List<NewsEntity> news);

    Observable<NewsContentEntity> getNewsContent(@NonNull final String id);

    Observable<NewsContentEntity> saveNewsContent(@NonNull String id,
            @NonNull final NewsContentEntity newsContentEntity);
}
