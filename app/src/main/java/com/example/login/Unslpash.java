package com.example.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  Unslpash {

    public Unslpash_interface photosApiService;

    private static final String BASE_URL = "https://api.unsplash.com/";

    private String TAG = "Unsplash";


    public Unslpash(String clientId) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor(clientId)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        photosApiService = retrofit.create(Unslpash_interface.class);

    }

    public void getPhotos(Integer page, Integer perPage, Order order, final OnPhotosLoadedListener listener) {
        Call<List<Photo>> call = photosApiService.getPhotos(page, perPage, order.getOrder());
        call.enqueue(getMultiplePhotoCallback(listener));
    }

    public void searchPhotos(@NonNull String query, OnSearchCompleteListener listener) {
        searchPhotos(query, null, null, null, listener);
    }

    public void searchPhotos(@NonNull String query, @Nullable Integer page, @Nullable Integer perPage, @Nullable String orientation, OnSearchCompleteListener listener) {
        Call<SearchResults> call = photosApiService.searchPhotos(query, page, perPage, orientation);
        call.enqueue(getSearchResultsCallback(listener));
    }

    public Unslpash_interface getPhotosApiService() {
        return photosApiService;
    }

    public interface OnPhotosLoadedListener {
        void onComplete(List<Photo> photos);

        void onError(String error);
    }

    public interface OnSearchCompleteListener {
        void onComplete(SearchResults results);

        void onError(String error);
    }

    private Callback<List<Photo>> getMultiplePhotoCallback(final OnPhotosLoadedListener listener) {
        return new UnsplashCallback<List<Photo>>() {
            @Override
            void onComplete(List<Photo> response) {
                listener.onComplete(response);
            }

            @Override
            void onError(Call<List<Photo>> call, String message) {
                Log.d(TAG, "Url = " + call.request().url());
                listener.onError(message);
            }
        };
    }

    private Callback<SearchResults> getSearchResultsCallback(final OnSearchCompleteListener listener) {
        return new UnsplashCallback<SearchResults>() {
            @Override
            void onComplete(SearchResults response) {
                listener.onComplete(response);
            }

            @Override
            void onError(Call<SearchResults> call, String message) {
                listener.onError(message);
            }
        };
    }

    private abstract class UnsplashCallback<T> implements Callback<T> {

        abstract void onComplete(T response);

        abstract void onError(Call<T> call, String message);

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            int statusCode = response.code();
            Log.d(TAG, "Status Code = " + statusCode);
            if (statusCode == 200) {
                onComplete(response.body());
            } else if (statusCode >= 400) {
                onError(call, String.valueOf(statusCode));

                if (statusCode == 401) {
                    Log.d(TAG, "Unauthorized, Check your client Id");
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            onError(call, t.getMessage());
        }
    }
}
