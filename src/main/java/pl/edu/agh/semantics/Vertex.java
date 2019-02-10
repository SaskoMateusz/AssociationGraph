package pl.edu.agh.semantics;

import java.util.ArrayList;
import java.util.List;

final class Vertex {

    private final String word;
    private final List<WeightPair<Vertex>> neighbour;

    Vertex(String word) {
        this.word = word;
        neighbour = new ArrayList<>();
    }

    List<WeightPair<Vertex>> getNeighbour() {

        return neighbour;
    }

    void addNeighbour(Vertex to, double weight) {

        neighbour.add(new WeightPair<>(to, weight));
    }

    String getWord() {

        return word;
    }
}
