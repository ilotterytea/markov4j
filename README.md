# Markov4J
Markov4J is a Markov chain generator for Java.
Currently, it can be used for generating random sentences from large base of text.

## Usage
### Generating sentences
```java
import kz.ilotterytea.markov4j.models.TextModel;

public class Main {
    public static void main(String[] args) {
        // Create a new text model:
        TextModel model = new TextModel();
        
        // To feed the model, it means to train the text model with sentences:
        model.feed("I don't know who you are, but I find you and markovify you!");
        model.feed("Standing here I realize you are just like me");
        
        // It can operate multiple strings (by separating them with \n):
        model.feed("Violence breeds violence\n" +
                "But in the end it has to be this way");
        
        // Print 5 randomly-generated sentences:
        for (int i = 0; i < 5; i++) {
            Optional<String> sentence = model.generate();
            
            sentence.ifPresent(System.out::println);
        }

        // Print 3 sentences with "I" as the start word:
        for (int i = 0; i < 3; i++) {
            Optional<String> sentence = model.generateWithStartWord("I");

            sentence.ifPresent(System.out::println);
        }
    }
}
```