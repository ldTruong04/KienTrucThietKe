package iuh.FactoryMethod;

public class MainFactory {

    public static void main(String[] args) {

        TransportFactory factory;

        // chọn factory (ví dụ vận chuyển đường bộ)
        factory = new RoadFactory();

        Transport transport1 = factory.createTransport();
        transport1.deliver();

        // đổi sang factory khác (đường biển)
        factory = new SeaFactory();

        Transport transport2 = factory.createTransport();
        transport2.deliver();
    }
}
