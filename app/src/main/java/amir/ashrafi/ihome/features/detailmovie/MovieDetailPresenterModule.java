package amir.ashrafi.ihome.features.detailmovie;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieDetailPresenterModule {

    @Provides
    public MovieDetailContract.Presenter providePresenter(MovieDetailPresenter presenter) {
        return presenter;
    }

}
