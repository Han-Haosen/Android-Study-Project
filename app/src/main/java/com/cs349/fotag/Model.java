package com.cs349.fotag;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class Model extends Observable {
    private static Model ourInstance;//Singleton in Android
    static Model getInstance()
    {
        if (ourInstance == null) {
            ourInstance = new Model();
        }
        return ourInstance;
    }

    public Context context;
    public ArrayList<RatedImage> allImages;
    public ArrayList<RatedImage> selectedImages;
    public ArrayList<RatedImage> imageSet;
    public ArrayList<String> setUrls;
    public String oneUrl;
    public int currentRating;

    Model() {
        currentRating = 0;
        this.setUrls = new ArrayList<String>();
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/bunny.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/chinchilla.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/deer.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/doggo.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/ducks.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/fox.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/hamster.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/hedgehog.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/husky.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/kitten.png");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/loris.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/puppy.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/running.jpg");
        setUrls.add("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/sleepy.png");
        this.allImages = new ArrayList<RatedImage>();
        this.selectedImages = new ArrayList<RatedImage>();
        this.imageSet = new ArrayList<RatedImage>();
        this.oneUrl = null;
    }

    public void setContext(Context con){
        context = con;
    }

    public void initObservers()
    {
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    @Override
    public synchronized void deleteObserver(Observer o)
    {
        super.deleteObserver(o);
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    @Override
    public synchronized void addObserver(Observer o)
    {
        super.addObserver(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers()
    {
        super.deleteObservers();
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers()
    {
        setChanged();
        Log.d("Update Triggered","Update");
        super.notifyObservers();

    }

    public boolean changeRating(int num){
        this.currentRating = num;
        Log.d("Update Rating", "Rating" + num);
        selectedImages.clear();
        for(int i = 0; i < allImages.size(); i++){
            if(allImages.get(i).rating >= currentRating){
                selectedImages.add(allImages.get(i));
            }
        }
        setChanged();
        notifyObservers();
        return true;
    }

    public void filterImages(){
        selectedImages.clear();
        for(int i = 0; i < allImages.size(); i++){
            if(allImages.get(i).rating >= currentRating){
                selectedImages.add(allImages.get(i));
            }
        }
        setChanged();
        notifyObservers();
    }


    public boolean uploadImage(String url){
        final String url1 = url;
        RatedImage newImage = new RatedImage(url1, this);
        allImages.add(newImage);
        selectedImages.clear();
        for(int i = 0; i < allImages.size(); i++){
            if(allImages.get(i).rating >= currentRating){
                selectedImages.add(allImages.get(i));
            }
        }
        Log.v("Selected Size", "Value = " + selectedImages.size());
        setChanged();
        notifyObservers();
        return true;
    }
    public void initImageSet(){
        imageSet.clear();
        for(String s : setUrls){
            RatedImage newImage = new RatedImage(s,this);
            imageSet.add(newImage);
        }
    }
    public boolean uploadImageSet(){
        for (RatedImage s : imageSet){
            allImages.add(s);
        }
        selectedImages.clear();
        for(int i = 0; i < allImages.size(); i++){
            if(allImages.get(i).rating >= currentRating){
                selectedImages.add(allImages.get(i));
            }
        }
        Log.v("Selected Size", "Value = " + selectedImages.size());
        setChanged();
        notifyObservers();
        return true;
    }
    public boolean clearImages(){
        this.allImages.clear();
        this.selectedImages.clear();
        setChanged();
        notifyObservers();
        return true;
    }
}
