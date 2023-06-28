package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.repository.TagRepository;

/**
 * The service class for managing Tag entities. Provides CRUD functionality and
 * additional methods for Tag operations.
 *
 * <p>
 * This class is responsible for handling business logic related to Tags.
 * </p>
 *
 * @see com.fdmgroup.api.model.Tag
 * @see com.fdmgroup.api.repository.TagRepository
 * @author Condelizza Kablan
 */
@Service
public class TagService {

	@Autowired
	private TagRepository tagRepo;

	/**
	 * Retrieves all tags from the repository.
	 *
	 * @return A list of all tags.
	 */
	public List<Tag> getAllTags() {
		return tagRepo.findAll();
	}

	/**
	 * Creates a new tag in the repository. If a tag with the same name already
	 * exists, it won't be created.
	 *
	 * @param tag The tag to create.
	 * @return The created tag if successful, or null if a tag with the same name
	 *         already exists.
	 */
	public Tag createNewTag(Tag tag) {
		Optional<Tag> existingTag = tagRepo.findTagByName(tag.getName());
		if (existingTag.isPresent()) {
			return null;
		} else {
			return tagRepo.save(tag);
		}
	}

	/**
	 * Retrieves a tag by its name.
	 *
	 * @param name The name of the tag to retrieve.
	 * @return The tag if found, or null if not found.
	 */
	public Tag getTagbyName(String name) {
		Optional<Tag> tagOptional = tagRepo.findTagByName(name);

		if (tagOptional.isPresent()) {
			return tagOptional.get();
		}

		return null;
	}

	/**
	 * Retrieves a tag by its unique identifier.
	 *
	 * @param id The unique identifier of the tag to retrieve.
	 * @return The tag if found, or null if not found.
	 */
	public Tag getTagById(long id) {
		Optional<Tag> tagOpt = tagRepo.findById(id);

		if (tagOpt.isPresent()) {
			return tagOpt.get();
		}

		return null;
	}

	/**
	 * Updates an existing tag in the repository.
	 *
	 * @param tag The tag to update.
	 * @return true if the tag was successfully updated, false if the tag does not
	 *         exist.
	 */
	public boolean updateTags(Tag tag) {
		if (tagRepo.existsById(tag.getId())) {
			tagRepo.save(tag);
			return true;
		}

		return false;
	}

	/**
	 * Removes an existing tag from the repository.
	 *
	 * @param tag The tag to remove.
	 * @return true if the tag was successfully removed, false if the tag does not
	 *         exist.
	 */
	public boolean removeTags(int tagId) {
		if (tagRepo.existsById(tagId)) {
			tagRepo.deleteById(tagId);
			return true;
		}

		return false;
	}
}
