package ua.petya.devs.filmoland.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ua.petya.devs.filmoland.models.MoviePage;
import ua.petya.devs.filmoland.utils.Constants;



public interface MoviesService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(Constants.MOVIES_ENDPOINT)
    public Call<MoviePage> getMoviePageById(@Query("api_key") String api_key, @Query("page") Integer page);

}
