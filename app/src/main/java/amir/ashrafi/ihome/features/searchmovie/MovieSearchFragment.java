package amir.ashrafi.ihome.features.searchmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import amir.ashrafi.ihome.R;
import amir.ashrafi.ihome.features.detailmovie.MovieDetailFragment;
import amir.ashrafi.ihome.pojo.Movie;
import amir.ashrafi.ihome.pojo.Pagination;
import amir.ashrafi.ihome.utils.bases.BaseFragment;
import amir.ashrafi.ihome.utils.bases.FragmentInteractionListener;
import amir.ashrafi.ihome.utils.customviews.EndlessLinearLayoutRecyclerview;
import amir.ashrafi.ihome.utils.customviews.LoadingLayout;

import static amir.ashrafi.ihome.utils.Utility.isNotNullOrEmpty;


public class MovieSearchFragment extends BaseFragment implements MovieSearchContract.View, MovieSearchAdapter.ItemClickListener {


    private MovieSearchAdapter mListAdapter;

    @BindView(R.id.rvMovies)
    EndlessLinearLayoutRecyclerview rvMovies;

    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    private OnMovieSearchFragmentInteractionListener mListener;
    private AppCompatActivity activity;

    public String title = "a";
    public String key = "6600a7ed4544894c22041afe32b3a290";

    public int current_page = 1;

    @Inject
    public MovieSearchContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    public static MovieSearchFragment newInstance() {
        MovieSearchFragment fragment = new MovieSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mPresenter.onViewAttached(this);
        current_page = 1;

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("ihome Assignment");


        mListAdapter = new MovieSearchAdapter(getActivity(), new ArrayList<>());
        mListAdapter.setItemClickListener(this);

        rvMovies.setAdapter(mListAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setOnLoadMoreListener(() -> {
            mPresenter.onLoadMoviesByTitle(key, current_page , title);
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submitQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        RxTextView.textChanges(searchView.getRootView().findViewById(R.id.searchTextView))
                .filter(charSequence -> isNotNullOrEmpty(charSequence.toString()))
                .debounce(1000, TimeUnit.MILLISECONDS).map(CharSequence::toString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::submitQuery);

        mPresenter.onLoadMoviesByTitle(key, 1 , title);
    }

    private void submitQuery(String inputSearch) {
        current_page = 1;
        title = inputSearch;
        mPresenter.onSearchButtonClick(inputSearch);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);

        if (context instanceof Activity) {
            activity = (AppCompatActivity) context;
        }
        if (context instanceof OnMovieSearchFragmentInteractionListener) {
            mListener = (OnMovieSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


    }


    public interface OnMovieSearchFragmentInteractionListener extends FragmentInteractionListener {
    }

    public void clearMovies() {
        mListAdapter.clear();
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
    }


    public void showLoading() {
        loadinglayout.setState(LoadingLayout.STATE_LOADING);

    }

    public void showData() {
        if (loadinglayout.getState() != LoadingLayout.STATE_SHOW_DATA)
            loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
    }

    @Override
    public void showError(String error) {
        loadinglayout.setErrorText(error);
        loadinglayout.setErrorClickListener(() -> {
                    current_page = 1;
                    mPresenter.onSearchButtonClick(title);
                }
        );
    }

    @Override
    public void showMoreMovies(Pagination<Movie> movies) {
        int lastPage = movies.total_pages;
        if (movies.page != lastPage)
            rvMovies.setLoading(false);
        current_page++;
        for (Movie p : movies.results) {
            mListAdapter.addItem(p);
        }
    }


    @Override
    public void ItemClicked(int position, Movie item) {
        getNavigationManager().openFragment(MovieDetailFragment.newInstance(String.valueOf(item.id)), true);

    }
}
