package interpreter;

public class SimpleExpression extends Expression {
    @Override
    public void interpret(Context ctx) {
        System.out.println("This is simple expression.");
    }
}
