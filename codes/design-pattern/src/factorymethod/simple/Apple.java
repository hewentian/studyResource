package factorymethod.simple;

public class Apple extends Fruit {
    private String name;

    public Apple() {
        this.name = "Apple";
    }

    public String getName() {
        return this.name;
    }
}
