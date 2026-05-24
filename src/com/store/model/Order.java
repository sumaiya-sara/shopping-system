package com.store.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private List<CartItem> items;
    private LocalDateTime orderDate;
    private double total;

    public Order(int id, Customer customer, List<CartItem> items) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.orderDate = LocalDateTime.now();
        this.total = calculateTotal();
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public int getId()                  { return id; }
    public Customer getCustomer()       { return customer; }
    public List<CartItem> getItems()    { return items; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotal()            { return total; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order[id=%d, customer=%s, date=%s, total=%.2f, items=[\n",
                id, customer.getName(), orderDate, total));
        for (CartItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append("]]");
        return sb.toString();
    }
}
