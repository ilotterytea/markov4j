package kz.ilotterytea.markov4j.models;

import kz.ilotterytea.markov4j.Chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Representation of a text model using Markov chains.
 * @author ilotterytea
 * @since 1.0
 */
public class TextModel implements BaseModel<String> {
    private final List<Chain<String>> chains;

    /**
     * Representation of a text model using Markov chains.
     * @author ilotterytea
     * @since 1.0
     */
    public TextModel() {
        this.chains = new ArrayList<>();
    }

    @Override
    public void feed(String input) {
    	for (String line : input.split("\n")) {
    		String[] words = line.split(" ");
    		
    		for (int i = 0; i < words.length - 1; i++) {
    			String currentWord = words[i];

                // Break the loop if this is the last word:
    			if (i + 1 >= words.length) {
    				break;
    			}
    			
    			String nextWord = words[i + 1];

                // Create a new chain if it does not exist:
    			if (this.chains.stream().noneMatch(c -> c.getOriginalInput().equals(currentWord))) {
    				this.chains.add(new Chain<>(currentWord));
    			}

                // Add the word to the chain:
    			this.chains.stream().filter(c -> c.getOriginalInput().equals(currentWord)).findFirst().get().add(nextWord);
    		}
    	}
    }

    @Override
    public Optional<String> generate() {
        return generateWithChain(this.chains.get(new Random().nextInt(this.chains.size())), null);
    }

    public Optional<String> generate(Integer fixedLength) {
        return generateWithChain(this.chains.get(new Random().nextInt(this.chains.size())), fixedLength);
    }

    public Optional<String> generateWithStartWord(String startWord, Integer fixedLength) {
        Optional<Chain<String>> optionalPreviousChain = this.chains.stream().filter(c -> c.getOriginalInput().equalsIgnoreCase(startWord)).findAny();

        if (!optionalPreviousChain.isPresent()) {
            return Optional.empty();
        }

        return generateWithChain(optionalPreviousChain.get(), fixedLength);
    }

    public Optional<String> generateWithStartWord(String startWord) {
        Optional<Chain<String>> optionalPreviousChain = this.chains.stream().filter(c -> c.getOriginalInput().equalsIgnoreCase(startWord)).findAny();

        if (!optionalPreviousChain.isPresent()) {
            return Optional.empty();
        }

        return generateWithChain(optionalPreviousChain.get(), null);
    }

    private Optional<String> generateWithChain(Chain<String> chain, Integer fixedLength) {
        StringBuilder sentence = new StringBuilder();

        Chain<String> previousChain = chain;

        while (true) {
            sentence.append(previousChain.getOriginalInput()).append(" ");

            Optional<String> nextWord = previousChain.choose();

            if (!nextWord.isPresent()) {
                break;
            }

            Optional<Chain<String>> nextChain = this.chains
                    .stream()
                    .filter(c -> c.getOriginalInput().equals(nextWord.get()))
                    .findFirst();

            if (!nextChain.isPresent()) {
                sentence.append(nextWord.get());
                break;
            }

            previousChain = nextChain.get();

            if (fixedLength != null && sentence.toString().length() > fixedLength) {
                break;
            }
        }

        if (sentence.toString().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(sentence.toString());
        }
    }

    public List<Chain<String>> getChains() {
        return chains;
    }
}
