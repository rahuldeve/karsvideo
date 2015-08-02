package com.company.Index.Upload;


import com.company.Index.Upload.DataHelperClass.SegmentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 30/07/15.
 */
public class SegmentDataConsolidator {

    Document xml;
    NodeList xmlSegmentNodes;
    List<SegmentHelper> segmentHelperList;
    Map<String, File> fileMap;
    String videoid;


    public SegmentDataConsolidator(Map<String,File> fileMap, String videoid){

        segmentHelperList = new ArrayList<>();
        this.fileMap = fileMap;
        this.videoid = videoid;

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setValidating(true);


        try {

            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            xml = dBuilder.parse(fileMap.get("xml"));
            xmlSegmentNodes = xml.getElementsByTagName("p");


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public List<SegmentHelper> collectSegmentData(){

        for(int i=0; i<xmlSegmentNodes.getLength(); ++i){

            SegmentHelper temp = new SegmentHelper();
            Node xmlSegmentNode = xmlSegmentNodes.item(i);


            getVideoID(temp);
            getSegmentID(temp, i);
            getSegmentBounds(temp, xmlSegmentNode);
            getSegmentScript(temp, xmlSegmentNode);

            segmentHelperList.add(temp);
        }

        getSegmentKeyphrases();
        return segmentHelperList;



    }


    public void getVideoID(SegmentHelper temp){
        temp.videoid = this.videoid;
    }

    public void getSegmentID(SegmentHelper temp, int index){
        temp.segid = temp.videoid + "-" + index;
    }

    public void getSegmentBounds(SegmentHelper temp, Node xmlSegmentNode){
        temp.start =  Double.parseDouble(xmlSegmentNode.getAttributes().getNamedItem("start").getNodeValue());
        temp.stop =  Double.parseDouble(xmlSegmentNode.getAttributes().getNamedItem("end").getNodeValue());
    }

    public void getSegmentScript(SegmentHelper temp, Node xmlSegmentNode){
        temp.script = xmlSegmentNode.getTextContent();
    }


    public void getSegmentKeyphrases(){

        //open the .seg file and read the contents
        File segmentFile = fileMap.get("segfile");


        // see java try-with-resources feature
        try (BufferedReader reader = new BufferedReader(new FileReader(segmentFile))) {

            String currentLine;

            while((currentLine = reader.readLine()) != null){

                if(currentLine.equals("begin"))
                    continue;

                else if(currentLine.matches("[0-9]+:[0-9]+"))
                    continue;

                else if(currentLine.equals("end"))
                    continue;

                else{

                    String keyphrase = currentLine.split(":")[0];
                    double start = Double.parseDouble(currentLine.split(":")[1].split("-")[0]);
                    double stop = Double.parseDouble(currentLine.split(":")[1].split("-")[1]);


                    for(SegmentHelper temp : segmentHelperList){

                        if(temp.isContainedWithin(start, stop))
                            temp.keyphrases.add(keyphrase);
                    }



                }

            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
