package flyweight;

public class Test {
    public static void main(String[] args) {
        Flyweight flyweight1 = FlyweightFactory.getFlyweitht("a");
        flyweight1.action(1);

        Flyweight flyweight2 = FlyweightFactory.getFlyweitht("a");
        System.out.println(flyweight1 == flyweight2);

        Flyweight flyweight3 = FlyweightFactory.getFlyweitht("b");
        flyweight3.action(2);

        Flyweight flyweight4 = FlyweightFactory.getFlyweitht("c");
        flyweight4.action(3);

        Flyweight flyweight5 = FlyweightFactory.getFlyweitht("d");
        flyweight5.action(4);

        System.out.println(FlyweightFactory.getSize());
    }
}
