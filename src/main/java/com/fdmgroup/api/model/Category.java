package com.fdmgroup.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catgen")
	@SequenceGenerator(name = "catgen", sequenceName = "cat_id_seq", allocationSize = 1)
	private long id;
	@NotBlank(message = "Category name must not be null or blank.")
	@Size(min = 2, max = 250, message = "Category name must be longer than 2 characters and less than 250.")
	private String name;

	public Category() {
		super();
	}

	public Category(String name) {
		super();
		this.name = name;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
