package iuh.state;

public class NewState implements OrderState {

    @Override
    public void handle() {
        System.out.println("Checking order information...");
    }

}
