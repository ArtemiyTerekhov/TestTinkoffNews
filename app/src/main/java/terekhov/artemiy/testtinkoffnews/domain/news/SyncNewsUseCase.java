package terekhov.artemiy.testtinkoffnews.domain.news;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.UseCase;

/**
 * Created by Artemiy Terekhov on 29.01.2018.
 */

public class SyncNewsUseCase extends UseCase<Boolean, Void> {
    private final NewsRepository mAppRepository;

    public SyncNewsUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = App.getAppComponent().newsRepository();
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return mAppRepository.syncNews().toObservable();
    }
}
