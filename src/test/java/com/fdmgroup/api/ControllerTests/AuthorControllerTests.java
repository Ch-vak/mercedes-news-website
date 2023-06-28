package com.fdmgroup.api.ControllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fdmgroup.api.controller.AuthorController;
import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.service.AuthorService;

@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AuthorControllerTests {

	@InjectMocks
	AuthorController controller;

	@Mock
	AuthorService authorService;

	@Test
	void test_getAuthorsReturnCorrectList() {
		List<Author> expectedAuthors = Arrays.asList(new Author("Author 1", null, null),
				new Author("Author 2", null, null));

		when(authorService.getAllAuthors()).thenReturn(expectedAuthors);

		List<Author> actualAuthors = controller.getAuthors();

		assertThat(actualAuthors).isEqualTo(expectedAuthors);
	}

	@Test
	void test_getAuthorReturnOk() {
		long authorId = 0L;

		when(authorService.getAuthorById(authorId)).thenReturn(new Author("Author 1", null, null));

		ResponseEntity<Author> response = controller.getAuthor(authorId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(authorId);
		assertThat(response.getBody().getName()).isEqualTo("Author 1");
	}

	@Test
	void test_getAuthorReturnNotFound() {
		long authorId = 1L;

		when(authorService.getAuthorById(authorId)).thenReturn(null);

		ResponseEntity<Author> response = controller.getAuthor(authorId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void test_deleteAuthorReturnOk() {
		long authorId = 1L;

		when(authorService.removeAuthorById(authorId)).thenReturn(true);

		ResponseEntity<Void> response = controller.deleteAuthor(authorId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void test_deleteAuthorReturnNotFound() {
		long authorId = 1L;

		when(authorService.removeAuthorById(authorId)).thenReturn(false);

		ResponseEntity<Void> response = controller.deleteAuthor(authorId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void test_createAuthorReturnsCreated() {
		Author author = new Author("Test Author", LocalDate.now(), null);

		when(authorService.createAuthor(author)).thenReturn(author);

		ResponseEntity<Author> response = controller.createAuthor(author);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	void test_createAuthorReturnConflict() {
		Author author = new Author("Author 1", null, null);

		when(authorService.createAuthor(author)).thenReturn(null);

		ResponseEntity<Author> response = controller.createAuthor(author);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test
	void test_updateAuthorReturnOk() {
		Author author = new Author("Author 1", null, null);

		when(authorService.updateAuthor(author)).thenReturn(true);

		ResponseEntity<Author> response = controller.updateAuthor(author);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(0L);
	}

	@Test
	void test_updateAuthorReturnNotFound() {
		Author author = new Author("Author 1", null, null);

		when(authorService.updateAuthor(author)).thenReturn(false);

		ResponseEntity<Author> response = controller.updateAuthor(author);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void test_getAuthorsByNameKeywordReturnOk() {
		String keyword = "John";

		List<Author> expectedAuthors = Arrays.asList(new Author("John 1", null, null),
				new Author("Author 1", null, null));

		when(authorService.getAuthorsByNameKeyword(keyword)).thenReturn(expectedAuthors);

		ResponseEntity<List<Author>> response = controller.getAuthorsByNameKeyword(keyword);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expectedAuthors);
	}

	@Test
	void test_getAuthorsByNameKeywordReturnNotFound() {
		String keyword = "John";

		when(authorService.getAuthorsByNameKeyword(keyword)).thenReturn(new ArrayList<>());

		ResponseEntity<List<Author>> response = controller.getAuthorsByNameKeyword(keyword);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
