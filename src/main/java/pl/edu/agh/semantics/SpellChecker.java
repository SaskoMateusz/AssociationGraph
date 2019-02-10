package pl.edu.agh.semantics;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SpellChecker {

    private static final Logger log = LoggerFactory.getLogger(SpellChecker.class);

    private SpellChecker() {
    }

    //@VisibleForTesting
    static boolean shouldBeConsideredIdentical(String first, String second) {
        Integer levenshteinDistance = LevenshteinDistance.getDefaultInstance()
                .apply(first.toLowerCase(), second.toLowerCase());
        if (levenshteinDistance <= 1) {
            log.info("Words considered identical: {} and {}", first, second);
            return true;
        } else if(first.length() > 5 && second.length() > 5 && levenshteinDistance <= 2){
            log.info("Words considered identical: {} and {}", first, second);
            return true;
        }
        return false;
    }

    static Map<String, Integer> removeDuplicates(Map<String, Integer> associations) {
        Map<String, Integer> preProcessingResult = SpellChecker.removeDuplicatesFromMap(associations);
        return SpellChecker.removeDuplicatesFromMap(preProcessingResult);
    }

    private static Map<String, Integer> removeDuplicatesFromMap(Map<String, Integer> associations) {
        Map<String, Integer> associationsWithoutDuplicates = new ConcurrentHashMap<>();
        associations.entrySet().forEach(toBeAdded -> {
            boolean isMapUpdated = false;
            for (Map.Entry<String, Integer> existing : associationsWithoutDuplicates.entrySet()) {
                if (shouldBeConsideredIdentical(existing.getKey(), toBeAdded.getKey())) {
                    if (existing.getValue() >= toBeAdded.getValue()) {
                        associationsWithoutDuplicates.put(existing.getKey(),
                                existing.getValue() + toBeAdded.getValue());
                    } else {
                        associationsWithoutDuplicates.put(toBeAdded.getKey(),
                                existing.getValue() + toBeAdded.getValue());
                        associationsWithoutDuplicates.remove(existing.getKey());
                    }
                    isMapUpdated = true;
                    break;
                }
            }
            if (!isMapUpdated) {
                associationsWithoutDuplicates.put(toBeAdded.getKey(), toBeAdded.getValue());
            }
        });
        assert associations.values().stream().mapToInt(Integer::intValue).sum()
                == associationsWithoutDuplicates.values().stream().mapToInt(Integer::intValue).sum();
        associationsWithoutDuplicates.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> log.info("Association: {} Occurrences: {}", e.getKey(), e.getValue()));
        return associationsWithoutDuplicates;
    }
}