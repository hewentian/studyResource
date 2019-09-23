package bridge;

public class Man extends Person {
    public Man() {
        setType("Man");
    }

    @Override
    public void dress() {
        Clothing clothing = getClothing();
        clothing.personDressCloth(this);
    }
}
