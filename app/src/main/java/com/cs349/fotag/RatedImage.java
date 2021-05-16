package com.cs349.fotag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RatedImage {
    public int rating;
    public String url;
    public Bitmap bit;
    public Model m;
    public RatedImage(String url, Model m){
        this.url = url;
        rating = 5;
        this.m = m;
        initBitmap();
    }
    public void setRating(int rat){
        rating = rat;
        triggerUpdate();
        Log.d("Changed Rating","For Individual");
    }
    public int getRating(){
        return rating;
    }
    public Bitmap getBitmap(){
        return this.bit;
    }
    public void setBitmap(Bitmap bitmap){
        this.bit = bitmap;
    }

    public void triggerUpdate(){
        m.filterImages();
    }

    public void initBitmap(){
        new ImageTask(this,m.context).execute(this);
    }
}


