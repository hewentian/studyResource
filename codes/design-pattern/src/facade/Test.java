package facade;

public class Test {
    public static void main(String[] args) {
        ServiceA serviceA = new ServiceAImpl();
        ServiceB serviceB = new ServiceBImpl();
        serviceA.methodA();
        serviceB.methodB();

        // facade
        System.out.println("===== facade =====");
        Facade facade = new Facade();
        facade.methodA();
    }
}
