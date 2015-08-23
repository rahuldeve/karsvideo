package com.company;
import static spark.Spark.*;

import com.company.Index.IndexService;
import com.company.ResultData.ResultObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Main {

        public static void main(String[] args) {


            //List<ResultObject> parsedList = new ArrayList<>();
            IndexService indexService = new IndexService();
            //indexService.uploadVideo("/home/rahul/test/videos/24Trees");
            //indexService.bulkUploadVideos("/home/rahul/test/videos");


            List<ResultObject> parsedList = indexService.indexQuery("trees");

            for(ResultObject resultObject : parsedList){
                System.out.println(resultObject.relevance);
            }

            //indexService.close();






            Gson gson = new GsonBuilder().setPrettyPrinting().create();



            get("/index", (req, res) ->

                            //indexService.indexQuery(req)

                            indexService.indexQuery(req.queryParams("query")), gson::toJson
            );


            get("/bulk", (req, res) -> {
                indexService.bulkUploadVideos("/home/rahul/test/videos");
                //res.redirect("asd");

                return "yay";
            });



        }
}
