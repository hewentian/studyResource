package interpreter;

public class AdvanceExpression extends Expression {
    @Override
    public void interpret(Context ctx) {
        System.out.println("This is advance expression.");
    }
}
