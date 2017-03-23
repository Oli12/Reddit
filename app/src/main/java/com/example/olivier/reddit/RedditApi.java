package com.example.olivier.reddit;

import android.util.Log;

import com.example.olivier.reddit.RedditJson.Child;
import com.example.olivier.reddit.RedditJson.Root;
import com.example.olivier.reddit.RedditJson.RootData;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by olivier on 17-03-09.
 */

public class RedditApi {
    public String url;

    public RedditApi(String category){
        String str = category;
        if(str.isEmpty()){
            str = "subreddits.json";
        }
        else{
            str = "r/" + str + ".json";
        }
        this.url = "https://www.reddit.com/" + str;
    }

    public RootData run() throws IOException {
        //Récupérer du contenue html a partir d'un url

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Log.i(json, "run: j");

        //Parser le contenu json
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Root> jsonAdapter = moshi.adapter(Root.class);
        Root root = jsonAdapter.fromJson(json);
        return root.data;

    }

    public void nextPage(String after){
        url += "?count=25&after=" + after;
        Log.i(url, "urldddd");
    }

}
