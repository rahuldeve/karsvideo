package com.company.Index.DataHelperClass;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 30/07/15.
 */
public class VideoHelper {

    public String videoid;
    public String name;
    public String location;
    public List<String> keyphrases;     //is this necessary?
    public List<SegmentHelper> segments;

    public VideoHelper(){
        this.keyphrases = new ArrayList<>();
    }

}
