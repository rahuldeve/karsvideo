package com.company.Index.Upload;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by rahul on 30/07/15.
 */
public class BulkUploader extends Uploader {

    MongoCollection<Document> videocollection;

    public BulkUploader(MongoCollection<Document> videocollection){
        super(videocollection);
    }

}
