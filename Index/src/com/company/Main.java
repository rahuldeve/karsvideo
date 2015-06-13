package com.company;
import static spark.Spark.*;

import com.company.data.Video;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main {

        public static void main(String[] args) {

            Gson gson = new Gson();


            List<Video> results = new ArrayList<>();

            for(int i=0;i<10;++i)
            {
                if(i*10<30)
                    results.add(new Video("asd0","asd1","asd2",10*i,"SR"));

                else if(i*10<60)
                    results.add(new Video("asd0","asd1","asd2",10*i,"MR"));
                else
                    results.add(new Video("asd0","asd1","asd2",10*i,"HR"));
            }












            get("/index", (req, res) ->
                    results,gson::toJson
            );






            //get("/index", (req,res) -> )
        }
}
