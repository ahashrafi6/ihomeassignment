package amir.ashrafi.ihome.interactors.remote;

import javax.inject.Named;

import amir.ashrafi.ihome.pojo.DetailMovie;
import io.reactivex.Observable;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface ApiInterface {

    //http://moviesapi.ir/api/v1/movies?q=[QUERY]
    @GET("search/movie")
    Observable<Pagination<Movie>> getMoviesByTitle(@Query("api_key") String key , @Query("page") Integer page , @Query("query") String query);

    //http://moviesapi.ir/api/v1/movies/{ID}
    @GET("movie/{id}")
    Observable<DetailMovie> getMovieById(@Path("id") String id , @Query("api_key") String key);
}
