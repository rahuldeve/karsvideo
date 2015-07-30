package com.company.Indexer;

import com.company.Data.Segment;
import com.company.Data.Video;
import com.company.Database.Db;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IndexService {

    Index index;
    Db database;

    MongoCollection<Document> segmentCollection;
    MongoCollection<Document> videoCollection;


    public IndexService(){

        database = new Db();
        database.connect("localhost","indexdb");

        segmentCollection = database.getCollection("segments");
        videoCollection = database.getCollection("videos");

    }


    public void query(){}
    public void indexQuery(String query){
        //perform search using any search algo to get relevant segids
        //use index
    }



    public Map databaseQuery(String query)
    {





        List<String> relevantSegID = new ArrayList<>();
        List<Document> retrivedSegments = new ArrayList<>();

        //retrive segids from database along with video details
        for(String segid : relevantSegID)
        {
            MongoCursor<Document> cursor = segmentCollection.find(eq("segid",segid)).iterator();

            try {

                while (cursor.hasNext()) {
                    retrivedSegments.add(cursor.next());
                }

            } finally {
                cursor.close();
            }

        }



        //for each segid query the database to find appropriate
        Map<String,List<Document>> videoIDSegmentMap = new HashMap<>();     //a hash map containing videoid segment relation.
                                                                            //videoid is represented as a string
                                                                            //segments are represented as Documents stored in a List

        for(Document segment : retrivedSegments){

            String videoID  = (String) segment.get("videoid");


            //if the hashmap contains the videoid as key just add the segment to the list associated with the key
            if(videoIDSegmentMap.containsKey(videoID)){
                videoIDSegmentMap.get(videoID).add(segment);

            }else{

                List<Document> temp = new ArrayList<>();
                temp.add(segment);
                videoIDSegmentMap.put(videoID, temp);

            }

        }


        Map<Document,List<Document>> videoSegmentMap = new HashMap<>();     //a hash map containing video segment relation each in document format
                                                                            //videoid is represented as a string
                                                                            //segments are represented as Documents stored in a List


        //query video collection for video object
        for(String videoid : videoIDSegmentMap.keySet()){

            MongoCursor<Document> cursor = videoCollection.find(eq("videoid", videoid)).iterator();


            while(cursor.hasNext()){
                videoSegmentMap.putIfAbsent(cursor.next(),videoIDSegmentMap.get(videoid));

            }


        }


        return videoSegmentMap;

    }

    public List parseToDataObject(Map<Document,List<Document>> videoSegmentMap){

        List<Video> result = new ArrayList<>();

        //for each video document create a video object

        for(Document videoDocument : videoSegmentMap.keySet()){

            String videoid = videoDocument.get("videoid").toString();
            String videoname = videoDocument.get("name").toString();
            String videolocation = videoDocument.get("location").toString();
            int relevance = Integer.parseInt(videoDocument.get("relevance").toString());
            //find relevance group

            Video tempvideo = new Video();


            List<Segment> segmentList = new ArrayList<>();
            for(Document segment : videoSegmentMap.get(videoDocument)){

                String segid = segment.get("segid").toString();
                double start = Double.parseDouble(segment.get("start").toString());
                double end = Double.parseDouble(segment.get("end").toString());
                List<String> keyphrases = (List) segment.get("keyphrases");
                String script = segment.get("script").toString();

                Segment temp = new Segment(segid, start, end, keyphrases, script);
                segmentList.add(temp);



            }




        }




        //for each segment document create segment object

        //consolidate all the keywords for video object
        //find relevance group

        //return list of video objects

        return result;
    }


}
