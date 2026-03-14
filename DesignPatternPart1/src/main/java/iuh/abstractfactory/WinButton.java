package iuh.abstractfactory;

public class WinButton implements Button {

    @Override
    public void paint() {
        System.out.println("Render Windows Button");
    }

}
