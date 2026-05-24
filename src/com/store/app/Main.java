package com.store.app;

import com.store.model.Product;
import com.store.model.Customer;
import com.store.model.Order;
import com.store.service.ProductService;
import com.store.service.CustomerService;
import com.store.service.OrderService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductService ps = new ProductService();
        CustomerService cs = new CustomerService();
        OrderService os = new OrderService(ps);

        ps.loadProducts("data/products.txt");
        cs.loadCustomers("data/customers.txt");

        System.out.println("=== All Products ===");
        ps.listAll().forEach(System.out::println);

        System.out.println("\n=== All Customers ===");
        cs.listAll().forEach(System.out::println);

        ps.addProduct(new Product(141, "Grapes", 4.0, 20));
        ps.updateStock(102, 50);
        ps.removeProduct(103);

        System.out.println("\n=== Products after CRUD ===");
        ps.listAll().forEach(System.out::println);

        cs.addCustomer(new Customer(21, "Alice Johnson", "alice@example.com"));
        Customer customer1 = cs.getCustomerById(1);
        if (customer1 != null) {
            customer1.setName("John Updated");
            customer1.setEmail("john.new@example.com");
        }
        cs.deleteCustomer(2);

        System.out.println("\n=== Customers after CRUD ===");
        cs.listAll().forEach(System.out::println);

        Customer c1 = cs.getCustomerById(1);
        os.addToCart(c1, 101, 2);
        os.addToCart(c1, 141, 3);
        System.out.println("\nCheckout for Customer 1:");
        os.checkout(c1);

        Customer alice = cs.getCustomerById(21);
        os.addToCart(alice, 102, 5);
        os.addToCart(alice, 101, 1);
        System.out.println("\nCheckout for Customer 21:");
        os.checkout(alice);

        System.out.println("\n=== All Orders in Memory ===");
        os.listOrders().forEach(System.out::println);

        System.out.println("\n=== Search Products (keyword: 'Apple') ===");
        ps.searchByName("Apple").forEach(System.out::println);

        System.out.println("\n=== Filter Products (price range: 1.0 - 200.0) ===");
        ps.filterByPrice(1.0, 200.0).forEach(System.out::println);
    }
}
