package org.slevin.util;

import org.slevin.common.FaceMatcherPerson;

public class SearchResultDTO {
	FaceMatcherPerson person;
	float score;
	
	
	public FaceMatcherPerson getPerson() {
		return person;
	}
	public void setPerson(FaceMatcherPerson person) {
		this.person = person;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
}
