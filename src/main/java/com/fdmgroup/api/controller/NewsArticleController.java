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

import com.fdmgroup.api.model.NewsArticle;
import com.fdmgroup.api.service.NewsArticleService;

/**
 * <p>
 * This controller provides endpoints for CRUD operations and other
 * NewsArticle-related operations.
 * </p>
 * Controller connected with the Service Layer
 * 
 * @author Chrysostomos Vakasiras
 */

@RestController
@RequestMapping("/api/v1/newsArticle")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsArticleController {

	@Autowired
	private NewsArticleService newsArticleService;

	/**
	 * Returns all news Articles
	 */
	@GetMapping
	public List<NewsArticle> getAllNewsArtilces() {
		return newsArticleService.getAllnewsArticles();
	}

	/**
	 * Returns news Article based on id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<NewsArticle> getNewsArticle(@PathVariable long id) {
		NewsArticle newsArticle = newsArticleService.getNewsArticle(id);

		if (newsArticle != null) {
			return ResponseEntity.status(HttpStatus.OK).body(newsArticle);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Deletes news Article based on id
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNewsArticle(@PathVariable long id) {
		if (newsArticleService.removeNewsArticle(id)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Creates news Article
	 */
	@PostMapping
	public ResponseEntity<NewsArticle> createNewsArticle(@RequestBody NewsArticle newsArticle) {
		NewsArticle createdNewsArticle = newsArticleService.createNewsArticle(newsArticle);
		if (createdNewsArticle != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{articleId}")
					.buildAndExpand(createdNewsArticle.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	/**
	 * Updates news Article
	 */
	@PutMapping
	public ResponseEntity<NewsArticle> updatedNewsArticle(@RequestBody NewsArticle newsArticle) {

		if (newsArticleService.updateNewsArticle(newsArticle)) {
			return ResponseEntity.ok(newsArticle);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Returns news Articles based on certain Tag First is using the PathVariable
	 * passed to find the corresponding Tag and then uses the tag to match all
	 * related articles
	 * 
	 * @see findTagsByName
	 */
	@GetMapping("/tags/{name}")
	public ResponseEntity<List<NewsArticle>> getNewsArticlesByTags(@PathVariable("name") String name) {
		List<NewsArticle> newsArticles = newsArticleService.findTagByName(name);
		if (newsArticles.size() != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(newsArticles);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Returns news Article based on Keyword
	 */
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<NewsArticle>> getNewsArticlesByKeyword(@PathVariable("keyword") String keyword) {
		List<NewsArticle> newsArticles = newsArticleService.getNewsArticlesByKeyword(keyword);
		if (newsArticles.size() != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(newsArticles);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Returns news Article based on title
	 */
	@GetMapping("/titles/{title}")
	public ResponseEntity<NewsArticle> getNewsArticlesByTitle(@PathVariable("title") String title) {
		NewsArticle newsArticle = newsArticleService.getNewsArticleByTitle(title);
		if (newsArticle != null) {
			return ResponseEntity.status(HttpStatus.OK).body(newsArticle);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
