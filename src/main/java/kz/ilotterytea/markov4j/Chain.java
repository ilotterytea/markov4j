package kz.ilotterytea.markov4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Markov chain with the original input and a weight map with the other inputs and the number of times they come after the original input.
 * @author ilotterytea
 * @since 1.0
 */
public class Chain<T> {
    /**
     * The original input.
     */
    private final T originalInput;

    /**
     * A weight map with the inputs and the number of times they come after the original input.
     */
    private final ConcurrentHashMap<T, Float> weightMap;

    /**
     * A markov chain with the original input and a weight map with the other inputs and the number of times they come after the original input.
     * @param input Original input
     * @author ilotterytea
     * @since 1.0
     */
    public Chain(T input) {
        this.originalInput = input;
        this.weightMap = new ConcurrentHashMap<>();
    }

    /**
     * Add an input to the weight map.
     * @param input Input
     */
    public void add(T input) {
        if (!this.weightMap.containsKey(input)) {
            this.weightMap.put(input, 0f);
        }

        this.weightMap.put(input, this.weightMap.get(input) + 1f);
    }

    /**
     * Choose an input from the weight map based on total weight and randomness.
     * @return input value or null if something has gone wrong.
     */
    public Optional<T> choose() {
    	float totalWeight = 0.0f;
    	
    	for (float weight : this.weightMap.values()) {
    		totalWeight += weight;
    	}
    	
    	double random = Math.random() * totalWeight;
    	
    	for (Map.Entry<T, Float> entry : this.weightMap.entrySet()) {
    		random -= entry.getValue();
    		
    		if (random <= 0.0f) {
    			return Optional.of(entry.getKey());
    		}
    	}
    	
    	return Optional.empty();
    }

    /**
     * Get the original input.
     * @return the original input.
     */
    public T getOriginalInput() {
        return originalInput;
    }

    /**
     * Get the weight map.
     * @return the weight map.
     */
    public ConcurrentHashMap<T, Float> getWeightMap() {
        return weightMap;
    }
}
