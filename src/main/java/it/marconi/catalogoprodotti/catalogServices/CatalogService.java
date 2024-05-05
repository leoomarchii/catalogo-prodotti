package it.marconi.catalogoprodotti.catalogServices;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marconi.catalogoprodotti.catalogDomains.ProductForm;

@Service
public class CatalogService {
    private ArrayList<ProductForm> products = new ArrayList<>();

    public ArrayList<ProductForm> getProducts() {
        return products;
    }

    public void addProduct(ProductForm newProduct) {
        products.add(newProduct);
    }

    public Optional<ProductForm> getProductById(int id) {
        for (ProductForm p : products)
            if(p.getId() == id)
                return Optional.of(p);
            
        return Optional.empty();
    }

    public void empty() {
        products.clear();
    }

    public void deleteProductById(int id) {
        for (ProductForm p : products)
            if(p.getId() == id){
                products.remove(p);
                return;
            }
    }
}