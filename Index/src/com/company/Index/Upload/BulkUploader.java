package com.company.Index.Upload;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by rahul on 30/07/15.
 */
public class BulkUploader extends Uploader {


    public BulkUploader(MongoCollection<Document> videocollection){
        super(videocollection);
    }

    public void scanFolder(String basePath){

        File baseFolder = new File(basePath);

        String folders[] = baseFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return new File(file,s).isDirectory();
            }
        });


        for(String folder : folders)
            add(folder);

    }

}
