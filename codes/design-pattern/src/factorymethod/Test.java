package factorymethod;

public class Test {
    public static void main(String[] args) {
        IDogFactory whiteDogFactory = new WhiteDogFactory();
        whiteDogFactory.createDog().eat();

        IDogFactory blackDogFactory = new BlackDogFactory();
        blackDogFactory.createDog().eat();
    }
}
