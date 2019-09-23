package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Citizen {
    protected List<Policeman> policemans;
    private String help = "normal";

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    abstract void sendMessenger(String help);

    public void setPoliceman() {
        this.policemans = new ArrayList<>();
    }

    public void register(Policeman policeman) {
        this.policemans.add(policeman);
    }

    public void unRegister(Policeman policeman) {
        this.policemans.remove(policeman);
    }
}
