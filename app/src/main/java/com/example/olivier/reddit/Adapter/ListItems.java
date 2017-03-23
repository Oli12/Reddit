package com.example.olivier.reddit.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olivier on 17-03-14.
 */

public class ListItems {

    private List<Items> items = new ArrayList<>();

    //Class interne des élément de la liste
    public class Items {

        private String text;
        private String image;

        public Items(String text, String image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public String getImageUrl() {
            return image;
        }
    }

    //mutator

    public void add(String text, String image){
        items.add(new Items(text, image));
    }

    //Accessor

    public String getText(int index){
        return items.get(index).getText();
    }

    public String getImageUrl(int index){
        return items.get(index).getImageUrl();
    }

    public int size(){
        return items.size();
    }
}

