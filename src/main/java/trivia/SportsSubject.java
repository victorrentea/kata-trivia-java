package main.java.trivia;

import java.util.Arrays;

public class SportsSubject extends Subject {

	public SportsSubject() {
		super();
		this.name = SPORTS;
		this.subjectPositions = Arrays.asList(2, 6, 10);
		//#CR
//		this.getPropertiesFile();
	}
}
