package iuh.abstractfactory;

public class MainAbstractFactory {

    public static void main(String[] args) {

        GUIFactory factory;

        // chọn hệ điều hành(Mac\Windows)
        String os = "Mac";

        if(os.equalsIgnoreCase("Windows")){
            factory = new WindowsFactory();
        }else{
            factory = new MacFactory();
        }

        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        button.paint();
        checkbox.check();

    }
}
