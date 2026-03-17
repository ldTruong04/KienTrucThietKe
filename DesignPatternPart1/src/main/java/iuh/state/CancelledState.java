package iuh.state;

public class CancelledState implements OrderState {

    @Override
    public void handle() {
        System.out.println("Order cancelled and refunded.");
    }

}