package factorymethod;

public class WhiteDogFactory implements IDogFactory {
    @Override
    public IDog createDog() {
        return new WhiteDog();
    }
}
