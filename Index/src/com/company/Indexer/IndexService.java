package com.company.Indexer;

import com.company.db.Mongo;

/**
 * Created by rahul on 11/06/15.
 */
public class IndexService {

    Index index;
    Mongo mongo;

    public IndexService(){


        mongo = new Mongo();
        index = new Index();

        mongo.connect("localhost", "indexdb");
    }


}
