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


        List results = response.json as List

        def thelist = results.collect{ JSONObject vidobject ->

            def seglist=[]

            vidobject.segments.each{

                JSONObject segobject ->

                    Segment segment = new Segment(

                            segid: segobject.segid,
                            start: segobject.start,
                            end: segobject.end,
                            script: segobject.script,
                            keyphrases: segobject.keyphrases
                    )


                    seglist.add(segment)
            }

            Video video = new Video(
                                        name:vidobject.name,
                                        location: vidobject.location,
                                        relevance: vidobject.relevance,
                                        relevance_group: vidobject.relevance_group
                                    )


            seglist.each {Segment segment ->
                video.addToSegments(segment.save(failOnError: true))
            }


            video.save(failOnError: true)

        }





        


        //print results
        render(view: 'result', model: [results:results])








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
