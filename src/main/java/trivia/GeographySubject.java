package trivia;

import trivia.Subject;

import java.util.Arrays;

public class GeographySubject extends Subject {
	
	public GeographySubject() {
		super();
		this.name = GEOGRAPHY;
		this.subjectPositions = Arrays.asList(11);
		this.getPropertiesFile();
	}

}
