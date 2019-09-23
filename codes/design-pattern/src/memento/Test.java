package memento;

public class Test {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("开会中");

        // 将数据封装在caretaker中
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        // 改变state并显示
        originator.setState("睡觉中");
        originator.showState();

        // 将数据从caretaker重新导入originator并显示
        originator.setMemento(caretaker.getMemento());
        originator.showState();
    }
}
