package main.java.trivia.cr;

import java.util.Arrays;

import main.java.trivia.Subject;

public class GeographySubject extends Subject {
	
	public GeographySubject() {
		super();
		this.name = GEOGRAPHY;
		this.subjectPositions = Arrays.asList(11);
		this.getPropertiesFile();
	}

}
