package iuh.singleton;

public class MainSingleton {

    public static void main(String[] args) {

        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        log1.log("Design Pattern Singleton");

        System.out.println(log1 == log2);
    }
}