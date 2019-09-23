package observer;

public class HuangPuCitizen extends Citizen {
    public HuangPuCitizen(Policeman policeman) {
        setPoliceman();
        register(policeman);
    }

    @Override
    void sendMessenger(String help) {
        setHelp(help);
        for (int i = 0; i < policemans.size(); i++) {
            Policeman policeman = policemans.get(i);
            // 通知警察行动
            policeman.action(this);
        }
    }
}
