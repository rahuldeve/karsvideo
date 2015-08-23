package com.company.ResultData;

import com.company.Index.Upload.DataHelperClass.SegmentHelper;
import com.company.Index.Upload.DataHelperClass.VideoHelper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rahul on 13/08/15.
 */
public class DataParser {

    public static List<ResultObject> parse(List<VideoHelper> retrievedList, Map<String,Double> segidRelevanceScores){

        List<ResultObject> parsedList = new ArrayList<>();

        //transform the segmentRelevance scores to percentages

        Double maxScore = Collections.max(segidRelevanceScores.values()) + 0.0001;

        // Heavy use of java 8 streams here
        segidRelevanceScores = segidRelevanceScores.entrySet()
                .stream()
                .collect(Collectors.toMap(p -> p.getKey(), p -> {
                    Double value = ( p.getValue()/ maxScore )*100;
                    return value;
                }));





        for(VideoHelper videoHelper : retrievedList){

            ResultObject resultVideo = new ResultObject();
            
            resultVideo.video = videoHelper;

            
            double avgScore = 0;    // a variable to calculate average relevance score for a video
            int count = 0;          //to find number of relevant segments

            for(SegmentHelper segmentHelper : videoHelper.segments){

                Double score = segidRelevanceScores.get(segmentHelper.segid);

                if(score != null) {
                    avgScore += score;
                    count++;
                }

            }

            avgScore = avgScore/count;
            resultVideo.setRelevance(avgScore);

            parsedList.add(resultVideo);

        }

        return parsedList;



    }

}
