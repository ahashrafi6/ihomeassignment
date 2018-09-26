package amir.ashrafi.ihome.features.searchmovie;

import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import amir.ashrafi.ihome.utils.bases.IPresenter;
import amir.ashrafi.ihome.utils.bases.IView;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieSearchContract {

    interface View extends IView<Presenter> {

        void showMoreMovies(Pagination<Movie> tasks);

        void clearMovies();

        void showLoading();

        void showData();

        void showError(String error);
    }

    interface Presenter extends IPresenter<MovieSearchContract.View> {


        void onSearchButtonClick(String terms);

        void onLoadMoviesByTitle(String key, int page , String title);

    }
}
