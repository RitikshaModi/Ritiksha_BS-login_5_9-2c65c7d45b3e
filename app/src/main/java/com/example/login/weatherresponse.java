package com.example.login;

import java.util.ArrayList;

public class weatherresponse {

    public Coord coord;
    public Sys sys;
    public ArrayList<Weather> weather = new ArrayList<Weather>();
    public Main main;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public float dt;
    public int id;
    public String name;
    public float cod;
}

class Weather {

    public int id;
    public String main;
    public String description;
    public String icon;
}

class Clouds {
    public float all;
}

class Rain {
    public float h3;
}

class Wind {
    public float speed;
    public float deg;
}

class Main {
    public float temp;
    public float humidity;
    public float pressure;
    public float temp_min;
    public float temp_max;
}

class Sys {
    public String country;
    public long sunrise;
    public long sunset;
}

class Coord {
    public double lon;
    public double lat;
}

