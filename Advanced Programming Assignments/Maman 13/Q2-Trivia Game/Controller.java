import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javax.swing.*;
import java.util.Collections;

public class Controller {

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private RadioButton rb3;

    @FXML
    private RadioButton rb4;

    @FXML
    private Label questionLabel;

    @FXML
    private ToggleGroup tg1;

    @FXML
    private Label scoreLabel;

    private GameLogic gl;

    @FXML
    void newGame(ActionEvent event) {
        gl.newGame();
        setLevel();
    }

    @FXML
    void submit(ActionEvent event) {
        try {
            checkAnswer();
            gl.getPool().remove(0);
            setLevel();
        } catch (IndexOutOfBoundsException i){
            int choice = JOptionPane.showOptionDialog(null, "Your'e Done !! \n" + "Your Score was " + gl.getScore() +
                            "\nDo you want to play again?",
                    "Winner", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {     // New Game
                gl.newGame();
                setLevel();
            } else {
                System.exit(0);
            }
        }
    }

    public void initialize() {
        gl = new GameLogic();
        setLevel();
    }

    private void setLevel() {
        try {
            scoreLabel.setText("Score: " + gl.getScore());
            Collections.shuffle(gl.getPool());
            Question temp = gl.getPool().get(0);
            questionLabel.setText(temp.getUsrQuestion());
            Collections.shuffle(temp.getOptions());
            rb1.setText(temp.getOptions().get(0));
            rb2.setText(temp.getOptions().get(1));
            rb3.setText(temp.getOptions().get(2));
            rb4.setText(temp.getOptions().get(3));
        } catch (NullPointerException n) {
            int choice = JOptionPane.showOptionDialog(null, "Your'e Done !! \n" + "Your Score was " + gl.getScore() +
                            "\nDo you want to play again?",
                    "Winner", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {     // New Game
                gl.newGame();
                setLevel();
            } else {
                System.exit(0);
            }
        }
    }

    public void checkAnswer() {
        try {
            RadioButton rb = (RadioButton) tg1.getSelectedToggle();
            if (rb.getText().equals(gl.getPool().get(0).getRightAnswer())) { // might be null
                gl.setScore(gl.getScore() + 10);
            } else {
                gl.setScore(gl.getScore() - 5);
            }
        } catch (NullPointerException n) {
            JOptionPane.showMessageDialog(null, "Please Select An Answer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
