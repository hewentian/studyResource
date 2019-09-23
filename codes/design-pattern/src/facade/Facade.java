package facade;

public class Facade {
    private ServiceA serviceA;
    private ServiceB serviceB;
    private ServiceC serviceC;

    public Facade() {
        serviceA = new ServiceAImpl();
        serviceB = new ServiceBImpl();
        serviceC = new ServiceCImpl();
    }

    public void methodA() {
        serviceA.methodA();
        serviceB.methodB();
    }

    public void methodB() {
        serviceB.methodB();
        serviceC.methodC();
    }

    public void methodC() {
        serviceC.methodC();
        serviceA.methodA();
    }
}
