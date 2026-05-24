package com.store.service;

import com.store.model.Customer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers = new ArrayList<>();

    public void loadCustomers(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id       = Integer.parseInt(parts[0].trim());
                String name  = parts[1].trim();
                String email = parts[2].trim();
                customers.add(new Customer(id, name, email));
            }
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public List<Customer> listAll() { return new ArrayList<>(customers); }

    public Customer getCustomerById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void addCustomer(Customer customer) { customers.add(customer); }

    public void deleteCustomer(int id) { customers.removeIf(c -> c.getId() == id); }
}
