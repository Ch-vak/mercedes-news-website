package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.NewsArticle;
import com.fdmgroup.api.repository.NewsArticleRepository;

/**
 * <h1>Contains Crud functionality and implementing the following methods</h1>
 * <ul>
 * <li>findByTitle</li>
 * <li>findAllByTags</li>
 * <li>findAllByContentKeyword</li>
 * </ul>
 * 
 * @see com.fdmgroup.api.model.NewsArticle
 * @see com.fdmgroup.api.repository.NewsArticleRepository
 * 
 * @author Chrysostomos Vakasiras
 */

@Service
public class NewsArticleService {

	@Autowired
	private NewsArticleRepository newsArticleRepo;

	/**
	 * Returns all news Articles
	 */
	public List<NewsArticle> getAllnewsArticles() {
		return newsArticleRepo.findAll();
	}

	/**
	 * Creates news Article if title does not exist
	 */
	public NewsArticle createNewsArticle(NewsArticle newsArticle) {
		Optional<NewsArticle> existingNewsArticle = newsArticleRepo.findByTitle(newsArticle.getTitle());
		if (existingNewsArticle.isEmpty()) {
			return newsArticleRepo.save(newsArticle);
		} else {
			return null;
		}
	}

	/**
	 * Returns news Article based on id
	 */
	public NewsArticle getNewsArticle(long id) {
		Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);
		if (newsArticle.isPresent()) {
			return newsArticle.get();
		}
		return null;
	}

	/**
	 * Updates news Article
	 */
	public boolean updateNewsArticle(NewsArticle newsArticle) {
		if (newsArticleRepo.existsById(newsArticle.getId())) {
			newsArticleRepo.save(newsArticle);
			return true;
		}
		return false;
	}

	/**
	 * Deletes news Article based on id
	 */
	public boolean removeNewsArticle(long id) {
		if (newsArticleRepo.existsById(id)) {
			newsArticleRepo.deleteById(id);
			return true;
		}
		return false;
	}


	public List<NewsArticle> findTagByName(String name) {
		return newsArticleRepo.findTagByName(name);
	}

	/**
	 * Returns news Article based on Keyword
	 */
	public List<NewsArticle> getNewsArticlesByKeyword(String keyword) {
		return newsArticleRepo.findAllByContentKeyword(keyword);
	}

	/**
	 * Returns news Article based on title
	 */
	public NewsArticle getNewsArticleByTitle(String title) {
		Optional<NewsArticle> newsArticle = newsArticleRepo.findByTitle(title);
		if (newsArticle.isPresent()) {
			return newsArticle.get();
		}
		return null;
	}

}
