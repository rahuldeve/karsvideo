package com.company.Index.Retrievers;

import com.company.Index.Upload.DataHelperClass.SegmentHelper;
import com.company.Index.Upload.DataHelperClass.VideoHelper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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

        for(String segid : retrievedSegmentList){

            MongoCursor<Document> videoCursor = videoCollection.find( eq("segments.segid", segid) ).iterator();
            VideoHelper tempVideo = new VideoHelper();


            while (videoCursor.hasNext()){

                Document videoDocument = videoCursor.next();

                tempVideo.videoid = (String) videoDocument.get("videoid");
                tempVideo.name = (String) videoDocument.get("name");
                tempVideo.location = (String) videoDocument.get("location");
                tempVideo.keyphrases = (List) videoDocument.get("keyphrases");

                //get list of segments as Documents
                List<Document> segmentsAsDocument = (List) videoDocument.get("segments");

                //for each entry of segment document in a video document create a segment Helper object and add it to list
                List<SegmentHelper> tempSegmentList = new ArrayList<>();
                for(Document segmentDoc : segmentsAsDocument){

                    SegmentHelper tempSegment = new SegmentHelper();

                    tempSegment.segid = (String) segmentDoc.get("segid");
                    tempSegment.start = (Double) segmentDoc.get("start");
                    tempSegment.stop = (Double) segmentDoc.get("stop");
                    tempSegment.keyphrases = (List) segmentDoc.get("keyphrases");
                    tempSegment.script = (String) segmentDoc.get("script");
                    tempSegment.videoid = (String) segmentDoc.get("videoid");

                    tempSegmentList.add(tempSegment);
                }


                tempVideo.segments = tempSegmentList;
                retrievedVideos.add(tempVideo);
            }


        }

        return retrievedVideos;
    }

}
