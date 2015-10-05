package com.company.Index.StorageInterfaces.Datastructures;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkGEXF;
import org.graphstream.stream.file.FileSourceGEXF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rahul on 4/10/15.
 */
public class GraphInterface {

    public static final String MAIN_KEYWORDS = "mainkeywords";
    public static final String SUB_KEYWORDS = "subkeywords";

    public static final String COMMON_KEYWORD_TAG = "commonkeyword";
    public static final String RELATED_TAG = "related";
    public static final String DIVERSE_TAG = "diverrse";
    public static final String ORPHANED_TAG = "orphaned";
    public static final String CLUSTER_TAG = "cluster";

    public static final String GRAPH_FILE_PATH = "graph.gexf";      // TODO: change the path later

    MultiGraph graph;

    public GraphInterface(){

    }

    public void insertOne(String videoid, List<String> mainKeywords, List<String> subKeywords){

        Node node = graph.addNode(videoid);
        node.addAttribute(MAIN_KEYWORDS, mainKeywords);
        node.addAttribute(SUB_KEYWORDS, subKeywords);

        //form related edges
        makeSingleNodeRelatedConnections(node, mainKeywords, subKeywords);

        //form diverse edges
        makeSingleNodeDiverseConnections(node, mainKeywords, subKeywords);

    }



    public void insertMany(Map<String, Map<String, List<String>>> keywordMapList){

        //TODO : add code here

    }

    public void loadFromFile(){

        FileSourceGEXF fileSourceGEXF = new FileSourceGEXF();
        fileSourceGEXF.addSink(graph);
        try {
            fileSourceGEXF.readAll(GRAPH_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(){

        FileSinkGEXF fileSinkGEXF = new FileSinkGEXF();
        try {
            fileSinkGEXF.writeAll(graph, GRAPH_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //function for adding one node at a a time
    private void makeSingleNodeRelatedConnections(Node node, List<String> mainKeywords, List<String> subKeywords){

        boolean edgeFormed = false;

        //make main - main connections
        Node nodeA = node;
        List<String> mainKeywordsA = mainKeywords;

        for( Node nodeB : graph){

            List<String> mainKeywordsB = nodeB.getAttribute(MAIN_KEYWORDS);
            List<String> commonKeywords = getCommonKeywords(mainKeywordsA, mainKeywordsB, 3);

            if(commonKeywords != null){
                for(String keyword : commonKeywords){

                    Edge edge = graph.addEdge(nodeA.getId() + nodeB.getId() + keyword, nodeA, nodeB);
                    edge.addAttribute(COMMON_KEYWORD_TAG, keyword);
                    edge.addAttribute(RELATED_TAG);
                    edgeFormed = true;

                }
            }
        }

        //if the inserted node is still an orphan relax constrains
        if(!edgeFormed){

            nodeA.addAttribute(ORPHANED_TAG);
            List<String> keywordsA = mainKeywords;
            keywordsA.addAll(subKeywords);


            for(Node nodeB : graph){

                List<String> mainKeywordsB = nodeB.getAttribute(MAIN_KEYWORDS);
                List<String> subKeywordsB = nodeB.getAttribute(SUB_KEYWORDS);
                mainKeywords.addAll(subKeywordsB);

                List<String> keywordsB = mainKeywordsB;

                List<String> commonKeywords = getCommonKeywords(keywordsA, keywordsB, 4);

                for(String keyword : commonKeywords){

                    Edge edge = graph.addEdge(nodeA.getId() + nodeB.getId() + keyword, nodeA, nodeB);
                    edge.addAttribute(COMMON_KEYWORD_TAG, keyword);
                    edge.addAttribute(RELATED_TAG);

                }
            }
        }
    }


    private void makeSingleNodeDiverseConnections(Node node, List<String> mainKeywords, List<String> subKeywords) {

        Node nodeA = node;

        List<String> mainkeywordsA = mainKeywords;
        List<String> subKeywordsA = subKeywords;


        //get the clusters by ignoring diverse edges
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.setCutAttribute(DIVERSE_TAG);
        connectedComponents.init(graph);
        connectedComponents.compute();
        connectedComponents.setCountAttribute(CLUSTER_TAG);


        Map<Integer, List<Node>> clusterMap = graph.getNodeSet().stream()
                .collect(Collectors.groupingBy(p_node -> p_node.getAttribute(CLUSTER_TAG)));

        boolean skip = false;

        for(Integer c1 : clusterMap.keySet()){

            List<Node> nodeCluster = clusterMap.get(c1);

            if(nodeCluster.size() > 1){

                for(Node nodeB : nodeCluster){

                    List<String> mainKeywordsB = nodeB.getAttribute(MAIN_KEYWORDS);
                    List<String> subKeywordsB = nodeB.getAttribute(SUB_KEYWORDS);

                    //look for main -> sub relation from  A -> B
                    List<String> commonKeywordsAtoB = getCommonKeywords(mainkeywordsA, subKeywordsB, -1);

                    //look for main -> sub relations from B -> A
                    List<String> commonKeywordsBtoA = getCommonKeywords(subKeywordsA, mainKeywordsB, -1);



                    if(commonKeywordsAtoB != null){
                        for(String keyword : commonKeywordsAtoB){

                            Edge edge = graph.addEdge(nodeA.getId() + nodeB.getId() + keyword, nodeA, nodeB);
                            edge.addAttribute(COMMON_KEYWORD_TAG, keyword);
                            edge.addAttribute(DIVERSE_TAG);
                            skip = true;

                        }
                    }



                    if(commonKeywordsBtoA != null){
                        for(String keyword : commonKeywordsBtoA){

                            Edge edge = graph.addEdge(nodeA.getId() + nodeB.getId() + keyword, nodeA, nodeB);
                            edge.addAttribute(COMMON_KEYWORD_TAG, keyword);
                            edge.addAttribute(DIVERSE_TAG);
                            skip = true;

                        }
                    }
                }
            }


            if(skip == true){
                skip = false;
                break;
            }
        }
    }



    private void makeMultiNodeRelatedConnections(){
        // TODO : fill code here
    }

    private void makeMultiNodeDiverseConnections(){
        // TODO : fill code here
    }



    private List<String> getCommonKeywords(List<String> keywordsA, List<String> keywordsB, int threshold){

        List<String> commonKeywords = new ArrayList<>();
        int count = 0;

        for(String wordA : keywordsA){
            for(String wordB : keywordsB){



                boolean both_same = false;
                boolean A_contains_B = false;
                boolean B_contains_A = false;


                //both words are same
                if(wordA.equals(wordB))
                    both_same = true;

                //A has word B
                if(wordA.contains(wordB))
                    A_contains_B = true;

                //B has word A
                if(wordB.contains(wordA))
                    B_contains_A = true;



                if(both_same){
                    commonKeywords.add(wordA);
                    ++count;
                }

                else if(A_contains_B){
                    commonKeywords.add(wordA);
                    ++count;
                }

                else if (B_contains_A) {
                    commonKeywords.add(wordB);
                    ++count;
                }
                else{

                }

            }

        }



        if(threshold != -1){

            if(count >= threshold)
                return commonKeywords;
            else
                return null;
        }

        else{
            return commonKeywords;
        }



    }


}
