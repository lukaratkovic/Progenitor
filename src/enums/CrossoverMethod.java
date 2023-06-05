package enums;

/**
 * Crossover method determines how a new Chromosome is created from two parents Chromosomes.
 */
public enum CrossoverMethod {
    /**
     * One point crossover takes genes in range [0, N> from the first parent, and [N, END] of the second parent. N is a random point in range <0, END>.
     */
    ONE_POINT,
    /**
     * Two point crossover takes genes in ranges [0, N1> and [N2, END] of the first parent, and [N1, N2> of the second. N1 and N2 are random, distinct points in range <0, END>.
     */
    TWO_POINT,
    /**
     * Uniform crossover randomly takes genes from either parent.
     */
    UNIFORM
}
