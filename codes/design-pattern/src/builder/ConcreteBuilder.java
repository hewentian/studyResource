package builder;

public class ConcreteBuilder implements Builder {
    private Person person;

    public ConcreteBuilder() {
        person = new Man();
    }

    @Override
    public void bulidHead() {
        person.setHead("building man's head");
    }

    @Override
    public void buileBody() {
        person.setBody("building man's body");
    }

    @Override
    public void buildFoot() {
        person.setFoot("building man's foot");
    }

    public Person buildPerson() {
        return person;
    }
}
