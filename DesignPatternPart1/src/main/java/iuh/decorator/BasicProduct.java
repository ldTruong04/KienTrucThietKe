package iuh.decorator;

public class BasicProduct implements Product {

    private double price;

    public BasicProduct(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return "Basic Product";
    }

}
