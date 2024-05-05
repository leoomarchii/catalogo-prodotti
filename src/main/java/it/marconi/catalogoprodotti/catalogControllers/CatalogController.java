package it.marconi.catalogoprodotti.catalogControllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.catalogoprodotti.catalogDomains.ProductForm;
import it.marconi.catalogoprodotti.catalogServices.CatalogService;

@Controller
@RequestMapping("/")
public class CatalogController {
    private int originalId;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/")
    public ModelAndView populate() {
        catalogService.addProduct(new ProductForm(1, "a", "b", 2021, 10));
        catalogService.addProduct(new ProductForm(2, "c", "d", 2022, 20));
        catalogService.addProduct(new ProductForm(3, "e", "f", 2023, 30));
        catalogService.addProduct(new ProductForm(4, "g", "h", 2024, 40));

        return new ModelAndView("redirect:/catalogo");
    }

    @GetMapping("/catalogo")
    public ModelAndView viewCatalog() {
        return new ModelAndView("catalog-page").addObject("products", catalogService.getProducts());
    }

    @GetMapping("/svuota")
    public ModelAndView emptyCatalog() {
        catalogService.empty();

        return new ModelAndView("redirect:/catalogo");
    }

    @GetMapping("/prodotto/{id}")
    public ModelAndView productDetail(@PathVariable("id") int id) {
        Optional<ProductForm> product = catalogService.getProductById(id);

        if(product.isPresent())
            return new ModelAndView("product-detail").addObject("product", product.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodotto non trovato");
    }

    @GetMapping("/prodotto/nuovo")
    public ModelAndView addNewProduct() {
        return new ModelAndView("new-product").addObject("productForm", new ProductForm());
    }

    @PostMapping("/prodotto/nuovo/handler")
    public ModelAndView handlerNewProduct(@ModelAttribute ProductForm productForm) {
        catalogService.addProduct(productForm);

        return new ModelAndView("redirect:/catalogo");
    }

    @GetMapping("/prodotto/modifica/{id}")
    public ModelAndView editProduct(@PathVariable("id") int id) {
        originalId = id;

        return new ModelAndView("edit-product").addObject("productForm", new ProductForm());
    }

    @PostMapping("/prodotto/modifica/handler")
    public ModelAndView handlerEditedProduct(@ModelAttribute ProductForm productForm) {
        ProductForm product = catalogService.getProductById(originalId).get();
        product.editProduct(productForm);

        return new ModelAndView("redirect:/prodotto/" + product.getId());
    }

    @GetMapping("/prodotto/elimina/{id}")
    public ModelAndView deleteProduct(
        @PathVariable("id") int id,
        RedirectAttributes attr
    ) {
        catalogService.deleteProductById(id);

        attr.addFlashAttribute("deleted", true);
        return new ModelAndView("redirect:/catalogo");
    }
}