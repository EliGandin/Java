import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.util.ArrayList;

public class HangmanController {

    @FXML
    private Canvas canv;

    @FXML
    private Label targetLabel;

    @FXML
    private Label wordLength;

    @FXML
    private Label attemptedCharacters;

    @FXML
    private Label remainingAttempts;

    @FXML
    private TextField inputField;

    private GameLogic gl;
    private GraphicsContext gc;
    private ArrayList<String> targetLetters;
    private ArrayList<String> attemptedChars;

    public void initialize() {
        gl = new GameLogic();
        gc = canv.getGraphicsContext2D();
        this.attemptedChars = new ArrayList<String>();
        targetLetters = new ArrayList<String>();
        String len = String.valueOf(gl.getTarget().length());
        wordLength.setText("Your Word Has " + len + " Letters");
        remainingAttempts.setText("Remaining Attempts: " +gl.getLives());
        drawWordTiles();
        drawGallows();
        drawHangingMan();
    }

    @FXML
    void newGame(ActionEvent event) {
        newGame();
    }

    @FXML
    void getChar(ActionEvent event) {
        String input = inputField.getText();
        int actionCode = gl.checkInput(input);
        input = input.toUpperCase();
        drawHangingMan();
        if (actionCode == -1) {     // Invalid Input
            JOptionPane.showMessageDialog(null, "Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            inputField.clear();
        } else if (actionCode == 1) {   // Lost Game
            int choice = JOptionPane.showOptionDialog(null, "You Lost \n" + "The target was " + gl.getTarget() +
                            "\nDo you want to play again?",
                    "You Lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {     // New Game
               newGame();
            }
        } else if (actionCode == 2) { // Incorrect Guess
            JOptionPane.showMessageDialog(null, "Incorrect Guess", "Incorrect Guess", JOptionPane.INFORMATION_MESSAGE);
            handleInput(input);
            remainingAttempts.setText("Remaining Attempts: " + gl.getLives());
        } else {        // Correct Guess
            handleInput(input);
            for (int i = 0; i < gl.getTarget().length(); i++) {
                if (input.charAt(0) == gl.getTarget().charAt(i)) {
                    targetLetters.set(i,input);
                }
            }
            targetLetters.set(gl.getTarget().indexOf(input.charAt(0)),input);
            targetLabel.setText(targetLetters.toString().replace("[", "").replace("]", ""));
            if (!targetLetters.contains("_")) {
                int choice = JOptionPane.showOptionDialog(null, "You Won !! \n" + "The target was " + gl.getTarget() +
                                "\nDo you want to play again?",
                        "Winner", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);
                if (choice == JOptionPane.YES_OPTION) {     // New Game
                    newGame();
                } else {
                    System.exit(0);
                }
            }
        }
    }

    public void handleInput(String input) {
        attemptedChars.add(input);
        attemptedCharacters.setText("Attempted Characters:" + attemptedChars);
        inputField.clear();
    }

    public void drawWordTiles() {
        for (int i = 0; i < gl.getTarget().length(); i++) { // Setting up the Tiles
            targetLetters.add("_");
        }
        targetLabel.setText(targetLetters.toString().replace("[", "").replace("]", ""));
    }

    public void drawGallows() {
        gc.setLineWidth(6);
        gc.strokeLine(250, 50, 250, 250);
        gc.strokeLine(250, 50, 350, 50);
        gc.strokeLine(350, 50, 350, 80);
    }

    public void newGame() {
        gl.newGame();
        gc.clearRect(0,0, canv.getWidth(),canv.getHeight());
        targetLetters.clear();
        attemptedChars.clear();
        drawGallows();
        drawWordTiles();
        remainingAttempts.setText("Remaining Attempts: " + gl.getLives());
        attemptedCharacters.setText("Attempted Characters: ");
    }

    public void drawHangingMan() {
        gc.setLineWidth(6);
        int lives = gl.getLives() + 1;
        switch (lives) {
            case 1:
                gc.strokeLine(390, 245, 350, 210); // Right LEg
                break;
            case 2:
                gc.strokeLine(310, 245, 350, 210); // Left leg
                break;
            case 3:
                gc.strokeLine(390, 185, 350, 150); // Right Hand
              break;
            case 4:
                gc.strokeLine(310, 185, 350, 150); // Left Hand
                break;
            case 5:
                gc.strokeLine(350, 120, 350, 210); // Body
                break;
            case 6:
                gc.strokeOval(330, 80, 40, 40); // Head
                break;
            default:
            break;
        }
    }
}
