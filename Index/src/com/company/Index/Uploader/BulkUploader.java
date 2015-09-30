package com.company.Index.Uploader;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;

/**
 * Created by rahul on 30/07/15.
 */
public class BulkUploader extends Uploader {


    public BulkUploader(MongoCollection<Document> videocollection){
        super(videocollection);
    }


    public void scanFolder(String basePath){

        File baseFolder = new File(basePath);
        File[] directories = baseFolder.listFiles(File::isDirectory);

        for(File directory : directories) {
            add(directory.getAbsolutePath());
        }

    }

}
