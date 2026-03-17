package iuh.decorator;

public class ShippingDecorator extends ProductDecorator {

    public ShippingDecorator(Product product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 30;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Express Shipping";
    }

}
