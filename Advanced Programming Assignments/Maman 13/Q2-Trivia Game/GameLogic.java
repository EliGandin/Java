import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
    private ArrayList<Question> pool;
    private int score;

    public GameLogic() {
        this.pool = new ArrayList<Question>();
        this.score = 0;
        setPool();
    }

    private void setPool() {
        try {
            int fullQuestion = 0;
            String question = "", rightAnswer = "", wrong1 = "", wrong2 = "", wrong3 = "";
            Scanner input = new Scanner(new File("Trivia.txt"));
            while (input.hasNextLine()) {
                switch (fullQuestion) {
                    case 0:
                        question = input.nextLine();
                        fullQuestion++;
                        break;
                    case 1:
                        rightAnswer = input.nextLine();
                        fullQuestion++;
                        break;
                    case 2:
                        wrong1 = input.nextLine();
                        fullQuestion++;
                        break;
                    case 3:
                        wrong2 = input.nextLine();
                        fullQuestion++;
                        break;
                    case 4:
                        wrong3 = input.nextLine();
                        Question q = new Question(question, rightAnswer, wrong1, wrong2, wrong3);
                        pool.add(q);
                        fullQuestion = 0;
                        break;
                    default:
                        break;
                }
            }
            input.close();
        } catch (IOException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public ArrayList<Question> getPool() {
        return pool;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void newGame() {
        setPool();
        score = 0;
    }
}
