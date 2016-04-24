package com.dfp.persistencia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BilleteImagen {

	@Id // id de la entidad
	@GeneratedValue(strategy = GenerationType.IDENTITY) // el proovedor de
														// persistencia gnerar
														// un valor al
														// insertarlo y se lo da
	private String id;

	private String email;

	private byte[] image;

	public BilleteImagen() {

	}

	public BilleteImagen(String email, byte[] image) {
		this.email = email;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}






}
