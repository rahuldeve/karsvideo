package frontend

class Segment {

    String segid;
    String videoid;
    double start;
    double end;
    String script;


    static constraints = {
    }

    static mapping = {
        script sqlType: "text"
    }

    static hasMany = [keyphrases:String]
    static belongsTo = Video
}
