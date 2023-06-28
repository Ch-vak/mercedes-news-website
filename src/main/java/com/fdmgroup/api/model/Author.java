package com.fdmgroup.api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorgen")
	@SequenceGenerator(name = "authorgen", sequenceName = "author_id_seq", allocationSize = 1)
	private long id;
	@NotBlank(message = "First name must not be null or blank.")
	@Size(min = 2, max = 250, message = "First name must be longer than 2 characters and less than 250.")
	private String name;
	private LocalDate birthday;
	@NotBlank(message = "Nationality must not be null or blank.")
	@Size(min = 2, max = 250, message = "Nationality must be longer than 2 characters and less than 250.")
	private String nationality;

	public Author() {
		super();
	}

	public Author(String name, LocalDate birthday, String nationality) {
		super();
		this.name = name;
		this.birthday = birthday;
		this.nationality = nationality;
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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
