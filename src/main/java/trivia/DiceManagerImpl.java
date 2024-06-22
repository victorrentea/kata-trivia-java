package trivia;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.Scanner;

public class DiceManagerImpl implements DiceManager {
    private static final Scanner scanner = new Scanner(System.in);
    private Random random = new Random(1);
    private final boolean isCallerTest;

    public DiceManagerImpl() {
        this.isCallerTest = isCallerTest();
    }


    public int readRoll() {
        System.out.print(">> Throw a dice and input roll, or [ENTER] to generate a random roll: ");
        String rollStr = "";
        if (!isCallerTest) {
            rollStr = scanner.nextLine().trim();
        }
        if (rollStr.isEmpty()) {
            int roll = random.nextInt(6) + 1;
            System.out.println(">> Random roll: " + roll);
            return roll;
        }
        if (!rollStr.matches("\\d+")) {
            System.err.println("Not a number: '" + rollStr + "'");
            return readRoll();
        }
        int roll = Integer.parseInt(rollStr);
        if (roll < 1 || roll > 6) {
            System.err.println("Invalid roll");
            return readRoll();
        }
        return roll;
    }

    public static boolean isCallerTest() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            if (element.getMethodName().contains("test")) {
                return true;
            }
        }
        return false;
    }
}
