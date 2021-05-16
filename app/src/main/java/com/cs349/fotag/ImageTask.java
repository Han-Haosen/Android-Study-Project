package com.cs349.fotag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
public class ImageTask extends AsyncTask<RatedImage, Void, Bitmap> {
        RatedImage im;
        String urlTemp;
        boolean success;
        AlertDialog alert;
        Context context;
        public ImageTask(RatedImage image, Context context){
            this.im = image;
            this.urlTemp = image.url;
            this.success = false;
            this.context = context;
        }
        @Override
        protected Bitmap doInBackground(RatedImage... image){
            return download(image[0]);
        }

        public Bitmap download(RatedImage image){
            im = image;
            String url = image.url;
            Bitmap temp = null;
            try{
                URL toDownload = new URL(url);
                URLConnection c = toDownload.openConnection();
                c.connect();
                InputStream i = c.getInputStream();
                temp = BitmapFactory.decodeStream(i);// From https://developer.android.com/reference/android/graphics/BitmapFactory
                Log.d("Bitmap", temp.toString());
                im.setBitmap(temp);
                this.success = true;
                i.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            return temp;
        }
        public void onPostExecute(Bitmap result){
            im.triggerUpdate();
            if(!success){
                AlertDialog.Builder b = new AlertDialog.Builder(context);//From htps://developer.android.com/reference/android/app/AlertDialog?hl=en
                b.setMessage("Invalid URL has been inputted").setTitle("Invalid URL").setCancelable(true);
                b.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
                alert = b.create();
                alert.show();
            }
        }
    }
