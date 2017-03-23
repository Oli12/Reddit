/*
    Cette classe qui est une sous-classe de ArrayAdapter cree une listView de type image, textView

*/


package com.example.olivier.reddit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olivier.reddit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by olivier on 17-03-14.
 */

public class ListAdapter extends ArrayAdapter<String>{
    int layoutId, textId, imageId;
    int[] images;     //liste des id des image.
    // *l'ordre des image doit correspondre à l'ordre dans le tableau de text
    String[] imagesUrl;
    //Context context;

    //Constructeur avec drawable id
    public ListAdapter(Context context, int layoutId, int textId,
                       int imageId, String[] list, int[] images) {

        super(context, layoutId, list); // appel au constructeur
        this.layoutId = layoutId;
        this.textId = textId;
        this.imageId = imageId;
        this.images = images;
        //this.context = context;
    }

    //Constructeur avec image url
    public ListAdapter(Context context, int layoutId, int textId,
                       int imageId, String[] list, String[] imagesUrl) {
        super(context, layoutId, list); // appel au constructeur
        this.layoutId = layoutId;
        this.textId = textId;
        this.imageId = imageId;
        this.imagesUrl = imagesUrl;
        //this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        TextView text = (TextView)v.findViewById(textId);
        ImageView image = (ImageView)v.findViewById(imageId);
        text.setText(getItem(position));
        if(imagesUrl == null)
            image.setImageResource(images[position]);
        else{
            String url = imagesUrl[position];
            Log.i("" + getContext(), "url:");
            if(url.isEmpty() || url == null){
                url = "1";
            }
            Picasso.with(getContext()).load(url).placeholder(R.drawable.popular_icon).into(image);
        }

        return v;
    }

}


