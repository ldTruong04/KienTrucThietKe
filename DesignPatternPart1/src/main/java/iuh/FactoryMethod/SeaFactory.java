package iuh.FactoryMethod;

public class SeaFactory extends TransportFactory {

    public Transport createTransport() {
        return new Ship();
    }

}
