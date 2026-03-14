package iuh.abstractfactory;

public class MacButton implements Button {

    @Override
    public void paint() {
        System.out.println("Render Mac Button");
    }

}
