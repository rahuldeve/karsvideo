package com.company.ResultData;

<<<<<<< HEAD
import com.company.Index.DataHelperClass.VideoHelper;
=======
import com.company.Index.Upload.DataHelperClass.VideoHelper;
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e

import java.util.Map;

/**
 * Created by rahul on 12/08/15.
 */
public class ResultObject {

    public VideoHelper video;
    public double relevance;
    public String relevance_group;

    Map<String,Double> segidRelevanceScores;



    public ResultObject(VideoHelper videoHelper, double relevance, String relevance_group){

        this.video = videoHelper;
        this.relevance = relevance;
        this.relevance_group =relevance_group;

    }


    public ResultObject(){

    }

    public void setRelevance(double relevance){
        this.relevance = relevance;

        //calculate relevance group
        if(relevance < 30)
            relevance_group = "SR";

        else if(relevance < 60)
            relevance_group = "MR";

        else
            relevance_group = "HR";


    }




    public ResultObject(VideoHelper videoHelper, Map<String,Double> segidRelevanceScores){
        this.video = videoHelper;
        this.segidRelevanceScores = segidRelevanceScores;
    }








}
