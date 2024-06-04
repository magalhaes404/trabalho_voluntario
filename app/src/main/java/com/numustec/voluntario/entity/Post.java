package com.numustec.voluntario.entity;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post {
    public String title,descript,address, author,id;
    public long datetime;
    public Post(){

    }
    public String getDate(){
        Log.e("GETDATE","value => "+this.datetime);
        Date date = new Date(datetime * 1000); // Convert to milliseconds
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Choose desired format
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
    public Date getDateI(){
        Date date = new Date(datetime * 1000); // Convert to milliseconds
        return date;
    }
}
