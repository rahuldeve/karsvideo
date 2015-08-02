package com.company.Index.Upload.DataHelperClass;

import java.util.List;

/**
 * Created by rahul on 30/07/15.
 */
public class SegmentHelper {

    public String videoid;
    public String segid;
    public double start;
    public double stop;
    public List<String> keyphrases;
    public String script;


    public boolean isContainedWithin(double a, double b){
        return a==start && b==stop;
    }


}
