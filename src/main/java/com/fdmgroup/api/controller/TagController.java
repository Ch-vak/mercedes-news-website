package com.fdmgroup.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.api.model.Tag;
import com.fdmgroup.api.service.TagService;

/**
 * The REST controller for managing Tag entities.
 *
 * <p>
 * This controller provides endpoints for CRUD operations and other Tag-related
 * operations.
 * </p>
 *
 * @see com.fdmgroup.api.model.Tag
 * @see com.fdmgroup.api.service.TagService
 */
@RestController
@RequestMapping("/api/v1/Tags")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

	@Autowired
	private TagService tagService;

	/**
	 * Retrieves all tags.
	 *
	 * @return A list of all tags.
	 */
	@GetMapping
	public List<Tag> getTags() {
		return tagService.getAllTags();
	}

	/**
	 * Retrieves a tag by its unique identifier.
	 *
	 * @param id The unique identifier of the tag.
	 * @return The tag if found, or a 404 Not Found response if the tag does not
	 *         exist.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Tag> getTag(@PathVariable long id) {
		Tag tagId = tagService.getTagById(id);

		if (tagId != null) {
			return ResponseEntity.status(HttpStatus.OK).body(tagId);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Deletes a tag by its unique identifier.
	 *
	 * @param id The unique identifier of the tag to delete.
	 * @return A 200 OK response if the tag was successfully deleted, or a 404 Not
	 *         Found response if the tag does not exist.
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable int tagId) {
		if (tagService.removeTags(tagId)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Creates a new tag.
	 *
	 * @param tag The tag to create.
	 * @return A 201 Created response with the URI of the created tag if successful,
	 *         or a 409 Conflict response if a tag with the same name already
	 *         exists.
	 */
	@PostMapping
	public ResponseEntity<Tag> createNewTag(@RequestBody Tag tag) {
		Tag createdTag = tagService.createNewTag(tag);

		if (createdTag != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{TagId}")
					.buildAndExpand(createdTag.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	/**
	 * Updates an existing tag.
	 *
	 * @param tag The updated tag.
	 * @return The updated tag if it exists, or a 404 Not Found response if the tag
	 *         does not exist.
	 */
	@PutMapping
	public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) {

		if (tagService.updateTags(tag)) {
			return ResponseEntity.ok(tag);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
