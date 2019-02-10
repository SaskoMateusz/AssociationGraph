package pl.edu.agh.semantics;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SpellCheckerTest {

    @Test
    void checkIfCheckingCorrect() {
        assertThat(SpellChecker.shouldBeConsideredIdentical("duzy", "duz6y")).isTrue();
        assertThat(SpellChecker.shouldBeConsideredIdentical("tata", "tata]")).isTrue();
        assertThat(SpellChecker.shouldBeConsideredIdentical("twój", "twójk")).isTrue();
        assertThat(SpellChecker.shouldBeConsideredIdentical("praca", "[RACA")).isTrue();
        assertThat(SpellChecker.shouldBeConsideredIdentical("wygoda", "wygodn")).isTrue();
        assertThat(SpellChecker.shouldBeConsideredIdentical("duck", "truck")).isFalse();
    }

    @Test
    void checkDuplicates() {
        Map<String, Integer> associations = new HashMap<>();
        associations.put("duzy", 75);
        associations.put("duz6y", 15);
        associations.put("praca", 80);
        associations.put("[RACA", 4);
        associations.put("pokoj", 5);
        associations.put("PRACn", 3);
        Map<String, Integer> checkedAssociations = SpellChecker.removeDuplicates(associations);
        assertThat(checkedAssociations.size()).isEqualTo(3);
        assertThat(checkedAssociations.get("duzy")).isEqualTo(90);
        assertThat(checkedAssociations.get("praca")).isEqualTo(87);
        assertThat(checkedAssociations.get("pokoj")).isEqualTo(5);
    }
}