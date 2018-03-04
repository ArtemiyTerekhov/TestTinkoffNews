package terekhov.artemiy.testtinkoffnews.domain.news;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.UseCase;

/**
 * Created by Artemiy Terekhov on 30.01.2018.
 */

public class SyncNewsContentUseCase extends UseCase<Boolean, String> {
    private final NewsRepository mAppRepository;

    public SyncNewsContentUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = ((App) App.getAppComponent().app()).newsRepository();
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(String id) {
        return mAppRepository.syncNewsContent(id);
    }
}
