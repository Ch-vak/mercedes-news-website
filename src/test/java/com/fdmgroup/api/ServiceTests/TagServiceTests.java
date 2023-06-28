package com.fdmgroup.api.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.TagRepository;
import com.fdmgroup.api.service.TagService;

@SpringBootTest
public class TagServiceTests {

	@Mock
	private TagRepository tagRepository;

	@InjectMocks
	private TagService tagService;

	private Tag tag1;
	private Tag tag2;
	private Tag tag3;

	@BeforeEach
	void setUp() {
		tag1 = new Tag("News");
		tag2 = new Tag("Sports");
		tag3 = new Tag("Technology");
	}

	@Test
	public void getAllTags_ReturnsListOfTags() {
		List<Tag> expectedTags = Arrays.asList(tag1, tag2, tag3);

		when(tagRepository.findAll()).thenReturn(expectedTags);

		List<Tag> actualTags = tagService.getAllTags();

		assertEquals(expectedTags.size(), actualTags.size());
		assertEquals(expectedTags, actualTags);
	}

	@Test
	public void createNewTag_TagNameNotExists_CreatesTag() {
		Tag newTag = new Tag("Entertainment");
		when(tagRepository.findTagByName(newTag.getName())).thenReturn(Optional.empty());
		when(tagRepository.save(any(Tag.class))).thenReturn(newTag);

		Tag createdTag = tagService.createNewTag(newTag);

		assertNotNull(createdTag);
		assertEquals(newTag.getName(), createdTag.getName());
		verify(tagRepository, times(1)).save(newTag);
	}

	@Test
	public void createNewTag_TagNameExists_ReturnsNull() {
		Tag existingTag = new Tag("Sports");
		when(tagRepository.findTagByName(existingTag.getName())).thenReturn(Optional.of(existingTag));

		Tag createdTag = tagService.createNewTag(existingTag);

		assertNull(createdTag);
		verify(tagRepository, never()).save(existingTag);
	}

	@Test
	public void getTagbyName_TagExists_ReturnsTag() {
		String tagName = "Sports";
		when(tagRepository.findTagByName(tagName)).thenReturn(Optional.of(tag2));

		Tag retrievedTag = tagService.getTagbyName(tagName);

		assertNotNull(retrievedTag);
		assertEquals(tag2, retrievedTag);
	}

	@Test
	public void getTagbyName_TagNotExists_ReturnsNull() {
		String tagName = "Technology";
		when(tagRepository.findTagByName(tagName)).thenReturn(Optional.empty());

		Tag retrievedTag = tagService.getTagbyName(tagName);

		assertNull(retrievedTag);
	}

	@Test
	public void getTagById_TagExists_ReturnsTag() {
		long tagId = 1L;
		when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag1));

		Tag retrievedTag = tagService.getTagById(tagId);

		assertNotNull(retrievedTag);
		assertEquals(tag1, retrievedTag);
	}

	@Test
	public void getTagById_TagNotExists_ReturnsNull() {
		long tagId = 1L;
		when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

		Tag retrievedTag = tagService.getTagById(tagId);

		assertNull(retrievedTag);
	}

	@Test
	public void updateTags_TagExists_UpdatesTag() {
		Tag updatedTag = new Tag("Sports");
		updatedTag.setId(1L);

		when(tagRepository.existsById(updatedTag.getId())).thenReturn(true);
		when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);

		boolean isUpdated = tagService.updateTags(updatedTag);

		assertTrue(isUpdated);
		verify(tagRepository, times(1)).save(updatedTag);
	}

	@Test
	public void updateTags_TagNotExists_ReturnsFalse() {
		Tag updatedTag = new Tag("Technology");
		updatedTag.setId(1L);

		when(tagRepository.existsById(updatedTag.getId())).thenReturn(false);

		boolean isUpdated = tagService.updateTags(updatedTag);

		assertFalse(isUpdated);
		verify(tagRepository, never()).save(updatedTag);
	}

	@Test
	public void removeTags_TagExists_RemovesTag() {
		int tagId = 1;
		when(tagRepository.existsById(tagId)).thenReturn(true);

		boolean isRemoved = tagService.removeTags(tagId);

		assertTrue(isRemoved);
		verify(tagRepository, times(1)).deleteById(tagId);
	}

	@Test
	public void removeTags_TagNotExists_ReturnsFalse() {
		int tagId = 1;
		when(tagRepository.existsById(tagId)).thenReturn(false);

		boolean isRemoved = tagService.removeTags(tagId);

		assertFalse(isRemoved);
		verify(tagRepository, never()).deleteById(tagId);
	}
}
