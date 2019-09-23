package factorymethod.simple;

public class Farmer {
    public static Fruit produce(String type) {
        Fruit f = null;

        if (type.equals("apple")) {
            f = new Apple();
        } else if (type.equals("banana")) {
            f = new Banana();
        }

        return f;
    }
}
