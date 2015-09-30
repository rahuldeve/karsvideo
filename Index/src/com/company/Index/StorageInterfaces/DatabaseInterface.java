package com.company.Index.StorageInterfaces;

import com.company.Index.DataHelperClass.SegmentHelper;
import com.company.Index.DataHelperClass.VideoHelper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by rahul on 28/09/15.
 */
public class DatabaseInterface {

    MongoClient mongoClient;
    MongoDatabase ascistDatabase;
    MongoCollection<Document> videoCollection;

    public DatabaseInterface(){

        this.mongoClient = new MongoClient();
        this.ascistDatabase = mongoClient.getDatabase("ascist");


        //retrieve collection from mongo
        videoCollection = ascistDatabase.getCollection("videos");



        //initialise collection if created for the first time
        if(videoCollection.count()==0) {

            //database created first time
            //create index for video and segment
            videoCollection.createIndex(new Document("videoid", 1));
            videoCollection.createIndex(new Document("segments.segid", 1));
        }

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






    public void add(VideoHelper videoHelper){

        //add to database

        List<Document> segmentsAsDocumentsList = new ArrayList<>();

        for(SegmentHelper segment : videoHelper.segments){

            Document segmentDocument = new Document("segid", segment.segid)
                    .append("videoid",segment.segid)
                    .append("start", segment.start)
                    .append("stop", segment.stop)
                    .append("keyphrases", segment.keyphrases)
                    .append("script", segment.script);

                segmentsAsDocumentsList.add(segmentDocument);

            }


            Document videoDoc = new Document("videoid", videoHelper.videoid)
                    .append("name", videoHelper.name)
                    .append("location", videoHelper.location)
                    .append("keyphrases", videoHelper.keyphrases)
                    .append("segments", segmentsAsDocumentsList);

            /*
            videoDoc.append("videoid", video.videoid);
            videoDoc.append("name", video.name);
            videoDoc.append("location", video.location);
            videoDoc.append("keyphrases", video.keyphrases);
            videoDoc.append("segments", video.segments);
            */

            videoCollection.insertOne(videoDoc);

            System.out.print("one video inserted");

        }




}
