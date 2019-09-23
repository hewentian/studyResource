package observer;

public class Test {
    public static void main(String[] args) {
        // 天河警察和市民
        Policeman tianHePoliceman = new TianHePoliceman();
        Citizen tianHeCitizen = new TianHeCitizen(tianHePoliceman);
        tianHeCitizen.sendMessenger("normal");
        tianHeCitizen.sendMessenger("unnormal");

        // 黄埔警察和市民
        Policeman huangPuPoliceman = new HuangPuPoliceman();
        Citizen huangPuCitizen = new HuangPuCitizen(huangPuPoliceman);
        huangPuCitizen.sendMessenger("normal");
        huangPuCitizen.sendMessenger("unnormal");
    }
}
