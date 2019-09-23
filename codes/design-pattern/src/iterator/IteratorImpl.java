package iterator;

public class IteratorImpl implements Iterator {
    private int index;
    private List list;

    public IteratorImpl(List list) {
        index = 0;
        this.list = list;
    }

    @Override
    public void first() {
        index = 0;
    }

    @Override
    public void last() {
        index = list.getSize() - 1;
    }

    @Override
    public boolean hasNext() {
        return index < list.getSize();
    }

    @Override
    public Object next() {
        Object object = list.get(index);
        index++;
        return object;
    }
}
