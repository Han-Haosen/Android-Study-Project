package com.cs349.fotag;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.*;

import java.util.ArrayList;

public class RatedImageAdapter extends BaseAdapter {//Some Code snippet from https://developer.android.com/reference/android/widget/BaseAdapter
    public Context context;
    public Model model;

    public RatedImageAdapter(Context context, Model model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        Log.d("Adapter Size", "" + model.selectedImages.size());
        return model.selectedImages.size();
    }

    @Override
    public Object getItem(int num) {
        return model.selectedImages.get(num);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        final int position = pos;
        Log.d("Loading Image Position", "" + pos);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item, null);
        final ImageView image;
        final RatingBar rating;
        image = (ImageView) convertView.findViewById(R.id.single_image);
        image.setImageBitmap(model.selectedImages.get(pos).getBitmap());
        image.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
               Intent intent = new Intent(context,SingleActivity.class);;
               intent.putExtra("ImageID",position);
               context.startActivity(intent);
            }
        });
        rating = (RatingBar) convertView.findViewById(R.id.single_rating);
        final String url = model.selectedImages.get(pos).url;
        Log.d("Loading Image", model.selectedImages.get(pos).url);
        rating.setRating(model.selectedImages.get(pos).rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar bar, float f, boolean bool){
                model.selectedImages.get(position).setRating((int) f);
            }
        });
        return convertView;
    }
}
