package com.fdmgroup.api.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.service.AuthorService;

@SpringBootTest
class AuthorServiceTests {

	@Autowired
	AuthorService service;

	Author author1, author2;

	@BeforeEach
	void setUp() throws Exception {
		author1 = new Author("Author 1", LocalDate.now(), "Country 1");
		author2 = new Author("Author 2", LocalDate.now(), "Country 2");

		service.createAuthor(author1);
		service.createAuthor(author2);
	}

	@Test
	void test_createAuthor_generatesCorrectIds_whenValidEmployeesPassedIn() {
		author1 = service.getAuthorByName("Author 1");
		author2 = service.getAuthorByName("Author 2");

		assertTrue(author1.getId() > 0);
		assertTrue(author2.getId() > 0);
		assertNotEquals(author1.getId(), author2.getId());
	}

	@Test
	void test_getAllAuthors_returnsList_ofCorrectSize() {
		List<Author> result = service.getAllAuthors();

		assertEquals(10, result.size());
	}

	@Test
	void test_getAuthorByName_returnsCorrectAuthor_whenValidNamePassedIn() {
		Author result = service.getAuthorByName("Author 1");

		assertNotNull(result);
		assertEquals("Author 1", result.getName());
	}

	@Test
	void test_getAuthorById_returnsCorrectAuthor_whenValidIdPassedIn() {
		Author result = service.getAuthorById(10);

		assertNotNull(result);
		assertEquals(10, result.getId());
	}

	@Test
	void test_updateAuthor_updatesCorrectAuthor_whenValidAuthorPassedIn() {
		author1 = service.getAuthorByName(author1.getName());
		assertTrue(author1.getId() > 0);

		author1.setName("New Author 1");

		assertTrue(service.updateAuthor(author1));

		assertEquals("New Author 1", service.getAuthorById(author1.getId()).getName());

	}

	@Test
	void test_removeAuthorById_removesCorrectAuthor_whenValidIdPassedIn() {
		author1 = service.getAuthorByName("Author 1");
		assertNotNull(author1);

		long id = author1.getId();
		assertTrue(id > 0);

		service.removeAuthorById(id);
		assertNull(service.getAuthorById(id));
	}

	@Test
	void test_removeAuthor_removesCorrectAuthor_whenValidAuthorPassedIn() {
		Author authorToRemove = service.getAuthorByName("Author 2");
		assertNotNull(authorToRemove);

		long id = authorToRemove.getId();
		assertTrue(service.removeAuthor(authorToRemove));

		assertNull(service.getAuthorById(id));
	}

	@Test
	void test_getAuthorsByNameKeyword_returnsCorrectListOfAuthors_whenValidKeywordPassedIn() {
		List<Author> result = service.getAuthorsByNameKeyword("author");

		assertEquals(2, result.size());
	}

	@Test
	void test_getAuthorByName_returnsNull_whenNonexistentNamePassedIn() {
		assertNull(service.getAuthorByName("This Name does not exist"));
	}

	@Test
	void test_updateAuthor_returnsFalse_whenNonexistentAuthorPassedIn() {
		author1.setId(9999);
		assertFalse(service.updateAuthor(author1));
	}

	@Test
	void test_removeAuthorById_returnsFalse_whenNonexistentIdPassedIn() {
		assertFalse(service.removeAuthorById(999));
	}

	@Test
	void test_removeAuthor_returnsFalse_whenNonexistentAuthorPassedIn() {
		author1.setId(9999);
		assertFalse(service.removeAuthor(author1));
	}
}
