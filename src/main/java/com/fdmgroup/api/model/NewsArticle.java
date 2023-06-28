package com.fdmgroup.api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class NewsArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsartgen")
	@SequenceGenerator(name = "newsartgen", sequenceName = "newsart_id_seq", allocationSize = 1)
	private long id;
	@NotBlank(message = "Title must not be null or blank.")
	@Size(min = 2, max = 250, message = "Title must be longer than 2 characters and less than 250.")
	private String title;
	@NotBlank(message = "Description must not be null or blank.")
	@Size(min = 10, max = 2500, message = "Description must be longer than 20 characters and less than 2500.")
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishDate;
	@NotBlank(message = "Content must not be null or blank.")
	@Size(min = 10, max = 2500, message = "Description must be longer than 20 characters and less than 2500.")
	private String content;
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "FK_CATEGORY_ID")
	private Category category;
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "FK_AUTHOR_ID")
	private Author author;
	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "ARTICLES_TAGS", joinColumns = @JoinColumn(name = "ARTICLES_ID"), inverseJoinColumns = @JoinColumn(name = "TAGS_ID"))
	private List<Tag> tags;

	public NewsArticle() {
		super();
	}

	public NewsArticle(String title, String description, LocalDate publishDate, String content, Category category,
			Author author, List<Tag> tags) {
		super();
		this.title = title;
		this.description = description;
		this.publishDate = publishDate;
		this.content = content;
		this.category = category;
		this.author = author;
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsArticle other = (NewsArticle) obj;
		return id == other.id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
