package com.company.Index.Uploader2;

import java.io.File;
import java.util.Map;

/**
 * Created by rahul on 28/09/15.
 */
public interface Uploader {

    Map<String, File> checkFiles(String path);

    void insertOne(String path);

    void insertMany(String basePath);
}
