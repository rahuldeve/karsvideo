package com.company;
import static spark.Spark.*;

<<<<<<< HEAD
import com.company.Index.DataHelperClass.SegmentHelper;
import com.company.Index.DataHelperClass.VideoHelper;
=======
import com.company.Index.IndexService;
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e
import com.company.ResultData.ResultObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e
import java.util.List;

public class Main {

        public static void main(String[] args) {

<<<<<<< HEAD
            /*

            IndexService indexService = new IndexService();
            indexService.uploadVideo("/home/rahul/test/videos/24Trees");

            indexService.indexQuery("24trees-0");

            indexService.close();
            */



            Gson gson = new GsonBuilder().setPrettyPrinting().create();


            List<ResultObject> results = new ArrayList<>();

            //////////////////////////////////////////////////////////////////////////////
            VideoHelper tempVideo = new VideoHelper();
            tempVideo.videoid = "asd";
            tempVideo.name = "asd";
            tempVideo.location = "qwert/dfsdf";
            tempVideo.keyphrases = new ArrayList<>();
            tempVideo.segments = new ArrayList<>();

            for(int i = 0; i<5; ++i){

                SegmentHelper tempSegment = new SegmentHelper();
                tempSegment.segid = "seg1";
                tempSegment.videoid = "asd";
                tempSegment.script = "Asdasdasdasd";
                tempSegment.start = 5;
                tempSegment.stop = 10;

                tempVideo.segments.add(tempSegment);
            }

            ResultObject asd = new ResultObject(tempVideo,10,"MR");;
            results.add(asd);



            //////////////////////////////////////////////////////////////////////////////

            VideoHelper tempVideo2 = new VideoHelper();
            tempVideo2.videoid = "asd";
            tempVideo2.name = "asd";
            tempVideo2.location = "qwert/dfsdf";
            tempVideo2.keyphrases = new ArrayList<>();
            tempVideo2.segments = new ArrayList<>();

            for(int i = 0; i<5; ++i){

                SegmentHelper tempSegment2 = new SegmentHelper();
                tempSegment2.segid = "seg1";
                tempSegment2.videoid = "asd";
                tempSegment2.script = "Asdasdasdasd";
                tempSegment2.start = 5;
                tempSegment2.stop = 10;

                tempVideo2.segments.add(tempSegment2);
            }

            ResultObject asd2 = new ResultObject(tempVideo2,10,"MR");;
            results.add(asd2);


            get("/index", (req, res) ->

                            results, gson::toJson
            );




            /*
            List<ResultVideo> results = new ArrayList<>();


            for(int i=0;i<10;++i) {



                if (i * 10 < 30) {


                    List<ResultSegment> seglist = new ArrayList<>();

                    for (int j = 0; j < 5; ++j) {

                        seglist.add(new ResultSegment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new ResultVideo("asd0", "asd1", Arrays.asList("a", "b", "c"), i * 10, "SR", seglist));

                }
=======
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e

            //List<ResultObject> parsedList = new ArrayList<>();
            IndexService indexService = new IndexService();
            //indexService.uploadVideo("/home/rahul/test/videos/24Trees");
            //indexService.bulkUploadVideos("/home/rahul/test/videos");

<<<<<<< HEAD
                else if (i * 10 < 60) {
                    List<ResultSegment> seglist = new ArrayList<>();
=======
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e

            List<ResultObject> parsedList = indexService.indexQuery("trees");

<<<<<<< HEAD
                        seglist.add(new ResultSegment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new ResultVideo("asd0", "asd1", Arrays.asList("a", "b", "c"), 10 * i, "MR", seglist));
                }


                else {

                    List<ResultSegment> seglist = new ArrayList<>();

                    for (int j = 0; j < 5; ++j) {

                        seglist.add(new ResultSegment("seg0"+i+j, 1, 5, Arrays.asList("a", "b", "c"), "them scripts"));

                    }

                    results.add(new ResultVideo("asd0", "asd1", Arrays.asList("a", "b", "c"), 10 * i, "HR", seglist));
                }
=======
            for(ResultObject resultObject : parsedList){
                System.out.println(resultObject.relevance);
>>>>>>> 73ff3ea95ac82399f204e5f3af212f951e7d7d2e
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


            get("/test", (req,res) -> {




            });
            */



        }
}
