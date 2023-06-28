package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Author;
import com.fdmgroup.api.repository.AuthorRepository;

/**
 * <h1>Contains CRUD functionality and filtering methods</h1>
 * 
 * @author Jakob Buergermeister
 * @see com.fdmgroup.api.model.Author
 * @see com.fdmgroup.api.repository.AuthorRepository
 */

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authRepo;

	public List<Author> getAllAuthors() {
		return authRepo.findAll();
	}

	public Author getAuthorByName(String name) {
		Optional<Author> authOpt = authRepo.findByName(name);

		if (authOpt.isPresent()) {
			return authOpt.get();
		}
		return null;
	}

	public Author getAuthorById(long id) {
		Optional<Author> authOpt = authRepo.findById(id);

		if (authOpt.isPresent()) {
			return authOpt.get();
		}
		return null;
	}

	public Author createAuthor(Author author) {
		Optional<Author> authOpt = authRepo.findByName(author.getName());
		if (authOpt.isEmpty()) {
			return authRepo.save(author);
		}
		return null;
	}

	public boolean updateAuthor(Author author) {
		if (authRepo.existsById(author.getId())) {
			authRepo.save(author);
			return true;
		}
		return false;
	}

	public boolean removeAuthorById(long id) {
		if (authRepo.existsById(id)) {
			authRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public boolean removeAuthor(Author author) {
		if (authRepo.existsById(author.getId())) {
			authRepo.deleteById(author.getId());
			return true;
		}
		return false;
	}

	public List<Author> getAuthorsByNameKeyword(String keyword) {
		return authRepo.searchNames(keyword);
	}

}
