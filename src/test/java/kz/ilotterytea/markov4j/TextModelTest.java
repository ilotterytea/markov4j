package kz.ilotterytea.markov4j;

import kz.ilotterytea.markov4j.models.TextModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author ilotterytea
 * @since 1.0
 */
public class TextModelTest {

    private TextModel getModel() {
        TextModel model = new TextModel();
        model.feed("Hello! My name is Jeff.");
        model.feed("We will see... We will see... and the winner gets a tea!");
        model.feed("If you see this we won");
        model.feed("Do not read this or you will be markovified at 3 am!");

        return model;
    }

    @Test
    public void generateRandomText() {
        TextModel model = getModel();

        assert !model.getChains().isEmpty();

        for (int i = 0; i < 5; i++) {
            Optional<String> line = model.generate();

            if (line.isPresent()) {
                System.out.println("Random: " + line.get());
            } else {
                assert false;
            }
        }

        assert true;
    }

    @Test
    public void generateTextWithStartWord() {
        TextModel model = getModel();
        String startWord = "will";

        assert !model.getChains().isEmpty();

        for (int i = 0; i < 5; i++) {
            Optional<String> line = model.generateWithStartWord(startWord);

            if (line.isPresent()) {
                System.out.println("\"" + startWord + "\" start word: " + line.get());
            } else {
                assert false;
            }
        }

        assert true;
    }
}
