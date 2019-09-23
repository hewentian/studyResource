package mediator;

public class ConcreteMediator extends Mediator {
    private ColleagueA colleagueA;
    private ColleagueB colleagueB;

    public ConcreteMediator() {
        colleagueA = new ColleagueA();
        colleagueB = new ColleagueB();
    }

    @Override
    public void notice(String context) {
        if ("boss".equals(context)) {
            colleagueA.action();
        } else if ("client".equals(context)) {
            colleagueB.action();
        }
    }
}
