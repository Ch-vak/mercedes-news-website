package com.fdmgroup.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.NewsArticle;

/**
 * <h1>Contains 3 Methods for filtering</h1>
 * <ul>
 * <li>findByTitle</li>
 * <li>findAllByTags</li>
 * <li>findAllByContentKeyword</li>
 * </ul>
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @return an List over the elements in this list.Titles are unique therefore
 *         expect Optional
 * @author Chrysostomos Vakasiras
 */

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

	Optional<NewsArticle> findByTitle(String title);

	@Query("select a from NewsArticle a where lower(a.content) like concat('%', lower(:keyword), '%')")
	List<NewsArticle> findAllByContentKeyword(String keyword);

	boolean existsById(long id);

	void deleteById(long id);

	@Query("SELECT a FROM NewsArticle a JOIN a.tags t WHERE lower(t.name) LIKE  lower(:name)")
	List<NewsArticle> findTagByName(String name);

}
