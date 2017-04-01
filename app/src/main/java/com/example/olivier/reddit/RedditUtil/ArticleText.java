package com.example.olivier.reddit.RedditUtil;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * Contien le text de l'article et s'occupe du formatage. la classe principale est
 * composé d'une série de classe interne appelé Section contenant les textes
 */

public class ArticleText {
    private String title;
    private ArrayList<Section> sections;

    //----------------Classe interne-------------------
    public class Section{
        private String title;
        private String text;


        public Section(String title, String text){
            this.title = title;
            this.text = text;
        }

        public String toString(){
            if(!title.equals(""))
                return title + "\n\n" + text;
            else return text;
        }
        /*
        *TODO:
        * je veut ajouter des propriété par exemple si un texte est en italique.
         * si le format est une liste etc.
         * pour utilisé dans formatForView
        */

    }

    //le constructeur ne prend que le titre de Larticle en paramètre
    public ArticleText(String title){
        this.title = title;
        this.sections = new ArrayList<>();
    }

    //ajouter une nouvelle section
    public void addSection(String title, String text){
        sections.add(new Section(title, text));
    }

    public String toString(){
        String text = title + "\n\n";
        for(int i=0; i<sections.size(); i++){
            text += sections.get(i).toString() + "\n\n\n";
        }
        return text;
    }

    //Accessor
    public int size(){
        return sections.size();
    }

    public Section getSection(int i){
        return sections.get(i);
    }

    //fait le formatage (titre plus grand et en gras text normal, etc.)
    //voir la classe SpannableStringBuilder et l'interface Spannable
    public CharSequence formatForView(Context context){

        Section section;
        SpannableStringBuilder text;
        //spannableStringBuilder est une chaine de caractère avec la possibilité de modifier l'affichage
        //de certaine partie
        SpannableStringBuilder title = new SpannableStringBuilder(this.title);


        SpannableStringBuilder result = new SpannableStringBuilder();

        //met le titre en gras et plus gros de 1.8 fois la taille mit dans le textVIew
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new RelativeSizeSpan(1.8f), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //On ajoute à la chaine principale
        result.append(title);

        //Pour chaque section on met le titre 1.5fois plus grand et le text de grandeur normal
        for(int i=0; i<sections.size(); i++){
            section = sections.get(i);
            title = new SpannableStringBuilder(section.title);
            text = new SpannableStringBuilder(section.text);
            title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            title.setSpan(new RelativeSizeSpan(1.5f), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.append(title);
            result.append("\n\n");
            result.append(text);
            result.append("\n\n\n");

        }
        return result;
    }

}
