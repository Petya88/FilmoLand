package ua.petya.devs.filmoland.models;

import java.io.Serializable;
import java.util.ArrayList;


public class MoviePage implements Serializable {
    private Long page;
    private ArrayList<Movie> results;
    //private MovieDate dates;
    private Long total_pages;
    private Long total_results;

    public MoviePage() {
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    /*public MovieDate getDates() {
        return dates;
    }

    public void setDates(MovieDate dates) {
        this.dates = dates;
    }

    */

    public Long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Long total_pages) {
        this.total_pages = total_pages;
    }

    public Long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Long total_results) {
        this.total_results = total_results;
    }
}
