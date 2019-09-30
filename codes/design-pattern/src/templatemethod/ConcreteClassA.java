package templatemethod;

public class ConcreteClassA extends AbstractClass {
    @Override
    public void primitiveOperation1() {
        System.out.println("ConcreteClassA -> primitiveOperation1");
    }

    @Override
    public void primitiveOperation2() {
        System.out.println("ConcreteClassA -> primitiveOperation2");
    }
}
