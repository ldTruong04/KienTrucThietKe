package observer;

public class MainObserver {

    public static void main(String[] args) {

        // ===== STOCK =====
        Stock stock = new Stock();

        Observer inv1 = new Investor("Alice");
        Observer inv2 = new Investor("Bob");

        stock.attach(inv1);
        stock.attach(inv2);

        stock.setPrice(100);
        stock.setPrice(120);

        System.out.println("-----");

        // ===== TASK =====
        Task task = new Task();

        Observer mem1 = new TeamMember("John");
        Observer mem2 = new TeamMember("Mary");

        task.attach(mem1);
        task.attach(mem2);

        task.setStatus("In Progress");
        task.setStatus("Completed");
    }
}