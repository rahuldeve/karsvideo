package com.company.Database;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by rahul on 18/06/15.
 */
public class Db {

    public MongoClient mongoClient;
    public MongoDatabase mongoDatabase;



    public void connect( String hostname, String dbname){

        mongoClient = new MongoClient(hostname);
        mongoDatabase = mongoClient.getDatabase(dbname);

    }


    public void close(){
        mongoClient.close();
    }











    public MongoCollection getCollection(String collectionname){

        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionname);
        return collection;

    }

    public void insert(){}

    public void query(String query){



    }




}
