package composite;

public class Programmer extends Employer {
    public Programmer(String name) {
        setName(name);
        employers = null; // 程序员，表示没有下属
    }

    @Override
    public void add(Employer employer) {
    }

    @Override
    public void delete(Employer employer) {
    }
}
