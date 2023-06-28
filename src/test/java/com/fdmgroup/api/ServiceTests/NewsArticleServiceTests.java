package com.fdmgroup.api.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.model.Category;
import com.fdmgroup.api.model.NewsArticle;
import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.NewsArticleRepository;
import com.fdmgroup.api.service.NewsArticleService;

@SpringBootTest
class NewsArticleServiceTests {

	@InjectMocks
	NewsArticleService newsArticleService;

	@Mock
	NewsArticleRepository newsArticleRepository;

	NewsArticle article1, article2, article3;
	Category cat1;
	Author author1, author2;
	List<Tag> tags = new ArrayList<>();
	Tag tag1, tag2;

	@Test
	void test_getAllnewsArticles_ReturnAllCreatedArticles() {
		List<NewsArticle> result = Arrays.asList(article1, article2, article3);

		when(newsArticleRepository.findAll()).thenReturn(result);

		List<NewsArticle> actualResult = newsArticleService.getAllnewsArticles();

		assertEquals(result.size(), actualResult.size());
		assertEquals(result, actualResult);
	}

	@Test
	void test_createNewNewsArticle_CreatesANewsArticle() {
		article1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", cat1, author1, tags);
		when(newsArticleRepository.save(article1)).thenReturn(article1);

		NewsArticle createdArticle = newsArticleService.createNewsArticle(article1);

		assertNotNull(createdArticle);
		assertEquals("dummyTitle1", createdArticle.getTitle());
	}

	@Test
	void test_createNewNewsArticle_DoesNotCreateANewsArticleWhenNameisPresent() {
		NewsArticle existingNewsArticle = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1",
				LocalDate.now(), "dummyContent1", cat1, author1, tags);
		when(newsArticleRepository.findByTitle(existingNewsArticle.getTitle()))
				.thenReturn(Optional.of(existingNewsArticle));

		NewsArticle createdNewsArticle = newsArticleService.createNewsArticle(existingNewsArticle);

		assertNull(createdNewsArticle);
		verify(newsArticleRepository, never()).save(existingNewsArticle);
	}

	@Test
	void test_getNewsArticle_ReturnsNewsArticle() {
		long id = 1L;
		NewsArticle newsArticle = new NewsArticle("dummyTitle", "dummyDescription", LocalDate.now(), "dummyContent",
				null, null, null);
		when(newsArticleRepository.findById(id)).thenReturn(Optional.of(newsArticle));

		NewsArticle actualResult = newsArticleService.getNewsArticle(id);

		assertNotNull(actualResult);
		assertEquals(newsArticle.getTitle(), actualResult.getTitle());
	}

	@Test
	void test_getNewsArticle_ReturnsNoNewsArticle_whenBadIdPassedIn() {
		long id = 10L;

		NewsArticle actualResult = newsArticleService.getNewsArticle(id);

		assertEquals(actualResult, null);
	}

	@Test
	void test_updateNewsArticle_ReturnsTrueWhenNewsArticleExists() {
		NewsArticle newsArticle = new NewsArticle("dummyTitle", "dummyDescription", LocalDate.now(), "dummyContent",
				null, null, null);
		when(newsArticleRepository.existsById(newsArticle.getId())).thenReturn(true);

		when(newsArticleRepository.save(newsArticle)).thenReturn(newsArticle);
		when(newsArticleRepository.save(article1)).thenReturn(article1);

		boolean actualResult = newsArticleService.updateNewsArticle(newsArticle);

		assertTrue(actualResult);
	}

	@Test
	void test_updateNewsArticle_ReturnsFalseWhenNewsArticleDoesNotExist() {
		NewsArticle newsArticle = new NewsArticle("dummyTitle", "dummyDescription", LocalDate.now(), "dummyContent",
				null, null, null);
		when(newsArticleRepository.existsById(100L)).thenReturn(false);

		when(newsArticleRepository.save(newsArticle)).thenReturn(newsArticle);

		boolean actualResult = newsArticleService.updateNewsArticle(newsArticle);

		assertFalse(actualResult);
	}

	@Test
	void test_removeNewsArticle_ReturnsTrueWhenNewsArticleExists() {
		long id = 1L;
		when(newsArticleRepository.existsById(id)).thenReturn(true);

		boolean actualResult = newsArticleService.removeNewsArticle(id);

		assertTrue(actualResult);
	}

	@Test
	void test_removeNewsArticle_ReturnsFalseWhenNewsArticleDoesNotExists() {
		long id = 100L;
		when(newsArticleRepository.existsById(id)).thenReturn(false);

		boolean actualResult = newsArticleService.removeNewsArticle(id);

		assertFalse(actualResult);
	}

	@Test
	void test_findTagByName_ReturnsListOfNewsArticles() {
		String name = "dummyName";
		List<NewsArticle> newsArticles = new ArrayList<>();
		newsArticles.add(new NewsArticle("dummyTitle1", "dummyDescription1", LocalDate.now(), "dummyContent1", null,
				null, null));
		newsArticles.add(new NewsArticle("dummyTitle2", "dummyDescription2", LocalDate.now(), "dummyContent2", null,
				null, null));
		when(newsArticleRepository.findTagByName(name)).thenReturn(newsArticles);

		List<NewsArticle> actualResult = newsArticleService.findTagByName(name);

		assertNotNull(actualResult);
		assertEquals(newsArticles.size(), actualResult.size());
	}

	@Test
	void test_getNewsArticlesByKeyword_ReturnsListOfNewsArticles() {
		String keyword = "dummyKeyword";
		List<NewsArticle> newsArticles = new ArrayList<>();
		newsArticles.add(new NewsArticle("dummyTitle1", "dummyDescription1", LocalDate.now(), "dummyContent1", null,
				null, null));
		newsArticles.add(new NewsArticle("dummyTitle2", "dummyDescription2", LocalDate.now(), "dummyContent2", null,
				null, null));
		when(newsArticleRepository.findAllByContentKeyword(keyword)).thenReturn(newsArticles);

		List<NewsArticle> actualResult = newsArticleService.getNewsArticlesByKeyword(keyword);

		assertNotNull(actualResult);
		assertEquals(newsArticles.size(), actualResult.size());
	}

	@Test
	void test_getNewsArticleByTitle_ReturnsNewsArticle() {
		String title = "dummyTitle";
		NewsArticle newsArticle = new NewsArticle(title, "dummyDescription", LocalDate.now(), "dummyContent", null,
				null, null);
		when(newsArticleRepository.findByTitle(title)).thenReturn(Optional.of(newsArticle));

		NewsArticle actualResult = newsArticleService.getNewsArticleByTitle(title);

		assertNotNull(actualResult);
		assertEquals(newsArticle.getTitle(), actualResult.getTitle());
	}

	@Test
	void test_getNewsArticleByTitle_ReturnsNull_whenNewsArticleDoesNotexist() {
		String title = "XXXXX";

		NewsArticle actualResult = newsArticleService.getNewsArticleByTitle(title);

		assertNull(actualResult);
	}

}
