package com.fdmgroup.api.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.repository.AuthorRepository;

@DataJpaTest
class AuthorRepositoryTests {

	@Autowired
	private AuthorRepository repo;

	Author author;

	@BeforeEach
	void setUp() throws Exception {
		LocalDate now = LocalDate.now();
		author = new Author("Typ E. Writer", now, "The Netherlands");
	}

	@Test
	void test_save_generatesAuthorId_WhenValidAuthorPassedIn() {
		repo.save(author);

		assertTrue(author.getId() > 0);
	}

	@Test
	void test_findAll_returnsListWithSizeGreaterThanZero_whenAuthorHasBeenCreated() {

		repo.save(author);

		assertTrue(author.getId() > 0);

		List<Author> result = repo.findAll();

		assertTrue(result.size() > 0);
	}

	@Test
	void test_findById_returnsAuthorWithCorrectValue_whenAuthorHasBeenCreated() {
		LocalDate now = LocalDate.now();
		author = new Author("Typ E. Writer", now, "The Netherlands");

		repo.save(author);

		assertTrue(author.getId() > 0);

		Optional<Author> authOpt = repo.findById(author.getId());

		assertTrue(authOpt.isPresent());

		Author foundAuthor = authOpt.get();

		assertEquals("Typ E. Writer", foundAuthor.getName());
		assertEquals(now, foundAuthor.getBirthday());
		assertEquals("The Netherlands", foundAuthor.getNationality());
	}

	@Test
	void test_save_updatesExistingAuthor_whenExistingAuthorRetrievedThenSaved() {
		Optional<Author> authOpt = repo.findById(1L);

		assertTrue(authOpt.isPresent());

		Author author = authOpt.get();
		author.setName("newAuthorName");
		repo.save(author);

		Author result = repo.findById(1L).get();

		assertEquals("newAuthorName", result.getName());
	}

	@Test
	void test_deleteById_removesExistingAuthor_whenValidIdPassedIn() {
		repo.save(author);

		assertTrue(author.getId() > 0);

		repo.deleteById(author.getId());

		assertTrue(!repo.existsById(author.getId()));
	}

	@Test
	void test_searchNames_returnsListOfAuthors_whenMatchingKeywordPassedIn() {
		LocalDate now = LocalDate.now();
		Author author1 = new Author("Writer1", now, "Belgium");
		Author author2 = new Author("Writer2", now, "Belgium");

		repo.save(author);
		repo.save(author1);
		repo.save(author2);

		List<Author> result = repo.searchNames("writer");
		assertEquals(3, result.size());
	}

	@Test
	void test_searchNames_returnsEmptyList_whenKeywordMatchingNoNamePassedIn() {
		repo.save(author);

		assertTrue(repo.existsById(author.getId()));

		List<Author> result = repo.searchNames("Mississippidampferkapitaensmuetzensammler");
		assertEquals(0, result.size());
	}

	@Test
	void test_findByName_returnsAuthor_whenCorrectNamePassedIn() {
		repo.save(author);

		assertTrue(repo.existsById(author.getId()));

		Optional<Author> authOpt = repo.findByName("Typ E. Writer");

		assertTrue(authOpt.isPresent());

		assertEquals("Typ E. Writer", authOpt.get().getName());
	}

}
