package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.models.Category;
import com.shop.alcoshopspring.models.Product;
import com.shop.alcoshopspring.services.CategoryManager;
import com.shop.alcoshopspring.services.ProductManager;
import com.shop.alcoshopspring.wrappers.ProductPageWrapper;
import com.shop.alcoshopspring.wrappers.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductManager productManager;

	@Autowired
	private CategoryManager categoryManager;

	@GetMapping("/filtered")
	public ProductPageWrapper getFiltered(@RequestParam int offset, @RequestParam int itemsOnPage,
			@RequestParam(required = false) String category, @RequestParam(required = false) String productName,
			@RequestParam(required = false) BigDecimal unitPriceMin, @RequestParam(required = false) BigDecimal unitPriceMax) {
		if (unitPriceMax == null || unitPriceMax.doubleValue() == 0) unitPriceMax = BigDecimal.valueOf(Long.MAX_VALUE);
		if (unitPriceMin == null || unitPriceMin.doubleValue() == 0) unitPriceMin = BigDecimal.valueOf(0);
		if (productName == null || productName.isBlank()) productName = "";
		if (category == null) {
			return productManager.getFiltered(offset, itemsOnPage, productName, unitPriceMin, unitPriceMax);
		} else {
			return productManager.getFiltered(offset, itemsOnPage, category, productName, unitPriceMin, unitPriceMax);
		}
	}

	@GetMapping("/all")
	public Iterable<Product> getAll() {
		return productManager.findAll();
	}

	@GetMapping("/id")
	public Optional<Product> getById(@RequestParam Long index) {
		return productManager.findById(index);
	}

	@GetMapping(value = "/{productId}")
	public Optional<Product> getId(@PathVariable("productId") Long productId) {
		return productManager.findById(productId);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/upd")
	public Product update(@RequestBody ProductWrapper product) {
		Product productToUpdate = null;
		try {
			productToUpdate = productManager.findById(product.getId()).get();
			productToUpdate.setName(product.getName());
			productToUpdate.setDescription(product.getDescription());
			productToUpdate.setUnitPrice(product.getUnitPrice());
			productToUpdate.setSize(product.getSize());
			productToUpdate.setConcentration(product.getConcentration());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return productManager.save(productToUpdate);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(value = "/del/{productId}")
	public void delete(@PathVariable("productId") Long productId) {
		productManager.deleteById(productId);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/add")
	public Product addProduct(@RequestBody ProductWrapper productWrapper) {
		System.out.println(productWrapper.getCategoryName());
		Category category = categoryManager.findByName(productWrapper.getCategoryName())
				.orElseThrow(IllegalArgumentException::new);

		String image = saveImage(productWrapper.getImage());
		Product product = Product.builder()
				.name(productWrapper.getName())
				.unitPrice(productWrapper.getUnitPrice())
				.image(image)
				.concentration(productWrapper.getConcentration())
				.size(productWrapper.getSize())
				.description(productWrapper.getDescription())
				.category(category)
				.build();
		return productManager.save(product);
	}

	private String saveImage(String imageValue) {
		try {
			String base64Image = imageValue.split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			Path root = Path.of("./images");
			createDirIfNotExists(root);

			String filename = UUID.randomUUID() + ".jpg";
			FileOutputStream fileOutputStream = new FileOutputStream(root.resolve(filename).toString());
			fileOutputStream.write(imageBytes);
			fileOutputStream.close();

			return filename;
		} catch (IOException e) {
			throw new IllegalStateException("Failed to save image");
		}
	}

	private void createDirIfNotExists(Path path) {
		if (!path.toFile().exists()) {
			path.toFile().mkdirs();
		}
	}
}
