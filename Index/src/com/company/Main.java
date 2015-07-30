package com.company;
import static spark.Spark.*;

import com.company.Data.Segment;
import com.company.Data.Video;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

        public static void main(String[] args) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();



            List<Video> results = new ArrayList<>();


            for(int i=0;i<10;++i) {



                if (i * 10 < 30) {


                    List<Segment> seglist = new ArrayList<>();

                    for (int j = 0; j < 5; ++j) {

                        seglist.add(new Segment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new Video("asd0", "asd1", Arrays.asList("a", "b", "c"), i * 10, "SR", seglist));

                }


                else if (i * 10 < 60) {
                    List<Segment> seglist = new ArrayList<>();

                    for (int j = 0; j < 5; ++j) {

                        seglist.add(new Segment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new Video("asd0", "asd1", Arrays.asList("a", "b", "c"), 10 * i, "MR", seglist));
                }


                else {

                    List<Segment> seglist = new ArrayList<>();

                    for (int j = 0; j < 5; ++j) {

                        seglist.add(new Segment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new Video("asd0", "asd1", Arrays.asList("a", "b", "c"), 10 * i, "HR", seglist));
                }
            }

            System.out.print(results.size());















            get("/index", (req, res) ->

                            results, gson::toJson
            );



        }
}
