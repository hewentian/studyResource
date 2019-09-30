package templatemethod;

public class ConcreteClassB extends AbstractClass {
    @Override
    public void primitiveOperation1() {
        System.out.println("ConcreteClassB -> primitiveOperation1");
    }

    @Override
    public void primitiveOperation2() {
        System.out.println("ConcreteClassB -> primitiveOperation2");
    }
}
