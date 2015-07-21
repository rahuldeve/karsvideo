package frontend

class Video {

    String name
    String location
    //String videoDetails
    //String vidImageLocation

    int relevance
    String relevance_group

    static constraints = {
    }

    static  hasMany = [segments:Segment, keyphrases:String]
    //static hasMany = [segments:Segment]

}
