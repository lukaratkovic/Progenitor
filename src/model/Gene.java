package model;

public abstract class Gene<T> {
    public T value;
    public Gene(){}
    public abstract Gene mutate();

    public abstract Gene getInstance();

    public abstract Gene clone();

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "value=" + value +
                '}';
    }
}
