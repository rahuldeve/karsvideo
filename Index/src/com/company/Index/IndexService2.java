package com.company.Index;

import com.company.Index.Retriever2.Retriever;
import com.company.Index.StorageInterfaces.DatabaseInterface;
import com.company.Index.StorageInterfaces.IndexInterface;
import com.company.Index.Uploader2.SingleFileUploader;
import com.company.Index.Uploader2.Uploader;
import com.company.ResultData.ResultObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 28/09/15.
 */
public class IndexService2 {

    DatabaseInterface databaseInterface;
    IndexInterface indexInterface;

    Uploader uploader;
    Retriever retriever;



    public IndexService2(){

        databaseInterface = new DatabaseInterface();
        indexInterface = new IndexInterface();

        uploader = new SingleFileUploader(indexInterface, databaseInterface);
        retriever = new Retriever(indexInterface, databaseInterface);


    }


    public void add(String path){
        uploader.add(path);
    }


    public List<ResultObject> get(String query){
        return retriever.get(query);
    }


}
