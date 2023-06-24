package enums;

/**
 * End condition determines when the execution of the generic algorithm stops.
 */
public enum EndCondition {
    /**
     * Genetic algorithm execution stops when a defined target fitness is reached or surpassed.
     */
    TARGET_FITNESS,
    /**
     * Genetic algorithm execution stops when a defined number of generations is reached.
     */
    MAX_GENERATIONS
}
