package frontend

class SearchController {


    //This is an instance of the rest-client-plugin which makes it easier to do RESTful call to a web service
    //The instance is declared as a bean in resources.groovy
    //An instance is automatically created by grails and injected here via IoC.
    def restClient



    def index() {

    }

    def query() {

        String query = params.query

        //send query to RESTful service and get result as json
        def response =restClient.get("http://localhost:4567/index?query=" + query)
        def json = response.json




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
