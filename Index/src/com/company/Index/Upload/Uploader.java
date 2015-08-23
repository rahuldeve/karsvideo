package com.company.Index.Upload;


import com.company.Index.Upload.DataHelperClass.SegmentHelper;
import com.company.Index.Upload.DataHelperClass.VideoHelper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 30/07/15.
 */
public class Uploader {

    File uploadFolderPath;
    Map<String,File> fileMap;
    MongoCollection<Document> videoCollection;


    public Uploader(MongoCollection<Document> videoCollection){

        //this.fileMap = new HashMap<>();
        this.videoCollection = videoCollection;

    }


    public boolean checkFiles(String path) {

        this.fileMap = new HashMap<>();

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

            addToDatabase(video);
            addToIndex(video);

            //System.out.println("video inserted : " + video.videoid);

        }

    }


    public void addToDatabase(VideoHelper video){

        List<Document> segmentDocumentList = new ArrayList<>();

        for(SegmentHelper segment : video.segments){

            Document segmentDocument = new Document("segid", segment.segid)
                    .append("videoid",segment.videoid)
                    .append("start", segment.start)
                    .append("stop", segment.stop)
                    .append("keyphrases", segment.keyphrases)
                    .append("script", segment.script);

            segmentDocumentList.add(segmentDocument);

        }


        Document videoDoc = new Document("videoid", video.videoid)
                .append("name", video.name)
                .append("location", video.location)
                .append("keyphrases", video.keyphrases)
                .append("segments", segmentDocumentList);



        videoCollection.insertOne(videoDoc);


    }




    public void addToIndex(VideoHelper video){


        for(SegmentHelper segmentHelper : video.segments){

            String segid = segmentHelper.segid;
            String script = segmentHelper.script;

            //open corpus.txt file
            File corpusFile = new File("pythonIndex/bm25/text/corpus.txt");


                try {


                    if(!corpusFile.exists())
                        corpusFile.createNewFile();


                    //try to change to nio option
                    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(corpusFile, true));
                    fileWriter.write("# " + segid);
                    fileWriter.newLine();
                    fileWriter.write(script);
                    fileWriter.newLine();
                    fileWriter.flush();
                    fileWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


        }



    }





}
