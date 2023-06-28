package com.fdmgroup.api.ControllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.api.controller.CategoryController;
import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.service.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	CategoryController controller;

	@Mock
	CategoryService categoryService;

	List<Category> expectedCategories = new ArrayList<>();

	@Test
	void test_controllerNotNull() {
		assertThat(controller).isNotNull();
	}

	@Test
	void test_getCategoriesReturnCorrectStatusCode() throws Exception {
		mvc.perform(get("/api/v1/category")).andExpect(status().isOk());
	}

	@Test
	void test_getCategoriesReturnCorrectList() throws Exception {
		List<Category> expectedCategories = new ArrayList<>();
		expectedCategories.add(new Category("Category 1"));
		expectedCategories.add(new Category("Category 2"));
		expectedCategories.add(new Category("Category 3"));
		expectedCategories.add(new Category("Category 4"));
		when(categoryService.getAllCategories()).thenReturn(expectedCategories);

		MvcResult result = mvc.perform(get("/api/v1/category")).andExpect(status().isOk()).andReturn();

		String responseBody = result.getResponse().getContentAsString();

		List<Category> actualCategories = new ObjectMapper().readValue(responseBody,
				new TypeReference<List<Category>>() {
				});

		assertThat(actualCategories.size()).isEqualTo(expectedCategories.size());
	}

	@Test
	void test_getCategoryReturnsCorrectCategory() {
		long categoryId = 1L;
		Category expectedCategory = new Category("Test Category");
		when(categoryService.findById(categoryId)).thenReturn(expectedCategory);

		ResponseEntity<Category> response = controller.getCategory(categoryId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void test_getCategoryReturnsNotFound() {
		long categoryId = 100L;
		when(categoryService.findById(categoryId)).thenReturn(null);

		ResponseEntity<Category> response = controller.getCategory(categoryId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void test_getCategoryByNameReturnsCorrectCategory() {
		String categoryName = "Performance";
		Category expectedCategory = new Category(categoryName);
		when(categoryService.getCategoryByName(categoryName)).thenReturn(expectedCategory);

		ResponseEntity<Category> response = controller.getCategoryByName(categoryName);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void test_getCategoryByNameReturnsNotFound() {
		String categoryName = "Test Category";
		when(categoryService.getCategoryByName(categoryName)).thenReturn(null);

		ResponseEntity<Category> response = controller.getCategoryByName(categoryName);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
