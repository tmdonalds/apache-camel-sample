package example.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<Item> items = new ArrayList<>();

    public Order(String orderId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", items=" + items +
                '}';
    }

}
