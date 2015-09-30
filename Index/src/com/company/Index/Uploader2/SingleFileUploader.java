package com.company.Index.Uploader2;

import com.company.Index.DataHelperClass.VideoDataConsolidator;
import com.company.Index.DataHelperClass.VideoHelper;
import com.company.Index.StorageInterfaces.DatabaseInterface;
import com.company.Index.StorageInterfaces.IndexInterface;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 28/09/15.
 */
public class SingleFileUploader implements Uploader {

    File uploadFolderPath;
    Map<String,File> fileMap;

    IndexInterface indexInterface;
    DatabaseInterface databaseInterface;

    public SingleFileUploader(IndexInterface indexInterface, DatabaseInterface databaseInterface) {

        this.fileMap = new HashMap<>();
        this.databaseInterface = databaseInterface;
        this.indexInterface = indexInterface;

    }


    @Override
    //have to add extra functionalities for checking clusterd keyword csv files etc
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
            databaseInterface.add(video);

            //add to index



        }

    }


}

