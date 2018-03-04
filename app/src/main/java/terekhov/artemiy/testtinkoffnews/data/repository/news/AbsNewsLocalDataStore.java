package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.objectbox.reactive.DataObserver;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 29.01.2018.
 */

public interface AbsNewsLocalDataStore extends DataStore {
    Flowable<List<NewsEntity>> getNews();

    Observable<Boolean> saveNews(@NonNull final List<NewsEntity> news);

    Single<NewsContentEntity> getNewsContent(@NonNull final String id);

    Observable<Boolean> saveNewsContent(
            @NonNull String id, @NonNull final NewsContentEntity newsContentEntity);

    void registerNewsObserver(DataObserver<List<NewsEntity>> dataObserver);
    void registerNewsContentObserver(@NonNull String id, DataObserver<List<NewsContentEntity>> dataObserver);

    void unregisterNewsObserver();
    void unregisterNewsContentObserver();
}
