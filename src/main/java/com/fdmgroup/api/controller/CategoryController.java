package com.fdmgroup.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.service.CategoryService;

/**
 * 
 * <p>
 * This controller provides endpoints for getting all , get by name and get by
 * id for Categories
 * </p>
 * 
 * @author Antonios Papatheodorou
 * @see CategoryService
 */

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable long id) {
		Category category = (Category) categoryService.findById(id);

		if (category != null) {
			return ResponseEntity.status(HttpStatus.OK).body(category);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<Category> getCategoryByName(@PathVariable("name") String name) {
		Category namedCategory = (Category) categoryService.getCategoryByName(name);
		if (namedCategory != null) {
			return ResponseEntity.status(HttpStatus.OK).body(namedCategory);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
