package project;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Controller {
    private HashMap<State, String> stateMap;
    private HashMap<Pass, State> passMap;
    private State selectedState;
    private Pass selectedPass;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button help;

    /**
     * Initializes the fields, resources for the Scene and methods
     */
    public void initialize() {
        stateMap = new HashMap<>();
        passMap = new HashMap<>();
        ImageView imageView = new ImageView(new Image("Icon.png"));

        imageView.setFitWidth(26);
        imageView.setFitHeight(26);
        help.setGraphic(imageView);
        anchorPane.setFocusTraversable(true);
        anchorPane.requestFocus();

        setDragOption();
    }

    /**
     * Sets the dragging option to enable the dragging of States, Arrows and SelfArrows
     */
    private void setDragOption() {
        anchorPane.setOnMouseDragged(e -> {
            if (selectedState != null) {
                selectedState.dragState(e.getX(), e.getY());
            }
        });
    }


    /**
     * Handles the mouse click
     * Checks which mouse key was clicked, how many times it was clicked and where was it clicked
     *
     * @param event represents which mouse key was clicked, how many times it was clicked and where was it clicked
     */
    @FXML
    void handleClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 1) {
                if (event.isShiftDown()) {
                    handlePass(event);
                }
                highlightState(event);
                highlightPass(event);
            } else {
                for (Map.Entry<State, String> entry : stateMap.entrySet()) {
                    if (entry.getKey().contains(new Point2D(event.getX(), event.getY()))) {
                        entry.getKey().toggleAcceptableState();
                        return;
                    }
                }
                addState(event);
            }
        } else {
            renamePass(event);
            renameState(event);
        }
    }


    /**
     * Selects a Pass based on the provided MouseEvent coordinates.
     *
     * @param event The MouseEvent containing the coordinates of the mouse click.
     */
    private void selectPass(MouseEvent event) {
        for (Map.Entry<Pass, State> entry : passMap.entrySet()) {
            try {
                if (entry.getKey().contains(new Point2D(event.getX(), event.getY()))) {
                    if (entry.getKey() == selectedPass) {
                        deselectPass();
                        return;
                    }
                    deselectPass();
                    selectedPass = entry.getKey();
                    return;
                }
            } catch (NullPointerException ignored) {
            }
            deselectPass();
        }
    }


    /**
     * Deselects the selected Pass
     */
    private void deselectPass() {
        for (Map.Entry<Pass, State> entry : passMap.entrySet()) {
            if (entry.getKey() instanceof Arrow) {
                ((Arrow) entry.getKey()).getLine().setStrokeWidth(1);
                ((Arrow) entry.getKey()).getLine().setStroke(Color.BLACK);
            } else {
                ((SelfArrow) entry.getKey()).getArc().setStrokeWidth(1);
                ((SelfArrow) entry.getKey()).getArc().setStroke(Color.BLACK);
            }
            entry.getKey().getArrowHead().setStroke(Color.BLACK);
            entry.getKey().getArrowHead().setFill(Color.BLACK);
        }
        selectedPass = null;
    }


    /**
     * Renames the selected Pass
     *
     * @param event The MouseEvent that contains the coordinates of the mouseclick
     */
    private void renamePass(MouseEvent event) {
        selectPass(event);
        if (selectedPass != null) {
            Optional<String> res = setPassName();
            if (selectedPass != null && validName(res) && res.isPresent()) {
                selectedPass.getLabel().setText(res.get());
                selectedPass = null;
            }
        }
    }


    /**
     * Renames the selected State
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void renameState(MouseEvent event) {
        selectState(event);
        if (selectedState != null) {
            Optional<String> res = setStateNamePopup();
            if (selectedState != null && validName(res) && res.isPresent()) {
                selectedState.getStateLabel().setText(res.get());
                stateMap.put(selectedState, selectedState.getStateName());
                selectedState = null;
            }
        }
    }


    /**
     * Selects a State based on the provided MouseEvent coordinates.
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void selectState(MouseEvent event) {
        for (Map.Entry<State, String> entry : stateMap.entrySet()) {
            try {
                if (entry.getKey().contains(new Point2D(event.getX(), event.getY()))) {
                    if (entry.getKey() == selectedState) {
                        deselectState();
                        return;
                    }
                    deselectState();
                    selectedState = entry.getKey();
                    return;
                }
            } catch (NullPointerException ignored) {
            }
        }
        deselectState();
    }


    /**
     * Highlights the selected State
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void highlightState(MouseEvent event) {
        selectState(event);
        try {
            selectedState.getCircle().setStrokeWidth(3);
            selectedState.getCircle().setStroke(Color.AQUA);
            if (selectedState.getAcceptingCircle() != null)
                selectedState.getAcceptingCircle().setStroke(Color.AQUA);
        } catch (NullPointerException e) {
            if (selectedPass == null)
                deselectState();
        }
    }


    /**
     * Highlights the selected Pass
     * 
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void highlightPass(MouseEvent event) {
        selectPass(event);
        try {
            if (selectedPass instanceof Arrow) {
                ((Arrow) selectedPass).getLine().setStrokeWidth(3);
                ((Arrow) selectedPass).getLine().setStroke(Color.AQUA);
            } else {
                ((SelfArrow) selectedPass).getArc().setStrokeWidth(3);
                ((SelfArrow) selectedPass).getArc().setStroke(Color.AQUA);
            }
            selectedPass.getArrowHead().setStroke(Color.AQUA);
            selectedPass.getArrowHead().setFill(Color.AQUA);
        } catch (NullPointerException e) {
            deselectPass();
        }
    }


    /**
     * Deselects the selected State
     */
    private void deselectState() {
        for (Map.Entry<State, String> entry : stateMap.entrySet()) {
            entry.getKey().getCircle().setStrokeWidth(1);
            entry.getKey().getCircle().setStroke(Color.BLACK);
            if (entry.getKey().getAcceptingCircle() != null)
                entry.getKey().getAcceptingCircle().setStroke(Color.BLACK);
        }
        selectedState = null;
    }


    /**
     * Adds a State to the Scene
     * 
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void addState(MouseEvent event) {
        if (!stateMap.isEmpty()) {
            Circle circle = new Circle(event.getX(), event.getY(), 30);
            for (Map.Entry<State, String> entry : stateMap.entrySet()) {
                if (entry.getKey().getCircle().getBoundsInParent().intersects(circle.getBoundsInParent())) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText(null);
                    a.setContentText("Invalid State Placement");
                    a.showAndWait();

                    return;
                }
            }
        }
        
        Optional<String> res = setStateNamePopup();
        if (validName(res) && res.isPresent()) {
            State s = new State(res.get(), event.getX(), event.getY());
            anchorPane.getChildren().addAll(s.getCircle(), s.getAcceptingCircle(), s.getStateLabel());
            stateMap.put(s, s.getStateName());
        }
    }


    /**
     * Validates the name of the State or Pass
     *
     * @param res is the name that is checked
     * @return {@code true} if the name is valid
     */
    private boolean validName(Optional<String> res) {
        if (res.isPresent()) {
            String name = res.get();
            if (name.length() > 3) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Invalid Input\nState Name Too Long");
                a.showAndWait();
                return false;
            }

            if (name.isEmpty() || !name.matches("[a-zA-Z0-9_]+")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Invalid Input\nPlease Provide a name");
                a.showAndWait();
                return false;
            }
        }

        return true;
    }


    /**
     * Sets the name of the State through a pop-up window for validation
     *
     * @return the name for validation
     */
    private Optional<String> setStateNamePopup() {
        Optional<String> res;
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("State Name");
        textInput.setHeaderText(null);
        textInput.setContentText("Please Enter a Name");
        res = textInput.showAndWait();

        return res;
    }


    /**
     * Sets the name of the Pass through a pop-up window
     * @return
     */
    private Optional<String> setPassName() {
        Optional<String> res;
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Pass Name");
        textInput.setHeaderText(null);
        textInput.setContentText("Please Name the Pass");
        res = textInput.showAndWait();

        return res;
    }


    /**
     * Initializes a Pass
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    private void handlePass(MouseEvent event) {
        if (selectedState != null) {
            State temp;
            for (Map.Entry<State, String> entry : stateMap.entrySet()) {
                try {
                    if (entry.getKey().contains(new Point2D(event.getX(), event.getY()))) {
                        temp = entry.getKey();
                        Optional<String> res = setPassName();

                        if (validName(res) && res.isPresent()) {
                            if (entry.getKey() == selectedState) {
                                SelfArrow selfArrow = new SelfArrow(res.get(), selectedState);
                                selectedState.getArrowsArr().add(selfArrow);
                                anchorPane.getChildren().addAll(selfArrow.getLabel(), selfArrow.getArrowHead(), selfArrow.getArc());
                                passMap.put(selfArrow, selectedState);
                            } else {
                                Arrow arrow = new Arrow(res.get(), selectedState, temp);
                                selectedState.getArrowsArr().add(arrow);
                                temp.getArrowsArr().add(arrow);
                                anchorPane.getChildren().addAll(arrow.getLabel(), arrow.getArrowHead(), arrow.getLine());
                                passMap.put(arrow, selectedState);
                            }
                        }
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }
    }


    /**
     * Initializes the Help Button
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    @FXML
    void help(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Information");
        a.setHeaderText(null);
        a.setContentText("""
                1. Double-click to create a new state
                2. Press once to select a state/ pass
                3. Backspace to delete a selected state
                4. To draw an arrow/pass, select the starting state, hold SHIFT and select the end state
                5. Double-click a state to make it as an Accepting State
                6. Right-click to rename a state/ pass
                """);
        a.showAndWait();
    }


    /**
     * Initializes the Save Button and allows the saving of the Scene to a picture file
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    @FXML
    void saveMachine(ActionEvent event) {
        deselectState();

        try {
            SnapshotParameters sp = new SnapshotParameters();
            sp.setDepthBuffer(true);

            WritableImage image = anchorPane.snapshot(sp, null);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg", "*.jpg");

            FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().addAll(pngFilter, jpgFilter, jpegFilter);

            fileChooser.setSelectedExtensionFilter(pngFilter);
            fileChooser.setInitialDirectory(new File("."));

            File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());

            if (file != null) {
                ImageIO.write(bufferedImage, "png", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Clears the Scene
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    @FXML
    void clear(ActionEvent event) {
        anchorPane.getChildren().clear();
        stateMap.clear();
        passMap.clear();
        selectedState = null;
        selectedPass = null;
    }


    /**
     * Deletes a State and associating Passes
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    @FXML
    void deleteState(KeyEvent event) {
        if (event.getCode() == KeyCode.BACK_SPACE) {
            if (selectedState != null) {
                anchorPane.getChildren().removeAll(selectedState.getStateLabel(), selectedState.getCircle());
                if (selectedState.getAcceptingCircle() != null)
                    anchorPane.getChildren().remove(selectedState.getAcceptingCircle());
                for (Pass pass : selectedState.getArrowsArr()) {
                    if (pass instanceof Arrow) {
                        anchorPane.getChildren().removeAll(((Arrow) pass).getLine(), pass.getArrowHead(), pass.getLabel());
                    } else {
                        anchorPane.getChildren().removeAll(((SelfArrow) pass).getArc(), pass.getArrowHead(), pass.getLabel());
                    }
                }
                stateMap.remove(selectedState);
            }

            if (selectedPass != null) {
                if (selectedPass instanceof Arrow) {
                    anchorPane.getChildren().removeAll(((Arrow) selectedPass).getLine());
                } else {
                    anchorPane.getChildren().removeAll(((SelfArrow) selectedPass).getArc());
                }
                anchorPane.getChildren().removeAll(selectedPass.getArrowHead(), selectedPass.getLabel());

                passMap.remove(selectedPass);
            }

            passMap.entrySet().removeIf(entry -> entry.getValue() == selectedState);
            selectedState = null;
            selectedPass = null;
        }
    }


    /**
     * Initializes the dragging functionality across the Scene
     *
     * @param event The MouseEvent containing the coordinates of the click.
     */
    @FXML
    void drag(MouseEvent event) {
        selectState(event);
    }
}
