package iterator;

public class ListImpl implements List {
    private Object[] list;
    private int index;
    private int size;

    public ListImpl() {
        index = 0;
        size = 0;
        list = new Object[100];
    }

    @Override
    public void add(Object object) {
        list[index++] = object;
        size++;
    }

    @Override
    public Object get(int index) {
        return list[index];
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator iterator() {
        return new IteratorImpl(this);
    }
}
