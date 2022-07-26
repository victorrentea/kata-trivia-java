package main.java.trivia;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public abstract class Subject implements Constants {

	protected String name;
	private LinkedList<String> questions;
	protected List<Integer> subjectPositions;
	private Properties properties;

	public Subject() {
		this.questions = new LinkedList<>();
		subjectPositions = new LinkedList<>();
	}

	public void addQuestion(int index) {
		questions.addLast(createQuestion(index));
	}

	public String createQuestion(int index) {
		try {
			return properties != null ? properties.getProperty(name.toLowerCase() + "_question_" + index) : (name + " Question " + index);
		} catch (Exception e) {
			e.printStackTrace();
			return "*";
		}
	}

	public void printQuestion() {
		System.out.println(questions.removeFirst());
	}

	public void askQuestionAccordingToPosition(int place) {
		if (subjectPositions.contains(place)) {
			printQuestion();
		}
	}

	public boolean isPlaceFromSubject(int place) {
		return subjectPositions.contains(place);
	}

	public String getName() {
		return name;
	}
	
	protected void getPropertiesFile() {
		properties = new Properties();
		try {
			properties.load(Subject.class.getClassLoader().getResourceAsStream(name.toLowerCase() + ".properties"));
		} 
		catch (Exception ex) {
		    ex.printStackTrace();
		}
	}

}
