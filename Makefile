up:
	java -cp target/kata-trivia-java-1.0-SNAPSHOT.jar trivia.PlayGame

install:
	mvn package

run: compile exec

compile:
	mvn compile

exec:
	mvn exec:java

test:
	mvn test

dependencies:
	mvn dependency:go-offline
