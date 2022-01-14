
package trivia;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import trivia.tools.CaptureSystemOutput;
import trivia.tools.CaptureSystemOutput.OutputCapture;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

	@Test
	@CaptureSystemOutput
	public void caracterizationTest(OutputCapture outputCapture) throws IOException {

		for (int seed = 1; seed <= 2; seed++) {
			Random pseudoRandomSequence = new Random(seed);
			runGame(pseudoRandomSequence, new Game());

			String actualOutput = outputCapture.toString();
			String fileName = "/tests/run-" + seed + ".txt";
			try (InputStream expectedStream = GameTest.class.getResourceAsStream(fileName)) {
				if (expectedStream == null) {
					throw new IllegalArgumentException("Expected output file not found: " + fileName);
				}
				String expectedOutput = IOUtils.toString(expectedStream, StandardCharsets.UTF_8);
				assertEquals(expectedOutput, actualOutput, "File: " + fileName);
			}
		}
	}

	private String runGame(Random rand, Game aGame) {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try (PrintStream inmemory = new PrintStream(baos)) {
//			System.setOut(inmemory);

			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");
			
			boolean notAWinner = false;
			do {
				aGame.roll(rand.nextInt(5) + 1);
				
				if (rand.nextInt(9) == 7) {
					notAWinner = aGame.wrongAnswer();
				} else {
					notAWinner = aGame.wasCorrectlyAnswered();
				}
				
			} while (notAWinner);
//		}
//		String output = new String(baos.toByteArray());
//		return output;
		return "";
	}
}
