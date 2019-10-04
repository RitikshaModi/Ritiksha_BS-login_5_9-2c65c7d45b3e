package com.example.login;


import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherApp extends Fragment {

    View view;
    ProgressBar Loader;
    TextView text_temp, text_humidity, text_presurre, txt_weathericon, detailsfield;
    EditText edt_searchcity;
    ImageButton btn_search, btn_cancel;

    public static String BaseUrl = "http://api.openweathermap.org/data/2.5/";
    public static final String OPEN_WEATHER_MAP_API = "8181d047d135d1fd3af29ebe6be71b2d";
    Typeface weatherFont;
    String city;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Api service = retrofit.create(Api.class);

    public WeatherApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather_app, container, false);
        text_temp = view.findViewById(R.id.txt_temp);
        text_humidity = view.findViewById(R.id.txt_humidity);
        text_presurre = view.findViewById(R.id.txt_pressure);
        edt_searchcity = view.findViewById(R.id.edttxt_searchcity);
        txt_weathericon = view.findViewById(R.id.weather_icon);
        btn_search = view.findViewById(R.id.btn_search_city);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        txt_weathericon.setTypeface(weatherFont);
        Loader = view.findViewById(R.id.loader);
        Loader.setVisibility(View.INVISIBLE);
        detailsfield = view.findViewById(R.id.details_field);

        edt_searchcity.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            searchcity();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchcity();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_searchcity.setText("");
                text_humidity.setText(" ");
                text_presurre.setText(" ");
                text_temp.setText(" ");
                txt_weathericon.setText(" ");
                detailsfield.setText(" ");
                Loader.setVisibility(View.GONE);
            }
        });
        return view;
    }

    public void searchcity(){

        text_humidity.setText(" ");
        text_presurre.setText(" ");
        text_temp.setText(" ");
        txt_weathericon.setText(" ");
        detailsfield.setText(" ");

        city = edt_searchcity.getText().toString();
        Log.e("city", city);

        if (city.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter city", Toast.LENGTH_LONG).show();
        } else {
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            taskLoadUp(city);
        }
    }

    public void taskLoadUp(String query) {
        if (isNetworkAvailable(getActivity().getApplicationContext())) {
            Log.e("msg", "taskLoadup");
            Loader.setVisibility(View.VISIBLE);

            Call<weatherresponse> call = service.getcurrentweatherdata(query, OPEN_WEATHER_MAP_API);
            Log.e("msg_apicall", "callAPI");

            call.enqueue(new Callback<weatherresponse>() {

                @Override
                public void onResponse(@NonNull Call<weatherresponse> call, @NonNull Response<weatherresponse> response) {

                    Log.e("msg", response.toString());
                    if (response.code() == 200) {
                        weatherresponse mainobject = response.body();
                        Log.e("msg", mainobject.toString());
                        assert mainobject != null;

                        text_temp.setText("Temperature:" + (String.format("%.2f", (mainobject.main.temp - 273.15)) + " °C"));
                        text_humidity.setText("Humidity: " + mainobject.main.humidity + " %");
                        text_presurre.setText("Pressure: " + mainobject.main.pressure + " hpa");
                        detailsfield.setText(mainobject.weather.get(0).description.toUpperCase());
                        txt_weathericon.setText(Html.fromHtml(setWeatherIcon(mainobject.weather.get(0).id, mainobject.sys.sunrise * 1000, mainobject.sys.sunset * 1000)));
                        Loader.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), "Error, Check City", Toast.LENGTH_LONG).show();
                        Loader.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<weatherresponse> call, Throwable t) {
                    Log.e("msg_apicall_faliure", "FAIL");
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch (id) {
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon = "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }

    public boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
