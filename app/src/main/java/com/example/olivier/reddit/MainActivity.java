package com.example.olivier.reddit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olivier.reddit.Adapter.ListAdapter;
import com.example.olivier.reddit.Adapter.ListItems;
import com.example.olivier.reddit.RedditJson.Child;
import com.example.olivier.reddit.RedditJson.ChildData;
import com.example.olivier.reddit.RedditJson.Preview;
import com.example.olivier.reddit.RedditJson.RootData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    DrawerLayout drawer;
    ListView drawerList;
    final String[] sections = {"popular", "news", "politic", "economy", "science", "autotldr"};
    final int[] icons = {R.drawable.popular_icon, R.drawable.news_icon, R.drawable.politics_icon,
            R.drawable.economy_icon, R.drawable.science_icon, R.drawable.popular_icon};
    ListView list;

    ListAdapter mainItemListAdapter;
    ListAdapter leftDrawerListAdapter;
    RootData data;
    List<Child> children = new ArrayList<>();
    String sectionsel;
    int pageCount = 0;

    RedditApi reddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = (ListView)findViewById(R.id.listView);


        Intent intent = getIntent();
        sectionsel = intent.getStringExtra("SECTION");
        if(sectionsel == null)
            sectionsel = "popular";

        reddit = new RedditApi(sectionsel);

        RunApi run = new RunApi(reddit);
        run.execute();

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.left_drawer);
        leftDrawerListAdapter= new ListAdapter(getApplicationContext(), R.layout.drawer_list_item,
                R.id.drawer_list_item_text, R.id.drawer_list_item_image, sections, icons);
        drawerList.setAdapter(leftDrawerListAdapter);
        drawerList.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(view.getId()){
            case R.id.rangee:

                ChildData childData = children.get(position).data;

                String title = childData.title;
                Preview preview = childData.preview;
                String image;

                if (preview == null)
                    image = "0";
                else
                    image = preview.images.get(0).source.url;

                String imageBackup = childData.thumbnail;
                String author = childData.author;
                String link = childData.url;
                String selfText = childData.selftext;

                Intent detailsIntent = new Intent(this, DetailsActivity.class);
                detailsIntent.putExtra("TITLE", title);
                detailsIntent.putExtra("IMAGE", image);
                detailsIntent.putExtra("IMAGE_BACKUP", imageBackup);
                detailsIntent.putExtra("AUTHOR", author);
                detailsIntent.putExtra("LINK", link);
                detailsIntent.putExtra("SELF_TEXT", selfText);
                startActivity(detailsIntent);
                break;
            case R.id.drawer_list_item:
                Log.i(sections[position], "drawable");
                Intent newMainActivity = new Intent(this, MainActivity.class);
                newMainActivity.putExtra("SECTION", sections[position]);
                startActivity(newMainActivity);
                break;
        }

    }






    public class RunApi extends AsyncTask<String, Object, RootData>{
        String[] titleList;
        String[] imageUrlList;
        //String category;
        RedditApi reddit;


        public RunApi(RedditApi reddit) {
            this.reddit = reddit;
        }

        @Override
        protected RootData doInBackground(String... params) {
            //reddit = new RedditApi(category);
            try {

                data = reddit.run();
                children.addAll(data.children);
                titleList = new String[children.size()];
                imageUrlList = new String[children.size()];
                for (int i = 0; i < children.size(); i++) {
                    titleList[i] = children.get(i).data.title;
                    imageUrlList[i] = children.get(i).data.thumbnail;
                }

            } catch (IOException e) {

                Intent intent = new Intent(getApplicationContext(), ConnexionErrorActivity.class);

                intent.putExtra("SECTION", sectionsel);
                Log.i(getApplicationContext().toString(), " conex erreur");
                startActivity(intent);

                System.exit(0);

            }

            return data;
        }

        @Override
        protected void onPostExecute(RootData rootData) {

            mainItemListAdapter = new ListAdapter(getApplicationContext(), R.layout.rangee,
                    R.id.rangee_text, R.id.rangee_image, titleList, imageUrlList);


            list.setAdapter(mainItemListAdapter);
            list.setOnItemClickListener(MainActivity.this);
            Log.i("" + list.getLastVisiblePosition(), "visible");


        }


    }
}
