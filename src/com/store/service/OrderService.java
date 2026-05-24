package com.store.service;

import com.store.model.CartItem;
import com.store.model.Customer;
import com.store.model.Order;
import com.store.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private ProductService productService;
    private Map<Integer, List<CartItem>> carts = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private int nextOrderId = 1;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(Customer customer, int productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) { System.out.println("Product not found: " + productId); return; }
        List<CartItem> cart = carts.computeIfAbsent(customer.getId(), k -> new ArrayList<>());
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + quantity); return;
            }
        }
        cart.add(new CartItem(product, quantity));
    }

    public void checkout(Customer customer) {
        List<CartItem> cart = carts.getOrDefault(customer.getId(), new ArrayList<>());
        if (cart.isEmpty()) { System.out.println("Cart is empty for: " + customer.getName()); return; }
        Order order = new Order(nextOrderId++, customer, new ArrayList<>(cart));
        orders.add(order);
        System.out.println(order);
        carts.put(customer.getId(), new ArrayList<>());
    }

    public List<Order> listOrders() { return new ArrayList<>(orders); }
}
