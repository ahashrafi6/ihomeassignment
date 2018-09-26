package amir.ashrafi.ihome.interactors.remote;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Named;

import amir.ashrafi.ihome.pojo.DetailMovie;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import amir.ashrafi.ihome.interactors.remote.exceptions.GeneralApiException;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import retrofit2.HttpException;


/**
 * Created by abbas on 7/18/16.
 */
public class SearchMoviesApiService {
    private final ApiInterface api;

    public SearchMoviesApiService(ApiInterface api) {
        this.api = api;
    }

    public Observable<Pagination<Movie>> getMoviesByTitle(String key, Integer page , String query) {
        return api.getMoviesByTitle(key, page , query)
                .compose(this.parseHttpErrors());
    }

    public Observable<DetailMovie> getMovieById(String id , String key) {
        return api.getMovieById(id , key)
                .compose(this.parseHttpErrors());
    }

    <T> ObservableTransformer<T, T> parseHttpErrors() {
        return observable -> observable.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            if (throwable instanceof HttpException) {

                Gson gson = new Gson();
                GeneralApiException generalApiException = null;
                try {
                    generalApiException = gson.fromJson(((HttpException) throwable).response().errorBody().string(), GeneralApiException.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (generalApiException == null)
                    return Observable.error(throwable);
                else
                    return Observable.error(generalApiException);
            }
            // if not the kind we're interested in, then just report the same error to onError()
            return Observable.error(throwable);
        });
    }
}
