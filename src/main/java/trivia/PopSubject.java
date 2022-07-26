package trivia;

import java.util.Arrays;

public class PopSubject extends Subject {

	public PopSubject() {
		super();
		this.name = POP;
		this.subjectPositions = Arrays.asList(0, 4, 8);
		//#CR
//		this.getPropertiesFile();
	}
}
