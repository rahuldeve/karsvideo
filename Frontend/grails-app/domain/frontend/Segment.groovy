package frontend

class Segment {

    String segid;
    float start;
    float end;
    String script;


    static constraints = {
    }

    static hasMany = [keyphrases:String]
    static belongsTo = Video
}
