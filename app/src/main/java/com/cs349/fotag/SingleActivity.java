package com.cs349.fotag;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import androidx.core.app.NavUtils;

import java.util.Observable;
import java.util.Observer;

public class SingleActivity extends AppCompatActivity implements Observer {
    public Model model;
    public ImageView img;
    public RatingBar rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Switch Page code from Course Notes
        model = Model.getInstance();
        setContentView(R.layout.activity_single);
        Intent i = getIntent();//from SwitchPages
        final int id = i.getIntExtra("ImageID",   0);
        img = findViewById(R.id.separate_image);
        rating = findViewById(R.id.separate_rating);
        img.setImageBitmap(model.selectedImages.get(id).bit);
        rating.setRating(model.selectedImages.get(id).rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar bar, float f, boolean bool){
                model.selectedImages.get(id).setRating((int) f);
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration config){

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // Remove observer when activity is destroyed.
        model.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // Update button with click count from model

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {//switch page from course note on gitlab
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
