package trivia;

import java.util.Arrays;

public class RockSubject extends Subject {

	public RockSubject() {
		super();
		this.name = ROCK;
		this.subjectPositions = Arrays.asList(3, 7, 11);
		//#CR
		/*
		this.subjectPositions = Arrays.asList(3, 7); //for the CR, where Geography is 11 and Rock is just 3 and 7
		this.getPropertiesFile();
		*/
	}
}
