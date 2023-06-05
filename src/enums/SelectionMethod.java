package enums;

/**
 * Selection method determines the logic for selecting a parent chromosome
 */
public enum SelectionMethod {
    /**
     * Tournament selection randomly selects K individuals from the population, then chooses the best of those
     */
    TOURNAMENT,
    /**
     * Roulette selection randomly selects an individual from the population. An individual's likelyhood of being picked is proportional to its fitness.
     */
    ROULETTE,
    /**
     * Rank selection randomly selects an individual from the population. An individual's likelyhood of being picked is proportional to its position in the generation sorted by fitness.
     */
    RANK
}
