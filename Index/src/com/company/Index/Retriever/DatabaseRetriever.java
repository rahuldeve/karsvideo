package com.company.Index.Retriever;

import com.company.Index.DataHelperClass.SegmentHelper;
import com.company.Index.DataHelperClass.VideoHelper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;

/**
 * Created by rahul on 3/08/15.
 */
public class DatabaseRetriever {

    MongoCollection<Document> videoCollection;

    public DatabaseRetriever(MongoCollection<Document> videoCollection){
        this.videoCollection = videoCollection;
    }


    public List<VideoHelper> get(List<String> retrievedSegmentList){

        List <VideoHelper> retrievedVideos = new ArrayList<>();

        System.out.println("*******************************************************************");
        //mongo query to send a list of segids and get documents that has them, group by videos
        Iterable<Document> cursor = videoCollection.find(in("segments.segid", retrievedSegmentList));

        for(Document document : cursor)
                System.out.println(document.get("videoid"));

        System.out.println("*******************************************************************");


        for(Document videoDocument : cursor){

            VideoHelper videoHelper = new VideoHelper();

            videoHelper.videoid = (String) videoDocument.get("videoid");
            videoHelper.name = (String) videoDocument.get("name");
            videoHelper.location = (String) videoDocument.get("location");
            videoHelper.keyphrases = (List) videoDocument.get("keyphrases");

            //get list of segments as Documents
            List<Document> segmentsAsDocument = (List) videoDocument.get("segments");

            //for each entry of segment document in a video document create a segment Helper object and add it to list
            List<SegmentHelper> segmentHelperList = new ArrayList<>();

            for(Document segmentDoc : segmentsAsDocument){

                SegmentHelper tempSegment = new SegmentHelper();

                tempSegment.segid = (String) segmentDoc.get("segid");
                tempSegment.start = (Double) segmentDoc.get("start");
                tempSegment.stop = (Double) segmentDoc.get("stop");
                tempSegment.keyphrases = (List) segmentDoc.get("keyphrases");
                tempSegment.script = (String) segmentDoc.get("script");
                tempSegment.videoid = (String) segmentDoc.get("videoid");

                segmentHelperList.add(tempSegment);
            }

            videoHelper.segments = segmentHelperList;
            retrievedVideos.add(videoHelper);
        }

        return retrievedVideos;
    }

}
