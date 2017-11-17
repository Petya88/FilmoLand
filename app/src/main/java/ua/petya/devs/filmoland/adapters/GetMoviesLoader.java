package ua.petya.devs.filmoland.adapters;

import android.content.Context;

import java.io.IOException;

import retrofit2.Call;
import ua.petya.devs.filmoland.loaders.BaseLoader;
import ua.petya.devs.filmoland.models.MoviePage;
import ua.petya.devs.filmoland.models.RequestResult;
import ua.petya.devs.filmoland.models.Response;
import ua.petya.devs.filmoland.retrofit.ApiFactory;
import ua.petya.devs.filmoland.retrofit.services.MoviesService;
import ua.petya.devs.filmoland.utils.Constants;


public class GetMoviesLoader extends BaseLoader {

    Integer pageId;

    public GetMoviesLoader(Context context, Integer pageId) {
        super(context);
        this.pageId = pageId;
    }

    @Override
    protected Response apiCall() throws IOException {
        MoviesService moviesService = ApiFactory.getMoviesService();
        Call<MoviePage> call = moviesService.getMoviePageById(Constants.API_KEY, pageId);
        MoviePage moviePage = null;
        try {
            moviePage = call.execute().body();
        } catch (Exception e){
            e.printStackTrace();
        }


        return new Response().setAnswer(moviePage).setRequestResult(RequestResult.SUCCESS);
    }
}
