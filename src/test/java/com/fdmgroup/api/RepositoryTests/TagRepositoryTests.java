package com.fdmgroup.api.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.TagRepository;

@DataJpaTest
public class TagRepositoryTests {

	@Autowired
	private TagRepository tagRepository;

	@BeforeEach
	public void setUp() {
		tagRepository.save(new Tag("News"));
		tagRepository.save(new Tag("Sports"));
	}

	@Test
	public void findById_ExistingTag_ReturnsOptionalOfTag() {
		Optional<Tag> tagIdOptional = tagRepository.findTagByName("News");
		Optional<Tag> tagOptional = tagRepository.findById(tagIdOptional.get().getId());

		assertThat(tagOptional).isPresent();
		assertThat(tagOptional.get().getName()).isEqualTo("News");
	}

	@Test
	public void findById_NonExistingTag_ReturnsEmptyOptional() {
		Optional<Tag> tagOptional = tagRepository.findById(100L);

		assertThat(tagOptional).isEmpty();
	}

	@Test
	public void findTagByName_ExistingTag_ReturnsOptionalOfTag() {
		Optional<Tag> tagOptional = tagRepository.findTagByName("Sports");

		assertThat(tagOptional).isPresent();
		assertThat(tagOptional.get().getId()).isEqualTo(tagOptional.get().getId());
	}

	@Test
	public void findTagByName_NonExistingTag_ReturnsEmptyOptional() {
		Optional<Tag> tagOptional = tagRepository.findTagByName("Technology");

		assertThat(tagOptional).isEmpty();
	}

	@Test
	public void existsById_ExistingTagId_ReturnsTrue() {
		boolean exists = tagRepository.existsById(1L);

		assertThat(exists).isTrue();
	}

	@Test
	public void existsById_NonExistingTagId_ReturnsFalse() {
		boolean exists = tagRepository.existsById(13L);

		assertThat(exists).isFalse();
	}

	@Test
	public void save_ValidTag_ReturnsSavedTag() {
		Tag newTag = new Tag("Technology");
		Tag savedTag = tagRepository.save(newTag);

		assertThat(savedTag.getId()).isNotNull();
		assertThat(savedTag.getName()).isEqualTo("Technology");
	}

	@Test
	public void deleteById_ExistingTagId_DeletesTag() {
		tagRepository.deleteById(1L);

		assertThat(tagRepository.findById(1L)).isEmpty();
	}

	@Test
	public void deleteById_ExistingTagId_RemovesTag() {
		Tag newTag = new Tag("Technology");
		Tag savedTag = tagRepository.save(newTag);

		long beforeCount = tagRepository.count();

		tagRepository.deleteById(savedTag.getId());

		long afterCount = tagRepository.count();

		assertTrue(beforeCount > afterCount);
		assertFalse(tagRepository.findById(savedTag.getId()).isPresent());
	}

}
