package com.company.Index.Retrieve;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by rahul on 3/08/15.
 */
public class DatabaseRetriever {

    MongoCollection<Document> videoCollection;

    public DatabaseRetriever(MongoCollection<Document> videoCollection){
        this.videoCollection = videoCollection;
    }

}
