package com.company.ResultData;

import com.company.Index.Upload.DataHelperClass.VideoHelper;

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
