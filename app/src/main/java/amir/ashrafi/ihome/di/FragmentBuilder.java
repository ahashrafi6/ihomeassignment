package amir.ashrafi.ihome.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import amir.ashrafi.ihome.features.detailmovie.MovieDetailFragment;
import amir.ashrafi.ihome.features.detailmovie.MovieDetailPresenterModule;
import amir.ashrafi.ihome.features.searchmovie.MovieSearchFragment;
import amir.ashrafi.ihome.features.searchmovie.MovieSearchPresenterModule;

@Module
abstract class FragmentBuilder {


    @ContributesAndroidInjector(modules = {MovieDetailPresenterModule.class})
    abstract MovieDetailFragment contributeMovieDetailInjector();

    @ContributesAndroidInjector(modules = {MovieSearchPresenterModule.class})
    abstract MovieSearchFragment contributeMoviesSearchInjector();
}
