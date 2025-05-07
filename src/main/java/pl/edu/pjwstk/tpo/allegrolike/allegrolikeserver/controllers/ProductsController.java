package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        System.out.println("getAllProducts");
        final List<ProductResponseDto> products = this.productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        var productOp = this.productService.getProductById(id);

        return productOp.isPresent() ?
                ResponseEntity.ok(productOp.get()) :
                ResponseEntity.status(404).body("Product with id = " + id + " was not found");
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid CreateProductRequestDto dto) {
        var response = this.productService.createProduct(dto);
        var uri = URI.create("/api/products/" + response.id());
        return ResponseEntity.created(uri)
                             .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(!this.productService.deleteProductById(id)) {
            return ResponseEntity.status(404).body("Product with id = " + id + " was not found");
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductRequestDto dto) {
        var response = this.productService.updateProduct(id, dto);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }

        return ResponseEntity.status(404).body("Product with id = " + id + " was not found");
    }
}
