package frontend

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray

class SearchController {


    //This is an instance of the rest-client-plugin which makes it easier to do RESTful call to a web service
    //The instance is declared as a bean in resources.groovy
    //An instance is automatically created by grails and injected here via IoC.
    def restClient



    def index() {

        //render(view: 'result')
        //redirect(action: "query")

    }

    def query() {

        String query = params.query

        //send query to RESTful service and get result as json
        def response =restClient.get("http://localhost:4567/index?query=" + query)
        List results = response.json as List
        //results instanceof List

        //replace every entry in the list into video domain object and return list

        results=results.collect {
            new Video(it)


        }
        



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
