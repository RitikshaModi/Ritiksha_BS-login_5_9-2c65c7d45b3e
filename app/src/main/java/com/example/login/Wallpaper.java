package com.example.login;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Wallpaper extends Fragment {

    View view;
    private final String CLIENT_ID = "a27e1d8ce44cb1e933493b3a2a063748d0aeb8706cf9df85c095e7e9307ad0d1";
    // private Unslpash_interface photosApiService;
    Unslpash_interface dataService;
    private int page = 0;
    private RecyclerView mrecyclerView;
    private Unslpash_Adapter adapter;
    private ImageButton button_search;
    private EditText edt_Image_text;
    private Unslpash unslpash;
    private ImageView fullimgview;
    //private static final String BASE_URL = "https://api.unsplash.com/";
    private boolean isLastPage = false;
    private ProgressBar loader,loader_recyler;
    //private int load = 0;
    private static final String _FRAGMENT_STATE = "FRAGMENT_STAT";

    public Wallpaper() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        mrecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(mLayoutManager);

        button_search = view.findViewById(R.id.btn_search_images);
        edt_Image_text = view.findViewById(R.id.edttxt_submit);
        edt_Image_text.setEnabled(true);
        loader = view.findViewById(R.id.loader);
        loader_recyler = view.findViewById(R.id.loader_recylerview);

        fullimgview = view.findViewById(R.id.Img_splash);
        unslpash = new Unslpash(CLIENT_ID);

        dataService = unslpash.photosApiService;

        adapter = new Unslpash_Adapter();
        mrecyclerView.setAdapter(adapter);

        final String query = edt_Image_text.getText().toString();

        edt_Image_text.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            loadgetphotos();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        mrecyclerView.addOnScrollListener(new Infinite_scrollView(mLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("from Load More_load", "Hellohoware you");

                Log.d("scroll_listner","page"+page);
                if (query.isEmpty()) {
                    loadPhotos();
                }

                else{
                    getPhotos();
                }
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadgetphotos();
            }
        });

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;

    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getActivity().finish();
//                break;
//        }
//        return true;
//    }

    public void loadgetphotos(){
        //page = 0;
       // load++;
        Log.d("btn_click","page"+page);
        String query = edt_Image_text.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        loader.setVisibility(View.VISIBLE);
        if(isNetworkAvailable(getActivity().getApplicationContext())) {

            unslpash.searchPhotos(query, new Unslpash.OnSearchCompleteListener() {
                @Override
                public void onComplete(SearchResults results) {
                    Log.d("Photos", "Total Results Found " + results.getTotal());
                    List<Photo> photos = results.getResults();
                    loader.setVisibility(View.GONE);
                    adapter.setPhotos(photos);
                    //adapter.setPhotos((List<Photo>) new Image(new Integer(String.valueOf(photos))));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String error) {
                    loader.setVisibility(View.GONE);
                    Log.d("Unsplash", error);
                }
            });
        }
        else
        {
            loader.setVisibility(View.GONE);
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    private void getPhotos(){

        loader_recyler.setVisibility(View.VISIBLE);
        Log.d("get_photos_start","page"+page);
        page++;
        Log.d("get_photos_started","page"+page);

        dataService.getPhotos(page, null, "latest")
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {

                        Log.d("get_photos_onResponse","page"+page);
                        loader_recyler.setVisibility(View.GONE);
                        List<Photo> photos = response.body();
                        Log.d("Photos", "Photos Fetched_get_onscroll " + photos.size());
                        //add to adapter

                        adapter.addPhotos(photos);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {

                        loader_recyler.setVisibility(View.GONE);
                        Log.d("failed", "Failed in loading " + t.getMessage());
                    }
                });
    }

    private void loadPhotos() {

        loader_recyler.setVisibility(View.VISIBLE);
        Log.d("from Load Phtots_photos", "going to load more");

        String query = edt_Image_text.getText().toString();

        Log.d("Query","Q "+query);
        Log.d("load_photo_start","page"+page);
        if(page == 0)
            page=1;

        page++;
        Log.d("load_photo_stated","page"+page);

        unslpash.searchPhotos(query, page, null, null, new Unslpash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                // int TotalPages = results.getTotalPages();
                loader_recyler.setVisibility(View.GONE);
                Log.d("Total Pages","OnScroll"+ results.getTotalPages());
                Log.d("Photos", "Total Results Found_onscroll " + results.getTotal());
                List<Photo> photos = results.getResults();
                adapter.addPhotos(photos);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

    }

    public boolean isNetworkAvailable(Context context)
    {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}
