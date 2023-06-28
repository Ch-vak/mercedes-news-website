package com.fdmgroup.api.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.repository.CategoryRepository;

@DataJpaTest
class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository categoryRepo;

	@BeforeEach
	void setUp() throws Exception {
		categoryRepo.save(new Category("Papaya"));
		categoryRepo.save(new Category("Mango"));
	}

	@Test
	public void test_findAll_returnsCorrectValues() {
		List<Category> categories = categoryRepo.findAll();

		assertThat(categories.size() >= 4);
	}

	@Test
	public void test_findById_whenValidCategoryPassedIn() {

		Optional<Category> result = categoryRepo.findById(1L);

		assertEquals(1L, result.get().getId());
	}

	@Test
	public void test_findByName_findsCorrectCategory_whenValidNamePassedIn() {
		Optional<Category> result = categoryRepo.findByName("Legacy");

		assertEquals("Legacy", result.get().getName());
	}
}
