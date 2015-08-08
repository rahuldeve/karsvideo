package com.company.Index;

import com.company.Index.Retrievers.DatabaseRetriever;
import com.company.Index.Retrievers.IndexRetriever;
import com.company.Index.Upload.BulkUploader;
import com.company.Index.Upload.DataHelperClass.VideoHelper;
import com.company.Index.Upload.Uploader;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class IndexService {

    MongoClient mongoClient;
    MongoDatabase ascistDatabase;


    Uploader videoUploader;
    BulkUploader bulkVideoUploader;

    IndexRetriever invertedIndexRetriever;
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


            //invoke Upload?

        }





    }


    public void indexQuery(String query){
        //perform search using any search algo to get relevant segids
        //use index

        List<String> retrievedSegIDList = new ArrayList<>();
        retrievedSegIDList.add(query);

        //use database retriever to get videos
        //System.out.print(retrievedSegIDList.size());
        List<VideoHelper> temp = videoDatabaseRetriever.get(retrievedSegIDList);

        for(VideoHelper a : temp){
            System.out.print(a.segments.iterator().next().keyphrases);
        }
    }


    public void uploadVideo(String path){
        videoUploader.add(path);
    }

    public void bulkUploadVideos(String baseFolderPath){

    }

    public void close(){
        mongoClient.close();
    }



}
