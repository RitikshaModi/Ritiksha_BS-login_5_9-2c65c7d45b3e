package com.example.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {

    private int total;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    private List<Photo> results = null;

    public List<Photo> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }
    public int getTotalPages(){
        return totalPages;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
