
package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.repository.CategoryRepository;

/**
 * The service class for managing Categories entities. Provides CRUD
 * functionality and additional methods for Categories operations.
 *
 * <p>
 * This class is responsible for handling business logic related to Categories.
 * </p>
 *
 * @see com.fdmgroup.api.model.Category
 * @see com.fdmgroup.api.repository.CategoryRepository
 * @author Antonios Papatheodorou
 */

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	public Category findById(long id) {
		Optional<Category> existingCategory = categoryRepo.findById(id);
		return existingCategory.orElse(null);
	}

	public Category getCategoryByName(String name) {
		Optional<Category> existingCategory = categoryRepo.findByName(name);
		if (existingCategory.isPresent()) {
			return existingCategory.get();
		}
		return null;
	}

}
