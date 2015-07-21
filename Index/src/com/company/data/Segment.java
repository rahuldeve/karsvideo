package com.company.data;

import java.util.List;

/**
 * Created by rahul on 18/06/15.
 */
public class Segment {

    String segid;
    double start;
    double end;
    List<String> keyphrases;
    String script;

    public Segment(String segid, float start, float end, List<String> keyparases, String script){

        this.segid = segid;
        this.start = start;
        this.end = end;
        this.keyphrases = keyparases;
        this.script = script;
    }



}
