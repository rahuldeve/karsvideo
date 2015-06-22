package com.company.db;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Created by rahul on 18/06/15.
 */
public class Mongo {

    public MongoClient mongoClient;
    public MongoDatabase mongoDatabase;
    public MongoCollection collection;




    public void connect( String hostname, String dbname){

        mongoClient = new MongoClient(hostname);
        mongoDatabase = mongoClient.getDatabase(dbname);

    }


    public void close(){
        mongoClient.close();
    }











    public void getCollection(){

    }

    public void insert(){}

    public void query(){}




}
