package com.store.service;

import com.store.model.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public void loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id         = Integer.parseInt(parts[0].trim());
                String name    = parts[1].trim();
                double price   = Double.parseDouble(parts[2].trim());
                int quantity   = Integer.parseInt(parts[3].trim());
                products.add(new Product(id, name, price, quantity));
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public List<Product> listAll() { return new ArrayList<>(products); }

    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void addProduct(Product product) { products.add(product); }

    public void updateStock(int id, int newQuantity) {
        Product p = getProductById(id);
        if (p != null) p.setQuantity(newQuantity);
    }

    public void removeProduct(int id) { products.removeIf(p -> p.getId() == id); }

    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
}
