package interpreter;

public class Test {
    public static void main(String[] args) {
        Context ctx = new Context();
        ctx.add(new SimpleExpression());
        ctx.add(new AdvanceExpression());
        ctx.add(new SimpleExpression());

        for (Expression expression : ctx.getList()) {
            expression.interpret(ctx);
        }
    }
}
