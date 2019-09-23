package factorymethod;

public class BlackDogFactory implements IDogFactory {
    @Override
    public IDog createDog() {
        return new BlackDog();
    }
}
