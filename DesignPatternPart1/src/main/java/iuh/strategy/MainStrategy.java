package iuh.strategy;

public class MainStrategy {

    public static void main(String[] args) {

        Product product = new Product(1000);

        // VAT
        product.setStrategy(new VATStrategy());
        System.out.println("VAT: " + product.calculateTax());

        // Luxury
        product.setStrategy(new LuxuryTaxStrategy());
        System.out.println("Luxury Tax: " + product.calculateTax());

        // Consumption
        product.setStrategy(new ConsumptionTaxStrategy());
        System.out.println("Consumption Tax: " + product.calculateTax());
    }

}
