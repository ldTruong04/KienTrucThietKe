package iuh.decorator;

public class GiftWrapDecorator extends ProductDecorator {

    public GiftWrapDecorator(Product product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 50;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Gift Wrap";
    }

}
