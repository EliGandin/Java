import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    private String target;
    private static int attemptCounter;
    private ArrayList<String> inputLog;
    private int bulls;
    private int cows;

    /* Constructor */
    public GameLogic() {
        this.target = setTarget();
        this.inputLog = new ArrayList<String>();
        this.bulls = 0;
        this.cows = 0;
    }

    /* Setting the target string */
    public String setTarget() {
        String target = "";
        ArrayList<Integer> arr = new ArrayList<Integer>(); // Used to make sure that all the numbers are distinct
        Random rand = new Random();
        int i = 0;

        while (i < 4) {
            int r = rand.nextInt(10);
            if (arr.contains(r) == false) {
                arr.add(r);
                target += String.valueOf(r); // Setting the string
                i++;
            }
        }
        return target;
    }

    /* Getter for the target string */
    public String getTarget() {
        return target;
    }

    /* Getter for the attempt counter */
    public static int getAttemptCounter() {
        return attemptCounter;
    }

    /* Setter for the attempt counter used when resetting the game */
    public static void setAttemptCounter(int i) {
        attemptCounter = i;
    }

    /* Getter for the input log */
    public ArrayList<String> getInputLog() {
        return inputLog;
    }

    /* Getter for the number of bulls */
    public int getBulls() {
        return bulls;
    }

    /* Getter for the number of cows */
    public int getCows() {
        return cows;
    }

    /* Setter for the number of bull */
    public void setBulls(int i) {
        bulls = i;
    }

    /* Setter for the number of cows */
    public void setCows(int i) {
        cows = i;
    }

    /* Adds an attempt to the arraylist */
    public void addInputLog(String input) {
        inputLog.add(input);
    }

    /* Validates the input */
    public int isValidInput(String input) {
        if (input == null) {
            return 2;
        } else if (input.length() != 4) {
            return -1;
        } else {
            for (int i = 0; i < 4; i++) {
                if (Character.getNumericValue(input.charAt(i)) > 9) { // Checks if the input is numbers only
                    return -1;
                }
            }
        }
        return 0;
    }

    /* Resets the game */
    public void gameReset() {
        target = setTarget();
        resetStats();
        setAttemptCounter(0);
        inputLog.clear();
    }

    /* Checks the attempt and scores accordingly */
    public int checkGuess(String input) {
        int actionCode;
        if (isValidInput(input) == 2) {
            actionCode = 2;     // null input
        } else if (isValidInput(input) == -1) {
            actionCode = -1;    // Signals invalid input
        } else {
            for (int i = 0; i < input.length(); i++) {
                if (target.contains(String.valueOf(input.charAt(i)))) {
                    if (target.charAt(i) == input.charAt(i)) {
                        bulls++;
                        if (bulls == 4) {
                            return 0; // Signals that the user has won
                        }
                    } else {
                        cows++;
                    }
                }
            }
            attemptCounter++;
            addInputLog(input);
            actionCode = 1; // Signals that the user hasn't won yet
        }
        return actionCode;
    }

    /* Resets the score after each attempt */
    public void resetStats() {
        setBulls(0);
        setCows(0);
    }

    /* Result handling */
    public String outputPrint() {
        return "Number of bulls: " + getBulls() + '\n' +
                "Number of cows: " + getCows() + '\n' +
                "Number of attempts: " + getAttemptCounter() + '\n' +
                "Attempts: \n" + inputLog;
    }
}