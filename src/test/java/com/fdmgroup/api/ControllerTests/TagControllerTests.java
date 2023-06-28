package com.fdmgroup.api.ControllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.api.controller.TagController;
import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.service.TagService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class TagControllerTests {

	@InjectMocks
	TagController controller;

	@Mock
	TagService tagService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_getTagsReturnsCorrectList() {
		List<Tag> expectedTags = new ArrayList<>();
		expectedTags.add(new Tag("tag1"));
		expectedTags.add(new Tag("tag2"));

		when(tagService.getAllTags()).thenReturn(expectedTags);

		List<Tag> actualTags = controller.getTags();

		assertThat(actualTags).isEqualTo(expectedTags);

	}

	@Test
	public void getTag_returns_correctStatusandTag() {
		Tag tag = new Tag();
		tag.setId(1L);
		tag.setName("test");

		when(tagService.getTagById(1L)).thenReturn(tag);

		ResponseEntity<Tag> response = controller.getTag(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(tag, response.getBody());
	}

	@Test
    public void getTag_returns_IncorrectStatus() {

        when(tagService.getTagById(100L)).thenReturn(null);

        ResponseEntity<Tag> response = controller.getTag(100L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

	@Test
	public void deleteTag_ReturnCorrectStatuls() {
		int tagId = 1;

		when(tagService.removeTags(tagId)).thenReturn(true);

		ResponseEntity<Void> response = controller.deleteTag(tagId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deleteTag_withWrongID_returnsNotFound() {
		int tagId = 100;

		when(tagService.removeTags(tagId)).thenReturn(false);

		ResponseEntity<Void> response = controller.deleteTag(tagId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void createNewTag_createsSuccessfullWhithCorrectCredentials() throws Exception {
		Tag tag = new Tag();
		tag.setId(1);
		tag.setName("test");

		String json = new ObjectMapper().writeValueAsString(tag);
		mockMvc.perform(post("/api/v1/Tags").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated());

	}

	@Test
	public void createNewTag_ReturnsConflict() {
		Tag tag = new Tag();

		when(tagService.createNewTag(tag)).thenReturn(null);

		ResponseEntity<Tag> response = controller.createNewTag(tag);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void updateTag_worksCorrectlyWithcorrectID() {
		Tag tag = new Tag();
		tag.setId(1);
		tag.setName("test");

		when(tagService.updateTags(tag)).thenReturn(true);

		ResponseEntity<Tag> response = controller.updateTag(tag);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void updateTag_ReturnsNotFoundForIncorrectID() {
		Tag tag = new Tag();

		when(tagService.updateTags(tag)).thenReturn(false);

		ResponseEntity<Tag> response = controller.updateTag(tag);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
