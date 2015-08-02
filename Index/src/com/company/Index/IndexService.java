package com.company.Index;

import com.company.Index.Retrieve.DatabaseRetriever;
import com.company.Index.Retrieve.IndexRetriever;
import com.company.Index.Upload.BulkUploader;
import com.company.Index.Upload.Uploader;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class IndexService {


    Uploader videoUploader;
    BulkUploader bulkVideoUploader;

    IndexRetriever invertedIndexRetriever;
    DatabaseRetriever videoDatabaseRetriver;







    public IndexService(){

        //load inverted index from file



        //establish connection to mongo and get database
        MongoClient mongoClient = new MongoClient();
        MongoDatabase ascistDatabase = mongoClient.getDatabase("ascist");


        //retrieve collection
        MongoCollection<Document> videoCollection = ascistDatabase.getCollection("videos");



        //initialise uploaders and retrivers
        Uploader videoUploader = new Uploader(videoCollection);
        BulkUploader bulkVideoUploader = new BulkUploader(videoCollection);




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
    }



}
