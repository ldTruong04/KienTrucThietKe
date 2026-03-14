package iuh.FactoryMethod;

public class RoadFactory extends TransportFactory {

    public Transport createTransport() {
        return new Truck();
    }

}