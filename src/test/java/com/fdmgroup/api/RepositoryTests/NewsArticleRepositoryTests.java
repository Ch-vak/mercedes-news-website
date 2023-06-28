package com.fdmgroup.api.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.model.NewsArticle;
import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.AuthorRepository;
import com.fdmgroup.api.repository.CategoryRepository;
import com.fdmgroup.api.repository.NewsArticleRepository;
import com.fdmgroup.api.repository.TagRepository;

@DataJpaTest
class NewsArticleRepositoryTests {

	@Autowired
	private NewsArticleRepository newsArticleRepo;

	@Autowired
	private AuthorRepository authorrepo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private TagRepository tagRepo;

	NewsArticle article1, article2, article3;
	Category cat1, cat2;
	Author author1, author2;
	List<Tag> tags = new ArrayList<>();
	Tag tag1, tag2;

	@BeforeEach
	void setUp() throws Exception {
		tag1 = new Tag("dummyTagOne");
		tag2 = new Tag("dummyTagTwo");
		tagRepo.save(tag1);
		tagRepo.save(tag2);
		author1 = new Author("dummyname1", LocalDate.now(), "dummyNationality1");
		author2 = new Author("dummyname2", LocalDate.now(), "dummyNationality2");
		authorrepo.save(author1);
		authorrepo.save(author2);
		cat1 = new Category("dummyName1");
		cat2 = new Category("dummyName2");
		catRepo.save(cat1);
		catRepo.save(cat2);
		tags.add(tag1);
		tags.add(tag2);
		article1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", cat1, author1, tags);
		article2 = new NewsArticle("dummyTitle2", "dummyDescription2dummyDescription2", LocalDate.now(),
				"dummyContent2", cat2, author2, tags);
		article3 = new NewsArticle("dummyTitle3", "dummyDescription3dummyDescription3", LocalDate.now(),
				"dummyContent3", cat1, author2, tags);
		newsArticleRepo.save(article1);
		newsArticleRepo.save(article2);
		newsArticleRepo.save(article3);
	}

	@Test
	void test_GeneratesId_WhenNewArticlePassedIn() {
		newsArticleRepo.save(article1);
		assertTrue(article1.getId() > 0);
	}

	@Test
	void test_GeneratesMultipleIds_WhenMultipleNewArticlesPassedIn() {
		assertTrue(article1.getId() > 0);
		assertTrue(article2.getId() > 0);
		assertTrue(article3.getId() > 0);
	}

	@Test
	void test_findByTitleReturnsTitle_whenCorrectTitlePassedIn() {
		Optional<NewsArticle> result = newsArticleRepo.findByTitle("dummyTitle1");

		assertTrue(result.isPresent());
		assertEquals("dummyTitle1", result.get().getTitle());

	}

	@Test
	void test_findByTitleReturnsNull_whenInCorrectTitlePassedIn() {
		Optional<NewsArticle> result = newsArticleRepo.findByTitle("dummyTitle1231");

		assertTrue(result.isEmpty());
	}

	@Test
	void test_findAllByContentKeywordResturnsList_WhenKeywordMatches() {
		List<NewsArticle> result = newsArticleRepo.findAllByContentKeyword("dummy");

		assertTrue(result.size() > 0);
	}

	@Test
	void test_findAllByContentKeywordResturnsEmptyList_WhenIncorrectKeywordMatches() {
		List<NewsArticle> result = newsArticleRepo.findAllByContentKeyword("XXX");

		assertTrue(result.size() == 0);
	}

	@Test
	void test_existsByIdReturnTrue_WhencorrectidPassedIn() {
		boolean result = newsArticleRepo.existsById(1L);

		assertThat(result).isTrue();
	}

	@Test
	void test_existsByIdReturnTrue_WhenIncorrectidPassedIn() {
		boolean result = newsArticleRepo.existsById(100L);

		assertThat(result).isFalse();
	}

	@Test
	void test_deleteByIdWorks_WhencorrectIdPassedIn() {
		long before = newsArticleRepo.count();
		newsArticleRepo.deleteById(1);
		long after = newsArticleRepo.count();
		assertTrue(after < before);
	}

	@Test
	void test_findTagsByNameReturnsCorrectTag_WhenTagNameMatches() {
		List<NewsArticle> result = newsArticleRepo.findTagByName("dummyTagOne");

		assertTrue(result.size() >= 3);
	}

}
