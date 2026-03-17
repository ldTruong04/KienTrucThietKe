package iuh.strategy;

public class ConsumptionTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(double price) {
        return price * 0.05; // 5%
    }

}
