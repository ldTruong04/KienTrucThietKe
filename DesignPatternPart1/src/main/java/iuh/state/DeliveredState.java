package iuh.state;

public class DeliveredState implements OrderState {

    @Override
    public void handle() {
        System.out.println("Order delivered successfully.");
    }

}
