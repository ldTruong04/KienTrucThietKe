package iuh.decorator;

public class MainDecorator {

    public static void main(String[] args) {

        Product product = new BasicProduct(1000);

        // thêm các decorator
        product = new GiftWrapDecorator(product);
        product = new ShippingDecorator(product);
        product = new WarrantyDecorator(product);

        System.out.println("Description: " + product.getDescription());
        System.out.println("Total Price: " + product.getPrice());
    }

}