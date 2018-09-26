package amir.ashrafi.ihome.features.detailmovie;


import amir.ashrafi.ihome.features.searchmovie.MovieSearchContract;
import amir.ashrafi.ihome.pojo.DetailMovie;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.utils.bases.IPresenter;
import amir.ashrafi.ihome.utils.bases.IView;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieDetailContract {

    interface View extends IView<MovieSearchContract.Presenter> {

        void showMovieDetail(DetailMovie detailMovie);

        void showLoading();

        void showData();

        void showError(String error);
    }

    interface Presenter extends IPresenter<MovieDetailContract.View> {
        void onLoadMovieDetail(String id , String key);
    }
}
