package bridge;

public class Lady extends Person {
    public Lady() {
        setType("Lady");
    }

    @Override
    public void dress() {
        Clothing clothing = getClothing();
        clothing.personDressCloth(this);
    }
}
