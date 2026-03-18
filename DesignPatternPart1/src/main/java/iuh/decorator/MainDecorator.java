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

@startuml
interface Product {
  + getDescription(): String
  + getPrice(): double
}

class BasicProduct {
  - price: double
  - description: String
  + BasicProduct(price: double)
  + getDescription(): String
  + getPrice(): double
}

abstract class ProductDecorator {
  - product: Product
  + ProductDecorator(product: Product)
  + getDescription(): String
  + getPrice(): double
}

class GiftWrapDecorator {
  - wrapCost: double
  + GiftWrapDecorator(product: Product)
  + getDescription(): String
  + getPrice(): double
}

class ShippingDecorator {
  - shippingCost: double
  + ShippingDecorator(product: Product)
  + getDescription(): String
  + getPrice(): double
}

class WarrantyDecorator {
  - warrantyCost: double
  + WarrantyDecorator(product: Product)
  + getDescription(): String
  + getPrice(): double
}

Product <|.. BasicProduct
Product <|.. ProductDecorator
ProductDecorator <|-- GiftWrapDecorator
ProductDecorator <|-- ShippingDecorator
ProductDecorator <|-- WarrantyDecorator
ProductDecorator *-- Product : wraps
@enduml