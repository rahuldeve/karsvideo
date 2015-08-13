package com.company.ResultData;

import com.company.Index.Upload.DataHelperClass.SegmentHelper;
import com.company.Index.Upload.DataHelperClass.VideoHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 13/08/15.
 */
public class DataParser {

    public static List<ResultObject> parse(List<VideoHelper> retrievedList, Map<String,Double> segidRelevanceScores){

        List<ResultObject> parsedList = new ArrayList<>();


        for(VideoHelper videoHelper : retrievedList){

            ResultObject resultVideo = new ResultObject();
            
            resultVideo.video = videoHelper;

            
            double avgScore = 0;    // a variable to calculate average relevance score for a video
            for(SegmentHelper segmentHelper : videoHelper.segments){

                Double score = segidRelevanceScores.get(segmentHelper.segid);

                if(score != null)
                    avgScore += score;

            }

            avgScore = avgScore/videoHelper.segments.size();
            resultVideo.setRelevance(avgScore);

            parsedList.add(resultVideo);

        }

        return parsedList;



    }

}
