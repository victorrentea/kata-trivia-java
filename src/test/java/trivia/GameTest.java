package trivia;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GameTest {
	private String extractOutput(Random rand, IGame aGame) {
		PrintStream old = System.out;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try (PrintStream inmemory = new PrintStream(output)) {

			// WARNING: System.out.println() doesn't work in this try {} as the sysout is captured and recorded in memory.
			System.setOut(inmemory); // TODO: use in game loggin or another tool so that we don't have to touch sys.out

			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");

			boolean notAWinner;
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

		return output.toString();
	}

	private String testSeed(int seed) {
		String expectedOutput = extractOutput(new Random(seed), new Game());
		String actualOutput = extractOutput(new Random(seed), new GameBetter());

		assertEquals(
				String.format("Change detected for seed %d, excepted = %s, got = %s", seed, expectedOutput, actualOutput),
				expectedOutput,
				actualOutput
		);
		return expectedOutput;
	}

	@Test
	@Ignore("enable back and set a particular seed to see the output")
	public void testNewImplementationMatchesOld() {
		String output = testSeed(1);
		System.out.println(output);
	}

	@Test
	public void testNewImplementationMatchesOldUsingManySeeds() {
		int totalRandomGames = 10_000;
		for (int seed = 1; seed < totalRandomGames; seed++) {
			testSeed(seed);
		}
	}


}
