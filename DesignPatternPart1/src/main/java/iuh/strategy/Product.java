package iuh.strategy;

public class Product {

    private double price;
    private TaxStrategy strategy;

    public Product(double price) {
        this.price = price;
    }

    public void setStrategy(TaxStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTax() {
        if(strategy == null){
            throw new RuntimeException("Tax strategy not set!");
        }
        return strategy.calculate(price);
    }

}
