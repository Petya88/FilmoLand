package ua.petya.devs.filmoland;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ua.petya.devs.filmoland.fragments.MovieItemFragment;
import ua.petya.devs.filmoland.fragments.MovieListFragment;
import ua.petya.devs.filmoland.models.Movie;
import ua.petya.devs.filmoland.utils.Constants;
import ua.petya.devs.filmoland.utils.UtilsAPI;



public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Toolbar toolbar;
    TextView firstToolbarTitle, toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        firstToolbarTitle = (TextView) findViewById(R.id.first_toolbar_title);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle("");


        fragmentManager = getSupportFragmentManager();

        openFragment(new MovieListFragment(), MovieListFragment.LOG_TAG, true);

    }

    public void openFragment(final Fragment fragment, String LOG_TAG, boolean addToBackStack) {

        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.frame_container, fragment, LOG_TAG)
                .addToBackStack(fragment.getTag())
                .commitAllowingStateLoss();

        fragmentManager.executePendingTransactions();



    }

    public void openFragment(final Fragment fragment, String LOG_TAG) {
        openFragment(fragment, LOG_TAG, false);
    }

    public void restartLoader() {
        MovieListFragment movieListFragment = (MovieListFragment) fragmentManager.findFragmentByTag(MovieListFragment.LOG_TAG);
        if (movieListFragment!=null){
            movieListFragment.restartLoader();
        }
    }

    public void openMovieById(Movie currentMovie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MOVIE, currentMovie);

        MovieItemFragment movieItemFragment = new MovieItemFragment();
        movieItemFragment.setArguments(bundle);
        openFragment(movieItemFragment, MovieItemFragment.LOG_TAG);
    }




    @Override
    protected void onResume() {
        super.onResume();

        if (!UtilsAPI.isNetworkConnected(this)){
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void setToolbarTitle(String title){
        if (toolbarTitle!=null) {
            toolbarTitle.setText(title);
        }
    }

    public void setFirstToolbarTitle(String title){
        if (firstToolbarTitle!=null) {
            firstToolbarTitle.setText(title);
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        int count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            finish();
        }

    }

}
