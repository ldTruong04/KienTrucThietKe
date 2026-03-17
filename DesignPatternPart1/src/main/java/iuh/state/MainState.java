package iuh.state;

public class MainState {

    public static void main(String[] args) {

        Order order = new Order();

        order.setState(new NewState());
        order.process();

        order.setState(new ProcessingState());
        order.process();

        order.setState(new DeliveredState());
        order.process();

        order.setState(new CancelledState());
        order.process();
    }

}
