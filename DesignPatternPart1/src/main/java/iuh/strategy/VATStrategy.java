package iuh.strategy;

public class VATStrategy implements TaxStrategy {

    @Override
    public double calculate(double price) {
        return price * 0.1; // 10%
    }

}