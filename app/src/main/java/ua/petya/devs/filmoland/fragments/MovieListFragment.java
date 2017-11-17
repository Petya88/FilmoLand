package ua.petya.devs.filmoland.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ua.petya.devs.filmoland.MainActivity;
import ua.petya.devs.filmoland.R;
import ua.petya.devs.filmoland.adapters.GetMoviesLoader;
import ua.petya.devs.filmoland.adapters.MoviesAdapter;
import ua.petya.devs.filmoland.models.Movie;
import ua.petya.devs.filmoland.models.MoviePage;
import ua.petya.devs.filmoland.models.Response;


public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Response>{

    public static final String LOG_TAG = "MovieListFragment";
    RecyclerView moviesRecyclerView;
    ProgressBar progressBar;

    MoviesAdapter moviesAdapter;
    MoviePage moviePage;


    Context context;

    Integer page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View v = inflater.inflate(R.layout.fragment_movielist, container, false);

        setToolbarTitle(getString(R.string.latest_movies));
        setFirstToolbarTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);




        moviesRecyclerView = (RecyclerView) v.findViewById(R.id.moviesRecyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);


        page = 0;

        if (moviesAdapter!=null){
            page = 1;
            updateAdapter();
        }
        else
            restartLoader();

        return v;
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        showProgressBar();
        return new GetMoviesLoader(context, page) ;
    }

    public void restartLoader(){
        if (page>=1)
            moviesAdapter.getMovieArrayList().remove(moviesAdapter.getItemCount()-1);
        page++;
        getLoaderManager().restartLoader(0, getArguments(), this);
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        moviePage = data.getTypedAnswer();
        if (moviePage!=null){
            moviePage.getResults().add(new Movie(false));
            updateList(moviePage.getResults());
        }

        getLoaderManager().destroyLoader(loader.getId());

    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {

    }

    private void updateList(ArrayList<Movie> updatedList) {
        dismissProgressBar();

        if (moviesAdapter==null) {
            moviesAdapter = new MoviesAdapter(context, updatedList);
            updateAdapter();
        }else {
            moviesAdapter.getMovieArrayList().addAll(updatedList);
            moviesAdapter.notifyItemRangeChanged(0, moviesAdapter.getItemCount());
        }


    }



    public void updateAdapter(){
        dismissProgressBar();

        moviesRecyclerView.setAdapter(moviesAdapter);
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(moviesAdapter.getItemViewType(position)){
                    case MoviesAdapter.ID_VIEW_TYPES_MOVIE_ITEM:
                        return 1;
                    case MoviesAdapter.ID_VIEW_TYPES_BUTTON_MORE:
                        return 2;
                    default:
                        return -1;
                }

            }
        });
        moviesRecyclerView.setLayoutManager(glm);
        moviesAdapter.notifyDataSetChanged();
    }

    private void dismissProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void setToolbarTitle(String title){
        ((MainActivity)context).setToolbarTitle(title);
    }

    public void setFirstToolbarTitle(String title){
        ((MainActivity)context).setFirstToolbarTitle(title);
    }


}
