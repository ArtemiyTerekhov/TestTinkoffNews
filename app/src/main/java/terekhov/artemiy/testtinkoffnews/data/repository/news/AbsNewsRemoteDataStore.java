package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;

/**
 * Created by Artemiy Terekhov on 29.01.2018.
 */

public interface AbsNewsRemoteDataStore {
    Flowable<List<NewsEntity>> getNews();
    Single<NewsContentEntity> getNewsContent(@NonNull final String id);
}
