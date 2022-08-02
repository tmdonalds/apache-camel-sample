package example.domain;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private final String name;

    private final List<Order> orders;

    private final List<Order> processOrders = new ArrayList<>();

    public Job(String name) {
        this.name = "Job 1";
        this.orders = new ArrayList<>();

        List<Item> item1 = new ArrayList<>();
        item1.add(new Item("item 1","1-123456789"));
        item1.add(new Item("item 2","1-987654321"));
        item1.add(new Item("item 3","1-111222333"));

        List<Item> item2 = new ArrayList<>();
        item2.add(new Item("item 1","2-123456789"));
        item2.add(new Item("item 2","2-987654321"));

        Order e = new Order("123456789", item1);
        orders.add(e);
        Order e1 = new Order("987654321", item2);
        orders.add(e1);

        processOrders.add(e);
        processOrders.add(e1);
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getProcessOrders() {
        return processOrders;
    }

    public boolean hasNextOrder(){
        return processOrders.size() != 0;
    }

    public Order getNextOrder(){
        return processOrders.remove(0);
    }
}
