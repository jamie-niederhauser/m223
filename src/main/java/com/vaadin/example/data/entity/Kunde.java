package com.vaadin.example.data.entity;

public class Kunde {

	private Long id;
	private String name;
	private String vorname;
	private String email;

	public Kunde() {
	}

	public Kunde(String name, String vorname, String email) {
		this.name = name;
		this.vorname = vorname;
		this.email = email;

	}

	public String getName() {
		return name;
	}

	public String getVorname() {
		return vorname;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public String toString() {
		return String.format("Kunde[title= %s, producer = %d]", name, vorname, email);
	}
}
