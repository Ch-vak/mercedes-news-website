package com.fdmgroup.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Author;

/**
 * <h1>Contains 3 Methods for filtering</h1>
 * <ul>
 * <li>findByName</li>
 * <li>findAll</li>
 * <li>searchNames</li>
 * </ul>
 * 
 * @author Jakob Buergermeister
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query("select au from Author au where lower(au.name) like concat('%', lower(:keyword), '%')")
	List<Author> searchNames(String keyword);

	List<Author> findAll();

	Optional<Author> findByName(String name);

	boolean existsById(long id);

	void deleteById(long id);

}
