package kz.ilotterytea.markov4j.models;

import java.util.Optional;

/**
 * A base interface for representing Markov chains models.
 * @author ilotterytea
 * @since 1.0
 */
public interface BaseModel<T> {
    /**
     * Feed the model with input.
     * @param input Input value.
     */
    void feed(T input);

    /**
     * Generate output based on the weight of values and randomness.
     * @return value or null if something has gone wrong.
     */
    Optional<T> generate();
}
