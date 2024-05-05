package it.marconi.catalogoprodotti.catalogDomains;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ProductForm {
    private int id;
    private String name;
    private String category;
    private int productionYear;
    private int quantity;

    public ProductForm(int id, String name, String category, int productionYear, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.productionYear = productionYear;
        this.quantity = quantity;
    }

    public void editProduct(ProductForm otherProduct) {
        if(otherProduct.getId() != 0)
            id = otherProduct.getId();

        if(otherProduct.getName() != null && !otherProduct.getName().isEmpty())
            name = otherProduct.getName();

        if(otherProduct.getCategory() != null && !otherProduct.getCategory().isEmpty())
            category = otherProduct.getCategory();
        
        if(otherProduct.getProductionYear() != 0)
            productionYear = otherProduct.getProductionYear();

        if(otherProduct.getQuantity() != 0)
            quantity = otherProduct.getQuantity();
    }
}