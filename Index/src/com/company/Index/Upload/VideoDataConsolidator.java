package com.company.Index.Upload;

import com.company.Index.Upload.DataHelperClass.VideoHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**+
 * Created by rahul on 30/07/15.
 */
public class VideoDataConsolidator {

    Map<String, File> fileMap;

    public VideoDataConsolidator(Map<String,File> fileMap){
        this.fileMap = fileMap;



    }

    public VideoHelper collectVideoData(){


        VideoHelper temp = new VideoHelper();

        getVideoName(temp);
        getVideoID(temp);
        getLocation(temp);

        SegmentDataConsolidator segmentDataConsolidator = new SegmentDataConsolidator(fileMap, temp.videoid);
        temp.segments = segmentDataConsolidator.collectSegmentData();

        return temp;



    }

    public void getVideoID(VideoHelper temp){
        temp.videoid = fileMap.get("video").getName().replaceFirst(".mp4","");
    }

    public void getVideoName(VideoHelper temp){
        temp.name = fileMap.get("video").getName();
    }

    public void getLocation(VideoHelper temp){
        //send the video file to webserver and get URL

        String fileURL = "http://localhost:80/" + temp.name + "/" + temp.name + ".mp4";

        //test data
        temp.location = "asd";

        //copy to apache document folder
        //first make sure the user has enough permissions to write to folder

        /*
        File videofile = fileMap.get("video");
        File copyfile = new File("/srv/http/" + temp.name + "/" + temp.name + ".mp4");

        try {

            Files.createDirectories(Paths.get("/srv/http/" + temp.name));
            Files.copy(videofile.toPath(), copyfile.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        temp.location = fileURL;
        */
    }




}
