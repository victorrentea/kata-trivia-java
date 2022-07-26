package trivia;

import main.java.trivia.cr.GeographySubject;

public class SubjectFactory implements Constants {

	public Subject createSubject(String subjectType) {
		
		if (subjectType == null) {
			return null;
		}
		
		if (subjectType.equalsIgnoreCase(POP)) {
			return new PopSubject();
		} else if (subjectType.equalsIgnoreCase(SCIENCE)) {
			return new ScienceSubject();
		} else if (subjectType.equalsIgnoreCase(SPORTS)) {
			return new SportsSubject();
		} else if (subjectType.equalsIgnoreCase(ROCK)) {
			return new RockSubject();
		} else if (subjectType.equalsIgnoreCase(GEOGRAPHY)) {
			return new GeographySubject();
		}
		
		return null;
	}
}
