package com.fdmgroup.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taggen")
	@SequenceGenerator(name = "taggen", sequenceName = "tag_id_seq", allocationSize = 1)
	private long id;
	@NotBlank(message = "Tag name must not be null or blank.")
	@Size(min = 2, max = 20, message = "First name must be longer than 2 characters and less than 20.")
	private String name;

	public Tag() {
		super();
	}

	public Tag(String name) {
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
