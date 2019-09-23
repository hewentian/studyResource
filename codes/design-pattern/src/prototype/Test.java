package prototype;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Prototype prototype1 = new ConcretePrototype("he");
        Prototype prototype2 = (Prototype) prototype1.clone();

        System.out.println(prototype1.getName());
        System.out.println(prototype2.getName());
    }
}
