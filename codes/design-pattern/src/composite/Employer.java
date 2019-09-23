package composite;

import java.util.List;

public abstract class Employer {
    private String name;
    public List<Employer> employers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employer> getEmployers() {
        return employers;
    }

    public abstract void add(Employer employer);

    public abstract void delete(Employer employer);
}
