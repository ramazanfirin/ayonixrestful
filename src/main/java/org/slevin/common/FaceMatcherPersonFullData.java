package org.slevin.common;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="FaceMacherPersonFullData")
public class FaceMatcherPersonFullData {
			 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	String name;
	String path;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	//@Column(columnDefinition="TEXT")
	private byte[] afid;

	
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public byte[] getAfid() {
		return afid;
	}
	public void setAfid(byte[] afid) {
		this.afid = afid;
	}
}
