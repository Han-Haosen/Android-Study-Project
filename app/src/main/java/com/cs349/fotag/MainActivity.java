package com.cs349.fotag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    public Model model;
    public GridView grid;
    public ImageButton clear;
    public ImageButton upload;
    public ImageButton loadset;
    public RatingBar rating;
    public RatedImageAdapter r;
    public Context context;
    public String url;
    public PopupWindow prompt;
    public RelativeLayout layout;

    @Override
    public void update(Observable o, Object arg)
    {
        r.notifyDataSetChanged();
        grid.setAdapter(r);
        Log.d("Check","Update Done");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this;
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
        model.addObserver(this);
        model.setContext(this);
        model.initImageSet();

        int orientation = getResources().getConfiguration().orientation;
        r = new RatedImageAdapter(this,model);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            layout = (RelativeLayout) findViewById(R.id.portrait_layout);
        } else {
            setContentView(R.layout.activity_main_landscape);
            layout = (RelativeLayout) findViewById(R.id.landscape_layout);
        }
        grid = findViewById(R.id.grid_view);
        grid.setAdapter(r);
        loadset = (ImageButton) findViewById(R.id.imageButton5);
        upload = (ImageButton) findViewById(R.id.imageButton6);
        clear = (ImageButton) findViewById(R.id.imageButton7);
        rating = (RatingBar) findViewById(R.id.ratingBar3);
        grid = (GridView) findViewById(R.id.grid_view);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("TAG::", "Position");
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Clear", "image");
                model.clearImages();
            }
        });
        loadset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.uploadImageSet();
                model.notifyObservers();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPrompt();
            }
        });
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar bar, float f, boolean bool){
                model.changeRating((int) f);
                model.notifyObservers();
            }
        });
        model.initObservers();
    }

    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
        }
        grid = findViewById(R.id.grid_view);
        r.notifyDataSetChanged();
        grid.setAdapter(r);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // Remove observer when activity is destroyed.
        model.deleteObserver(this);
    }



    public void userPrompt() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup,null);
        prompt = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button submit = (Button) view.findViewById(R.id.submit_url);
        Button cancel = (Button) view.findViewById(R.id.quit_prompt);
        final EditText edit = (EditText) view.findViewById(R.id.editText);
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                prompt.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.v("URL Upload", edit.getText().toString());
                model.uploadImage(edit.getText().toString());
                prompt.dismiss();
            }
        });
        prompt.setFocusable(true);
        prompt.showAtLocation(layout, Gravity.CENTER,0,0);
    }

}
