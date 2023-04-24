package Model;

public interface Gene <T> {
    public Gene<T> newGene();
    public Gene<T> newGene(T value);
}
