package com.fdmgroup.api.ControllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fdmgroup.api.controller.NewsArticleController;
import com.fdmgroup.api.model.NewsArticle;
import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.NewsArticleRepository;
import com.fdmgroup.api.repository.TagRepository;
import com.fdmgroup.api.service.NewsArticleService;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsArticleControllerTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	NewsArticleController controller;

	@Autowired
	NewsArticleService newsArticleService;

	@Autowired
	NewsArticleRepository newsArticleRepo;

	@Autowired
	TagRepository tagRepo;

	List<Tag> tags = new ArrayList<>();

	@Test
	void test_controllerNotNull() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void test_allNewsArticles_ReturnsCorrectStatus() throws Exception {
		mvc.perform(get("/api/v1/newsArticle")).andExpect(status().isOk());
	}

	@Test
	public void test_getNewsArticles_ReturnsCorrectStatus_whencorrectIdPassedIn() throws Exception {
		mvc.perform(get("/api/v1/newsArticle/1")).andExpect(status().isOk());
	}

	@Test
	public void test_getNewsArticles_ReturnsInCorrectStatus_whenWrongIdpassedIn() throws Exception {
		mvc.perform(get("/api/v1/newsArticle/100")).andExpect(status().isNotFound());
	}

	@Test
	public void test_deleteNewsArticles_ReturnsCorrectStatus_whencorrectIdPassedIn() throws Exception {
		mvc.perform(delete("/api/v1/newsArticle/1")).andExpect(status().isOk());
	}

	@Test
	public void test_deletetNewsArticles_ReturnsInCorrectStatus_whenWrongIdpassedIn() throws Exception {
		mvc.perform(delete("/api/v1/newsArticle/100")).andExpect(status().isNotFound());
	}

	@Test
	public void test_createNewsArticle_ReturnsCorrectStatus() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		NewsArticle newsArticle2 = new NewsArticle("dummyTitle0", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);
		String json = mapper.writeValueAsString(newsArticle2);
		mvc.perform(post("/api/v1/newsArticle").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void test_createNewsArticle_ReturnsBadReuquestStatus() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		NewsArticle newsArticle2 = new NewsArticle("dummyTitle5", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);

		String json = mapper.writeValueAsString(newsArticle2);
		mvc.perform(post("/api/v1/newsArticle").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated());
		NewsArticle newsArticle = new NewsArticle("dummyTitle5", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);

		String json1 = mapper.writeValueAsString(newsArticle);
		mvc.perform(post("/api/v1/newsArticle").contentType(MediaType.APPLICATION_JSON).content(json1))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void test_updatedNewsArticle_ReturnsCorrectStatus() throws Exception {
		// Test case 1: Test with existing news article
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		NewsArticle newsArticle1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);
		newsArticleService.createNewsArticle(newsArticle1);
		newsArticleRepo.save(newsArticle1);
		newsArticle1.setTitle("updatedTitle!");
		String json1 = mapper.writeValueAsString(newsArticle1);
		mvc.perform(put("/api/v1/newsArticle").contentType(MediaType.APPLICATION_JSON).content(json1))
				.andExpect(status().isOk());
	}

	@Test
	public void test_updatedNewsArticle_ReturnsInCorrectStatus() throws Exception {
		NewsArticle newsArticle2 = new NewsArticle("dummyTitle2", "dummyDescription2dummyDescription2", null,
				"dummyContent2", null, null, null);
		String json2 = new ObjectMapper().writeValueAsString(newsArticle2);
		mvc.perform(put("/api/v1/newsArticle").contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isNotFound());
	}

	@Test
	public void test_getNewsArticlesByTags_ReturnsCorrectStatus() throws Exception {
		Tag tag1 = new Tag("tag1");
		tagRepo.save(tag1);
		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);

		NewsArticle newsArticle1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, tags);

		newsArticleService.createNewsArticle(newsArticle1);
		mvc.perform(get("/api/v1/newsArticle/tags/tag1")).andExpect(status().isOk());
	}

	@Test
	public void test_getNewsArticlesByTags_ReturnsNotfundStatus() throws Exception {
		Tag tag1 = new Tag("tag1");
		tagRepo.save(tag1);
		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);

		NewsArticle newsArticle1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, tags);

		newsArticleService.createNewsArticle(newsArticle1);
		mvc.perform(get("/api/v1/newsArticle/tags/tag2")).andExpect(status().isNotFound());

	}

	@Test
	public void test_getNewsArticlesByKeyword_ReturnsCorrectStatus() throws Exception {
		// Test case 1: Test with existing keyword
		NewsArticle newsArticle1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);
		newsArticleService.createNewsArticle(newsArticle1);
		mvc.perform(get("/api/v1/newsArticle/search/dummy")).andExpect(status().isOk());
	}

	@Test
	public void test_getNewsArticleByKeywor_withNonExistingKeyword() throws Exception {
		mvc.perform(get("/api/v1/newsArticle/search/nonexistingkeyword")).andExpect(status().isNotFound());
	}

	@Test
	public void test_getNewsArticlesByTitle_ReturnsCorrectStatus() throws Exception {
		// Test case 1: Test with existing title
		NewsArticle newsArticle1 = new NewsArticle("dummyTitle1", "dummyDescription1dummyDescription1", LocalDate.now(),
				"dummyContent1", null, null, null);
		newsArticleService.createNewsArticle(newsArticle1);
		mvc.perform(get("/api/v1/newsArticle/titles/dummyTitle1")).andExpect(status().isOk());
	}

	@Test
	public void test_getNewsArticlesByTitle_ReturnNotFoundWithNonExistingTitle() throws Exception {
		mvc.perform(get("/api/v1/newsArticle/titles/nonexistingtitle")).andExpect(status().isNotFound());
	}
}
