package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;

/**
 * Created by Artemiy Terekhov on 29.01.2018.
 */

public interface AbsNewsLocalDataStore extends DataStore {
    Flowable<List<NewsEntity>> getNews();

    Completable saveNews(@NonNull final List<NewsEntity> news);

    Single<NewsContentEntity> getNewsContent(@NonNull final String id);

    Completable saveNewsContent(
            @NonNull String id, @NonNull final NewsContentEntity newsContentEntity);
}
