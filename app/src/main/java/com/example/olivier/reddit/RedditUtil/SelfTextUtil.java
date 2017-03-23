package com.example.olivier.reddit.RedditUtil;

/**
 * Created by olivier on 17-03-16.
 */

public class SelfTextUtil {

    public static String removeChariot(String text){
        return text.replaceAll("/r/", "");
    }

    public static String htmlFormat(String text){
        return text.replaceAll("&lt(;|#)", "<").replaceAll("&gt(;|#)", ">");
    }
}
