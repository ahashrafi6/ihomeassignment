package amir.ashrafi.ihome.features.detailmovie;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import amir.ashrafi.ihome.pojo.DetailMovie;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import amir.ashrafi.ihome.R;
import amir.ashrafi.ihome.utils.bases.BaseFragment;
import amir.ashrafi.ihome.utils.bases.FragmentInteractionListener;
import amir.ashrafi.ihome.utils.customviews.LoadingLayout;


public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {


    private String movie_id;
    public String key = "6600a7ed4544894c22041afe32b3a290";

    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.imPoster)
    ImageView imPoster;

    private OnMovieDtailFragmentInteractionListener mListener;
    private AppCompatActivity activity;

    @Inject
    public MovieDetailContract.Presenter mPresenter;


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(String movie_id) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString("movie_id", movie_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie_id = getArguments().getString("movie_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("");


        mPresenter.onViewAttached(this);
        mPresenter.onLoadMovieDetail(movie_id , key);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);

        if (context instanceof Activity) {
            activity = (AppCompatActivity) context;

        }

        if (context instanceof OnMovieDtailFragmentInteractionListener) {
            mListener = (OnMovieDtailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieDtailFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
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
        loadinglayout.setErrorClickListener(() -> mPresenter.onLoadMovieDetail(movie_id , key));
    }

    @Override
    public void showMovieDetail(DetailMovie detailMovie) {
        activity.getSupportActionBar().setTitle(detailMovie.original_title);

        tvOverview.setText(detailMovie.overview);

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w500" + detailMovie.backdrop_path.toString())
                .placeholder(R.drawable.placeholder)
                .into(imPoster);
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMovieDtailFragmentInteractionListener extends FragmentInteractionListener {
    }
}
