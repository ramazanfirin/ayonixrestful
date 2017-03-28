package org.slevin.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AyonixPerson")
public class AyonixPerson implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	String name;
	String username;
	String userId;
	String afid;
	
	
	public String getAfid() {
		return afid;
	}
	public void setAfid(String afid) {
		this.afid = afid;
	}
	public AyonixPerson(String name, String username, String userId) {
		super();
		this.name = name;
		this.username = username;
		this.userId = userId;
	}
	public AyonixPerson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
