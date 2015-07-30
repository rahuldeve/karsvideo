package com.company.Data;

import java.util.List;

/**
 * Created by rahul on 13/06/15.
 */
public class Video {

    String name;
    String location;
    int relevance;
    String relevance_group;
    List<String> keyphrases;
    List<Segment> segments;

    public Video()
    {

    }




    public Video(String name,String location, List<String> keyphrases, int relevance, String relevance_group, List<Segment> segments){

        this.name=name;
        this.location=location;
        this.keyphrases=keyphrases;
        this.relevance=relevance;
        this.relevance_group = relevance_group;
        this.segments = segments;

    }


}
