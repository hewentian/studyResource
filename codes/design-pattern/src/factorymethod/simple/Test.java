package factorymethod.simple;

public class Test {
    public static void main(String args[]) {
        Fruit f = Farmer.produce("apple");
        System.out.println("Good,I get a " + f.getName());
    }
}
