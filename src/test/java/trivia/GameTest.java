
package trivia;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GameTest {
	@Test
	public void caracterizationTest() {
		// runs 10.000 "random" games to see the output of old and new code mathces
		for (int seed = 1; seed < 10_000; seed++) {
			testSeed(seed);
		}
	}

	private void testSeed(int seed) {
		String expectedOutput = extractOutput(new Random(seed), new Game());
		String actualOutput = extractOutput(new Random(seed), new GameBetter());
		assertEquals("Change detected for seed " + seed +
						 ". To breakpoint through it, run this seed alone using the (ignored) test below",
			expectedOutput, actualOutput);
	}
	@Test
	@Ignore
	public void oneSeed() {
		testSeed(1);
	}

	private String extractOutput(Random rand, IGame aGame) {
		PrintStream old = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintStream inmemory = new PrintStream(baos)) {
			// WARNING: System.out.println() doesn't work in this try {} as the sysout is captured and recorded in memory.
			System.setOut(inmemory);

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
		} finally {
			System.setOut(old);
		}
		String output = new String(baos.toByteArray());
		// System.out.println(output);
		return output;
	}
}
