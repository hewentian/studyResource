package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private String content;
    private List<Expression> list = new ArrayList<Expression>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Expression> getList() {
        return list;
    }

    public void add(Expression expression) {
        list.add(expression);
    }
}
