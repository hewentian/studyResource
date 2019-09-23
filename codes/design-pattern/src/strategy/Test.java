package strategy;

public class Test {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStrategy1());
        context.doMethod();

        context = new Context(new ConcreteStrategy2());
        context.doMethod();
    }
}
