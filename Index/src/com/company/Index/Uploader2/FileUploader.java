package com.company.Index.Uploader2;

import com.company.Index.DataHelperClass.VideoDataConsolidator;
import com.company.Index.DataHelperClass.VideoHelper;
import com.company.Index.StorageInterfaces.DatabaseInterface;
import com.company.Index.StorageInterfaces.IndexInterface;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 28/09/15.
 */
public class FileUploader implements Uploader {

    File uploadFolderPath;

    IndexInterface indexInterface;
    DatabaseInterface databaseInterface;

    public FileUploader(IndexInterface indexInterface, DatabaseInterface databaseInterface) {

        this.databaseInterface = databaseInterface;
        this.indexInterface = indexInterface;

    }


    @Override
    //have to add extra functionalities for checking clustered keyword csv files etc
    public Map<String, File> checkFiles(String path) {

        Map<String, File> fileMap = new HashMap<>();

        boolean hasVideo = false;
        boolean hasXML = false;
        boolean hasScript = false;
        boolean hasStatfile = false;
        boolean hasKeywordCSVFile = false;

        uploadFolderPath = new File(path);

        //get only files and not folders
        File files[] = uploadFolderPath.listFiles( file -> {
            return file.isFile();
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

            //check for csv file with keywords
            if(file.getName().matches(".*csv")){
                hasKeywordCSVFile = true;
                fileMap.putIfAbsent("csvfile", file);
            }


        }

        if(hasScript && hasXML && hasStatfile && hasVideo && hasKeywordCSVFile)
            return fileMap;
        else
            return null;
    }



    public void insertOne(String path){

        Map<String, File> fileMap = checkFiles(path);

        if(fileMap == null){
            System.out.println("files not found !!!!!!!!!!!!");
        }else{

            VideoDataConsolidator videoDataConsolidator = new VideoDataConsolidator(fileMap);
            VideoHelper video = videoDataConsolidator.collectVideoData();

            //add to database
            databaseInterface.insertOne(video);

            //add to index
            indexInterface.insertOne(video.videoid,fileMap);



        }

    }


    public void insertMany(String basePath){

        File baseFolder = new File(basePath);
        File[] directories = baseFolder.listFiles(File::isDirectory);

        Map<String, Map<String, File>> fileMaps = new HashMap<>();


        //there is a better code to do this using mongo batch operation
        for(File directory : directories) {

            Map<String, File> fileMap = checkFiles(directory.getPath());

            if(fileMap == null){
                System.out.println("files not found !!!!!!!!!!!!");
            }else{

                VideoDataConsolidator videoDataConsolidator = new VideoDataConsolidator(fileMap);
                VideoHelper video = videoDataConsolidator.collectVideoData();


                //add to database
                databaseInterface.insertOne(video);


                fileMaps.put(video.videoid, fileMap);



            }

        }

        //add all together to index
        indexInterface.insertMany(fileMaps);



    }



}

