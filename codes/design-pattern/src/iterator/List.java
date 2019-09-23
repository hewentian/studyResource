package iterator;

public interface List {
    Iterator iterator();

    int getSize();

    Object get(int index);

    void add(Object object);
}
