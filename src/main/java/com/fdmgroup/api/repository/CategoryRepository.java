package com.fdmgroup.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Category;

/**
 * The repository interface for managing Category entities. Provides methods for
 * filtering and managing Category data.
 *
 * <p>
 * The TagRepository interface also includes additional custom methods for
 * advanced filtering.
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.fdmgroup.api.model.Category
 * @author Antonios Papatheodorou
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findAll();

	public Optional<Category> findById(long id);

	@Query("select cat from Category cat where lower(cat.name) like concat('%', lower(:name), '%')")
	public Optional<Category> findByName(String name);

}
