/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package amir.ashrafi.ihome.interactors;


import javax.inject.Inject;

import amir.ashrafi.ihome.pojo.DetailMovie;
import io.reactivex.Observable;
import amir.ashrafi.ihome.interactors.remote.SearchMoviesApiService;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import amir.ashrafi.ihome.utils.SchedulerProvider;


public class MovieInteractor {

    private final SearchMoviesApiService searchMoviesApiService;
    private final SchedulerProvider scheduler;

    @Inject
    public MovieInteractor(SearchMoviesApiService searchMoviesApiService, SchedulerProvider scheduler) {
        this.searchMoviesApiService = searchMoviesApiService;
        this.scheduler = scheduler;
    }

    public Observable<Pagination<Movie>> getMoviesByTitle(String key, Integer page , String query) {
        return this.searchMoviesApiService.getMoviesByTitle(key, page , query)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    public Observable<DetailMovie> getMovieByID(String movieId , String key) {
        return this.searchMoviesApiService.getMovieById(movieId , key)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

}
