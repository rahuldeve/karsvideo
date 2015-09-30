package com.company.Index;

import com.company.Index.Retriever.DatabaseRetriever;
import com.company.Index.Retriever.IndexRetriever;
import com.company.Index.Uploader.BulkUploader;
import com.company.Index.DataHelperClass.VideoHelper;
import com.company.Index.Uploader.Uploader;
import com.company.ResultData.DataParser;
import com.company.ResultData.ResultObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IndexService {

    MongoClient mongoClient;
    MongoDatabase ascistDatabase;


    Uploader videoUploader;
    BulkUploader bulkVideoUploader;

    DatabaseRetriever videoDatabaseRetriever;







    public IndexService(){;

        //load inverted index from file



        //establish connection to mongo and get database
        this.mongoClient = new MongoClient();
        this.ascistDatabase = mongoClient.getDatabase("ascist");


        //retrieve collection from mongo
        MongoCollection<Document> videoCollection = ascistDatabase.getCollection("videos");



        //initialise uploaders and retrivers
        this.videoUploader = new Uploader(videoCollection);
        this.bulkVideoUploader = new BulkUploader(videoCollection);

        this.videoDatabaseRetriever = new DatabaseRetriever(videoCollection);




        //initialise collection if created for the first time
        if(videoCollection.count()==0){

            //database created first time

            //create index for video and segment
            videoCollection.createIndex(new Document("videoid", 1));
            videoCollection.createIndex(new Document("segments.segid",1));


            //invoke Uploader?

        }





    }


    public List<ResultObject> indexQuery(String query){

        //perform search using any search algo to get relevant segids
        IndexRetriever indexRetriever = new IndexRetriever();
        Map<String, Double> segIDRelevanceScore = indexRetriever.queryIndex(query);

        List<String> retrievedSegIDList = new ArrayList<>(segIDRelevanceScore.keySet());


        //retrieve results from database
        List<VideoHelper> retrievedResults = videoDatabaseRetriever.get(retrievedSegIDList);

        // can try to do the following steps using streams but will require a lot of refactoring

        //convert to result message
        List<ResultObject> parsedList = DataParser.parse(retrievedResults, segIDRelevanceScore);

        return parsedList;



    }


    public void uploadVideo(String path){
        videoUploader.add(path);
    }

    public void bulkUploadVideos(String baseFolderPath){
        bulkVideoUploader.scanFolder(baseFolderPath);

    }

    public void close(){
        mongoClient.close();
    }



}
