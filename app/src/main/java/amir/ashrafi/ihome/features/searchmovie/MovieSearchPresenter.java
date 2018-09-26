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

package amir.ashrafi.ihome.features.searchmovie;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import amir.ashrafi.ihome.interactors.MovieInteractor;
import amir.ashrafi.ihome.interactors.remote.exceptions.GeneralApiException;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import retrofit2.HttpException;

public class MovieSearchPresenter implements MovieSearchContract.Presenter {


    private MovieSearchContract.View viewLayer;
    private CompositeDisposable compositeDisposable;
    private final MovieInteractor mMovieInteractor;
    private static final String TAG = "MovieSearchPresenter";
    public String key = "6600a7ed4544894c22041afe32b3a290";

    @Inject
    public MovieSearchPresenter(MovieInteractor mMovieInteractor) {
        this.compositeDisposable = new CompositeDisposable();
        this.mMovieInteractor = mMovieInteractor;
    }


    Disposable disposable = null;

    @Override
    public void onLoadMoviesByTitle(String key , int page , String title) {
        if (page == 1) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            viewLayer.showLoading();
        }
        disposable =
                mMovieInteractor.getMoviesByTitle(key, page , title)
                        .subscribeWith(new DisposableObserver<Pagination<Movie>>() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    viewLayer.showError("StatusCode: " + ((HttpException) e).code());
                                } else if (e instanceof GeneralApiException) {
                                    viewLayer.showError(((GeneralApiException) e).message);
                                } else {
                                    viewLayer.showError(e.getMessage());
                                }
                            }

                            @Override
                            public void onNext(Pagination<Movie> movies) {
                                viewLayer.showData();
                                viewLayer.showMoreMovies(movies);
                            }
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onSearchButtonClick(String terms) {
        viewLayer.clearMovies();
      //  onLoadMoviesByTitle(terms, 1 , key);
    }


    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onViewAttached(MovieSearchContract.View view) {
        viewLayer = view;
    }
}
