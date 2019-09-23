package builder;

public class PersonDirector {
    public Person constructPerson(ConcreteBuilder builder) {
        builder.bulidHead();
        builder.buileBody();
        builder.buildFoot();

        return builder.buildPerson();
    }
}
