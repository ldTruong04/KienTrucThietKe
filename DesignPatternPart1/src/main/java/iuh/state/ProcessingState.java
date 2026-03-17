package iuh.state;

public class ProcessingState implements OrderState {

    @Override
    public void handle() {
        System.out.println("Packing and shipping order...");
    }

}
