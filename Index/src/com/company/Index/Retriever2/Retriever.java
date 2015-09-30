package com.company.Index.Retriever2;

import com.company.Index.DataHelperClass.VideoHelper;
import com.company.Index.StorageInterfaces.DatabaseInterface;
import com.company.Index.StorageInterfaces.IndexInterface;
import com.company.ResultData.DataParser;
import com.company.ResultData.ResultObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 28/09/15.
 */
public class Retriever {

    IndexInterface indexInterface;
    DatabaseInterface databaseInterface;

    public Retriever(IndexInterface indexInterface, DatabaseInterface databaseInterface){
        this.indexInterface = indexInterface;
        this.databaseInterface = databaseInterface;
    }

    public List<ResultObject> get(String query){

        //get from index interface
        //result will mostly be video or segment id in string

        List<String> indexResults = new ArrayList<>();
        List<VideoHelper> databaseResults = databaseInterface.get(indexResults);

        //pass it to DataParser to get it in required format
        List<ResultObject> parsedList = new ArrayList<>();
        return parsedList;
    }
}
