package pl.edu.agh.semantics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class GraphDemo {

    private static final Logger log = LoggerFactory.getLogger(Graph.class);

    private static DataParser parser = new DataParser();


    public static void main(String[] args) {

        List<String> words = new ArrayList<>();
        List<Map<String, Integer>> associations = new ArrayList<>();

        //load given data
        words.add("ciezka");
        words.add("dluga");
        words.add("dobra");
        words.add("dom");
        words.add("duze");
        words.add("ksiazka");
        words.add("mieszkanie");
        words.add("moj");
        words.add("nauka");
        words.add("pokoj");
        words.add("praca");
        words.add("rodzinny");


        for(String s: words){
            String fileName = ("./data/" + s +".csv");
            Map<String, Integer> a = parser.readAssociationsFromFile(fileName);
            associations.add(a);
        }
        //create graph
        Graph graph = Graph.BuildofMaps(associations, words);

        //check few paths
        log.info(graph.getShortestPath("ciezka", "praca").toString());
        log.info(graph.getShortestPath("nauka", "miłość").toString());
        log.info(graph.getShortestPath("dobra", "nauka").toString());
        log.info(graph.getShortestPath("ciezka", "duży").toString());
        log.info(graph.getShortestPath("moj", "dobry").toString());
    }

}
