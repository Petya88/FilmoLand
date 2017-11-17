package ua.petya.devs.filmoland.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.petya.devs.filmoland.MainActivity;
import ua.petya.devs.filmoland.R;
import ua.petya.devs.filmoland.models.Movie;
import ua.petya.devs.filmoland.utils.Constants;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ID_VIEW_TYPES_MOVIE_ITEM = 0;
    public static final int ID_VIEW_TYPES_BUTTON_MORE = 1;


    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MoviesAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @Override
    public int getItemViewType(int position) {

            if(movieArrayList.get(position).isAdapterMovieItem())
                return ID_VIEW_TYPES_MOVIE_ITEM;
            else
                return ID_VIEW_TYPES_BUTTON_MORE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType){
            case ID_VIEW_TYPES_MOVIE_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
                return new MovieViewHolder(v);
            case ID_VIEW_TYPES_BUTTON_MORE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
                return new ButtonViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Movie currentMovie = movieArrayList.get(position);
        switch (holder.getItemViewType()) {
            case ID_VIEW_TYPES_MOVIE_ITEM:
                MovieViewHolder movieViewHolder = (MovieViewHolder)holder;
                Picasso.with(context).load(Constants.URL_POSTER + currentMovie.getPoster_path()).into(movieViewHolder.movieImageView);
                movieViewHolder.movieImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openMovieByID(currentMovie);
                    }
                });
                break;
            case ID_VIEW_TYPES_BUTTON_MORE:
                ButtonViewHolder buttonViewHolder = (ButtonViewHolder)holder;
                buttonViewHolder.loadMoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadMore();
                    }
                });
                break;

        }

    }

    private void openMovieByID(Movie currentMovie) {
        ((MainActivity)context).openMovieById(currentMovie);
    }

    private void loadMore() {
        ((MainActivity)context).restartLoader();
    }


    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.movieImageView);
        }
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton loadMoreButton;
        public ButtonViewHolder(View itemView) {
            super(itemView);
            loadMoreButton = (AppCompatButton) itemView.findViewById(R.id.loadMoreButton);
        }
    }





    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }
}
