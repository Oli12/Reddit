package com.example.olivier.reddit;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * gère les changements de page
 */

public class NewPageControl implements AbsListView.OnScrollListener{

    ListView list;
    Boolean load;

    public NewPageControl(ListView list){
        this.list =list;
        this.load = true;
    }

    public void setLoadFlag(){
        load = true;
    }

    public void raiseLoadFlag(){
        load = true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (list.getLastVisiblePosition() == list.getCount() - 1 && !load){
            MainActivity main= (MainActivity)list.getContext();
            RedditApi api = main.reddit;

            load = true;
            Log.i(Integer.toString(list.getLastVisiblePosition()), "end of list");

        }
    }
}
