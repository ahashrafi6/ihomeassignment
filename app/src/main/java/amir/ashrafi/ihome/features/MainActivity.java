package amir.ashrafi.ihome.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import amir.ashrafi.ihome.R;
import amir.ashrafi.ihome.features.detailmovie.MovieDetailFragment;
import amir.ashrafi.ihome.features.searchmovie.MovieSearchFragment;
import amir.ashrafi.ihome.utils.NavigationManager;
import amir.ashrafi.ihome.utils.bases.BaseActivity;
import amir.ashrafi.ihome.utils.bases.FragmentInteractionListener;
import amir.ashrafi.ihome.utils.bases.HasNavigationManager;

public class MainActivity extends BaseActivity implements HasNavigationManager,
        FragmentInteractionListener,
        MovieSearchFragment.OnMovieSearchFragmentInteractionListener,
        MovieDetailFragment.OnMovieDtailFragmentInteractionListener {


    private NavigationManager navigationManager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationManager = new NavigationManager(getSupportFragmentManager(), R.id.container);

        if (savedInstanceState == null) {
            navigationManager.openAsRoot(MovieSearchFragment.newInstance());
        }


    }


    @Override
    public NavigationManager provideNavigationManager() {
        return navigationManager;
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        this.currentFragment = fragment;
    }
}
