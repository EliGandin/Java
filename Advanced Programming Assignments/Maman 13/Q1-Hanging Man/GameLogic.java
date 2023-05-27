import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    private String target;
    private int lives;
    private ArrayList<String> wordPool;


    public GameLogic() {
        this.wordPool = new ArrayList<String>();
        setWordPool();
        this.target = getRandomWord();
        this.lives = 6;     // User has 6 tries to
    }

    private String getRandomWord() {
        Random rand = new Random();
        return wordPool.get(rand.nextInt(wordPool.size() - 1));
    }

    private void setWordPool() {
        try {
            Scanner input = new Scanner(new File("words.txt"));
            while (input.hasNext()) {
                wordPool.add(input.next());
            }
            input.close();
        } catch (IOException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public String getTarget() {
        return target;
    }

    public int getLives() {
        return lives;
    }

    public void newGame() {
        target = getRandomWord();
        lives = 6;
    }

    public int checkInput(String input){
        if ((input.length() != 1) || !(Character.getNumericValue(input.charAt(0)) > 9)) {       // Invalid Input
            return -1;
        } else if (!target.contains(input.toUpperCase())) {     // Incorrect Input
            lives--;
            if (lives == 0) {   // Game Over
                return 1;
            }
            return 2;
        } else {
            return 0;
        }
    }
}
