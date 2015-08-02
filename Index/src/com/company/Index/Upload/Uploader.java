package com.company.Index.Upload;


import com.company.Index.Upload.DataHelperClass.VideoHelper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 30/07/15.
 */
public class Uploader {

    File uploadFolderPath;
    Map<String,File> fileMap;
    MongoCollection<Document> videoCollection;


    public Uploader(MongoCollection<Document> videoCollection){

        this.fileMap = new HashMap<>();
        this.videoCollection = videoCollection;

    }


    public boolean checkFiles(String path) {

        boolean hasVideo = false;
        boolean hasXML = false;
        boolean hasScript = false;
        boolean hasStatfile = false;

        uploadFolderPath = new File(path);

        //get only files and not folders
        File files[] = uploadFolderPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });


        //check for all necessary files
        for (File file : files) {

            //check for vid file
            if (file.getName().matches(".*mp4")) {
                hasVideo = true;
                fileMap.putIfAbsent("video", file);
            }

            //check fro transcript file
            if (file.getName().matches(".*script")) {
                hasScript = true;
                fileMap.putIfAbsent("transcript", file);
            }

            //check for xml file
            if (file.getName().matches(".*xml")) {
                hasXML = true;
                fileMap.putIfAbsent("xml", file);
            }

            //check for statistics file
            if (file.getName().matches(".*txt")) {
                hasStatfile = true;
                fileMap.putIfAbsent("statfile", file);
            }

            //check for seg file
            if (file.getName().matches(".*seg")) {
                hasStatfile = true;
                fileMap.putIfAbsent("segfile", file);
            }


        }

        return hasScript && hasXML && hasStatfile && hasVideo;
    }


    public void add(String path){

        if(!checkFiles(path)){
            System.out.println("files not found !!!!!!!!!!!!");
        }else{

            VideoDataConsolidator videoDataConsolidator = new VideoDataConsolidator(fileMap);
            VideoHelper video = videoDataConsolidator.collectVideoData();

            //add to database




        }

    }





}
