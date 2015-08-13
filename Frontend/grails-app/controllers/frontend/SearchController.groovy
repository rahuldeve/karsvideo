package frontend

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class SearchController {


    //This is an instance of the rest-client-plugin which makes it easier to do RESTful call to a web service
    //The instance is declared as a bean in resources.groovy
    //An instance is automatically created by grails and injected here via IoC.
    def restClient



    def index() {

        //render(view: 'result')
        if(Video.count > 0){
            Video.findAll().each {it.delete(flush: true)}

        }

        redirect(action: "query")


        //print Video.get(0)

    }

    def query() {

        String query = params.query

        //send query to RESTful service and get result as json
        def response =restClient.get("http://localhost:4567/index?query=" + query)
        //print response.json

        JSONArray temp = response.json
        //print temp.get("segments")



        List jsonList = response.json as List

        def parsedList = jsonList.collect{ JSONObject resultObject ->

            def videoObject = resultObject.video

            Video video = new Video(

                    videoid: videoObject.videoid,
                    name: videoObject.name,
                    location: videoObject.location,
                    relevance: resultObject.relevance,
                    relevance_group: resultObject.relevance_group,


            )


            def segmentList =[]


            videoObject.segments.each{ JSONObject segmentObject ->

                Segment segment = new Segment(

                        segid: segmentObject.segid,
                        videoid: segmentObject.videoid,
                        start: segmentObject.start,
                        end: segmentObject.stop,
                        script: segmentObject.script,
                        keyphrases: segmentObject.keyphrases

                )

                segmentList.add(segment)

            }


            segmentList.each {Segment segment ->
                video.addToSegments(segment.save(failOnError: true));
            }

            video.save(failOnError: true, flush: true)
        }

        Video.list().each { print it.id}



        


        //print results
        render(view: 'result', model: [results:parsedList])








        //results will contain only id of the segments
        //get the details of the video from the database
        //render the results in a page (nice UI here)

    }

    def playVideo(){

        //Video video = Video.get(params.id)

        //get the url of the video from database
        //String videourl = video.videourl



        //render page to show video


    }

}
