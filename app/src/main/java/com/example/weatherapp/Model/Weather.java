package com.example.weatherapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Weather")

public class Weather extends Model {

    //The table consist only one field name
    @Column(name = "info")
    public String info;
}
