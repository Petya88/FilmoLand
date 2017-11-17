package ua.petya.devs.filmoland.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.petya.devs.filmoland.MainActivity;
import ua.petya.devs.filmoland.R;
import ua.petya.devs.filmoland.models.Movie;
import ua.petya.devs.filmoland.utils.Constants;


public class MovieItemFragment extends Fragment {

    public static final String LOG_TAG = "MovieItemFragment";
    Movie currentMovie;

    ImageView movieImageView, movieBackgroundImageView;
    TextView movieTitleTextView, movieDescriptionTextView;
    TextView scoreTextView, ratingTextView, releaseDateTextView;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentMovie = (Movie) bundle.getSerializable(Constants.MOVIE);

        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_item, container, false);

        context = container.getContext();

        movieImageView = (ImageView) v.findViewById(R.id.movieImageView);
        movieBackgroundImageView = (ImageView) v.findViewById(R.id.movieBackgroundImageView);

        movieTitleTextView = (TextView) v.findViewById(R.id.movieTitleTextView);
        movieDescriptionTextView = (TextView) v.findViewById(R.id.movieDescriptionTextView);

        scoreTextView = (TextView) v.findViewById(R.id.scoreTextView);
        ratingTextView = (TextView) v.findViewById(R.id.ratingTextView);
        releaseDateTextView = (TextView) v.findViewById(R.id.releaseDateTextView);

        if (currentMovie!=null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);

            setToolbarTitle(currentMovie.getTitle());
            setFirstToolbarTitle(getString(R.string.back));


            Picasso.with(context).load(Constants.URL_POSTER + currentMovie.getPoster_path()).into(movieImageView);
            Picasso.with(context).load(Constants.URL_POSTER + currentMovie.getBackdrop_path()).into(movieBackgroundImageView);

            movieTitleTextView.setText(currentMovie.getTitle());
            movieDescriptionTextView.setText(currentMovie.getOverview());

            scoreTextView.setText(String.valueOf(currentMovie.getVote_average()));
            ratingTextView.setText(String.valueOf(currentMovie.getVote_count()));
            releaseDateTextView.setText(currentMovie.getRelease_date());


        }

        return v;
    }

    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    public void setToolbarTitle(String title){
        ((MainActivity)context).setToolbarTitle(title);
    }

    public void setFirstToolbarTitle(String title){
        ((MainActivity)context).setFirstToolbarTitle(title);
    }

}
