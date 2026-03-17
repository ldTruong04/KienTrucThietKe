package iuh.strategy;

public class LuxuryTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(double price) {
        return price * 0.2; // 20%
    }

}