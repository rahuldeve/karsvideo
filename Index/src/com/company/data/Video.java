package com.company.data;

import com.google.gson.Gson;

/**
 * Created by rahul on 13/06/15.
 */
public class Video {

    String name;
    String location;
    String keyphrases;
    int relevance;
    String relevance_group;


    Gson gson;

    public Video(String name,String location, String keyphrases,int relevance, String relevance_group){
        this.name=name;
        this.location=location;
        this.keyphrases=keyphrases;
        this.relevance=relevance;
        this.relevance_group = relevance_group;


        this.gson = new Gson();
    }

    public String toJSON(){
        return gson.toJson(this);
    }

}
