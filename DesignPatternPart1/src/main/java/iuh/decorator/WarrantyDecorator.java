package iuh.decorator;

public class WarrantyDecorator extends ProductDecorator {

    public WarrantyDecorator(Product product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 100;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Warranty";
    }

}
