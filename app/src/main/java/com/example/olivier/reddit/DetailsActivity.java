package com.example.olivier.reddit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olivier.reddit.RedditUtil.ArticleText;
import com.example.olivier.reddit.RedditUtil.HtmlParser;
import com.example.olivier.reddit.RedditUtil.NewsSite;
import com.example.olivier.reddit.RedditUtil.SelfTextUtil;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageDetails;
    TextView titleDetails, authorDetails, linkDetails, inText;
    String slink;
    HtmlParser htmlParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageDetails = (ImageView)findViewById(R.id.details_image);
        titleDetails = (TextView)findViewById(R.id.details_title);
        authorDetails = (TextView)findViewById(R.id.details_author);
        linkDetails = (TextView)findViewById(R.id.details_url);
        inText = (TextView)findViewById(R.id.details_inText);

        Intent intent = getIntent();
        String stitle = intent.getStringExtra("TITLE");
        String simage = intent.getStringExtra("IMAGE");
        String simageBackup = intent.getStringExtra("IMAGE_BACKUP");
        String sauthor = intent.getStringExtra("AUTHOR");
        slink = intent.getStringExtra("LINK");
        String selfText = intent.getStringExtra("SELF_TEXT");


        titleDetails.setText(stitle);
        authorDetails.setText("Author: " + sauthor);

        //Utilisation de HtmlParser----------------

        //info sur les site particulier pas necessaire mais
        // pourrait être utile en cas de problème particulié
        NewsSite site = NewsSite.getSiteFromUrl(slink);

        htmlParser = new HtmlParser(slink); //slink est le lien obtenue du json
        //parametre pur la fonction parsArticleBody
        String articleId;
        String articleBodyId;
        String articleClass;
        String articleTitle;
        
        //Il peuve être null
        if(site == null) {

            articleId = "";
            articleBodyId = "";
            articleClass = "";
            articleTitle = "";
        }
        else{
            articleId = site.getArticleId();
            articleBodyId = site.getIdName();
            articleClass = site.getClassName();
            articleTitle = site.getTitleName();
        }


        ArticleText article = htmlParser.parseArticleBody(articleId, articleBodyId, articleClass, articleTitle);
        CharSequence articleText;
        if(article == null)
            articleText = "";
        else
            articleText = article.formatForView(this);


        inText.setText(articleText);
        //-----------------------------------------


        /*if(selfText.equals("") || selfText == null)
            inText.setText("");
        else
            selfText = SelfTextUtil.removeChariot(selfText);
            inText.setText(selfText);
       */
        //linkDetails.setText(slink);

        String viewImage;

        if(simage == "" || simage != null)
            viewImage = simage;

        else if(simageBackup == "" || simageBackup != null)
            viewImage = simageBackup;
        else
            viewImage = "1";

        Picasso.with(this).load(viewImage).placeholder(R.drawable.popular_icon).into(imageDetails);

        linkDetails.setOnClickListener(this);
        Log.i("asds", " details");

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.details_url:
                Uri uri = Uri.parse(slink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
