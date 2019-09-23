package proxy;

public class Proxy implements Subject {
    private Subject subject;

    public Proxy() {
        System.out.println("这是代理类");
        subject = new RealSubject();
    }

    @Override
    public void action() {
        System.out.println("代理开始");
        subject.action();
        System.out.println("代理结束");
    }
}
