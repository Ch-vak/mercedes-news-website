
package com.fdmgroup.api.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.service.CategoryService;

@SpringBootTest
class CategoryServiceTests {

	@Autowired
	CategoryService categoryService;

	@Test
	void test_getAllCategories_returnsAListOfCategories() {
		List<Category> result = categoryService.getAllCategories();

		assertEquals(4, result.size());
	}

	@Test
	void test_getCategoryByname_returnsCorrectCategory_whenValidNamePassedIn() {

		Category result = categoryService.getCategoryByName("Performance");

		assertEquals("Performance", result.getName());
	}

	@Test
	void test_findByCategoryName_returnsEmptyList_whenNoMatchingCategoryNamePassedIn() {
		Category categories = categoryService.getCategoryByName("testWrongName");

		assertNull(categories);
	}

	@Test
	void test_getCategoryById_WhenCorrectCategoryPassedInWithvalidId() {
		Category categories = categoryService.findById(2);

		assertEquals(2, categories.getId());
	}

}
