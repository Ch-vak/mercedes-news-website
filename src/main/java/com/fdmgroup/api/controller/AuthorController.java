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

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.service.AuthorService;

/**
 * 
 * <p>
 * This controller provides endpoints for CRUD operations and other
 * Author-related operations.
 * </p>
 *
 * @author Jakob Buergermeister
 * @see AuthorService
 */

@RestController
@RequestMapping("/api/v1/author")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping
	public List<Author> getAuthors() {
		return authorService.getAllAuthors();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Author> getAuthor(@PathVariable long id) {
		Author author = authorService.getAuthorById(id);

		if (author != null) {
			return ResponseEntity.status(HttpStatus.OK).body(author);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable long id) {
		if (authorService.removeAuthorById(id)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
		Author newAuthor = authorService.createAuthor(author);

		if (newAuthor != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{authorId}")
					.buildAndExpand(newAuthor.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@PutMapping
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {

		if (authorService.updateAuthor(author)) {
			return ResponseEntity.ok(author);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<Author>> getAuthorsByNameKeyword(@PathVariable("keyword") String keyword) {
		List<Author> authors = authorService.getAuthorsByNameKeyword(keyword);

		if (authors.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(authors);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
