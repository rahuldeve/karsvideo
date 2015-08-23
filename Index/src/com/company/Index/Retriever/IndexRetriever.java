package com.company.Index.Retriever;

import java.io.*;
import java.util.*;

/**
 * Created by rahul on 3/08/15.
 */
public class IndexRetriever {

    public IndexRetriever()
    {

    }

    public Map<String,Double> queryIndex(String query){

        List<String> retrievedSegid = new ArrayList<>();

        Map<String,Double> segIDRelevanceScores = new HashMap<>();

        //open queries.txt file
        //try nio file

        File queryFile = new File("pythonIndex/bm25/text/queries.txt");


        try {


            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(queryFile));
            fileWriter.write(query);
            fileWriter.newLine();
            fileWriter.flush();
            fileWriter.close();


            ProcessBuilder processBuilder = new ProcessBuilder("/home/rahul/Project/karsvideo/Index/pythonIndex/bm25/main_executing_shell.sh");


            Process pythonBM25Process = processBuilder.start();
            pythonBM25Process.waitFor();

            File outputFile = new File("/home/rahul/Project/karsvideo/Index/pythonIndex/bm25/result/current_output.txt");

            if(!outputFile.exists())
                System.err.print("Error");

            else{

                BufferedReader fileReader = new BufferedReader(new FileReader(outputFile));
                String line;

                while ( (line = fileReader.readLine()) != null){

                    String segid = line.split("\t")[1];
                    Double score = Double.parseDouble(line.split("\t")[3]);

                    segIDRelevanceScores.put(segid,score);

                }


            }




        } catch (IOException e) {
            e.printStackTrace();
        }


        catch (InterruptedException e) {
            e.printStackTrace();
        }



        return segIDRelevanceScores;

    }
}
