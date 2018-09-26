package amir.ashrafi.ihome.features.searchmovie;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieSearchPresenterModule {

    @Provides
    public MovieSearchContract.Presenter providePresenter(MovieSearchPresenter presenter) {
        return presenter;
    }

}
