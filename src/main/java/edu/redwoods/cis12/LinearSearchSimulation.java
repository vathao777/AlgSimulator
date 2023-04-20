package edu.redwoods.cis12;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.util.concurrent.TimeUnit;

public class LinearSearchSimulation extends AlgorithmSimulation {
    public LinearSearchSimulation(AlgSimulatorController asc) {
        super("Linear Search", "linearSearchControls.fxml", asc);
    }

    private void algorithmStep(int searchFor, int start, int end, int step) {
        try {
            while (start <= end) {
                int current = start; // To make 'current' effectively final
                Platform.runLater(() -> {
                    Button b = (Button) gridPane.getChildren().get(current);
                    int v = Integer.parseInt(b.getText());
                    if (v == searchFor) {
                        b.setStyle("-fx-background-color: #FF0000");
                    } else {
                        b.setStyle("-fx-background-color: #666666");
                    }
                });
                TimeUnit.SECONDS.sleep(1);
                start += step;
            }
        } catch (NumberFormatException e) {
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Invalid Search");
                a.setContentText("Please enter an integer to search for in the \"Search For\" text-field.");
                a.show();
            });
        } catch (InterruptedException ie) {
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Interrupted");
                a.setContentText("Please enter an integer to search for in the \"Search For\" text-field.");
                a.show();
            });
        }
    }


    @Override
    public void simulate() {
        try {
            int searchFor = Integer.parseInt(((LinearSearchSimulationController)this.getSc()).getSearchForTextField().getText());
            new Thread(() -> {
                algorithmStep(searchFor, 0, gridPane.getChildren().size() - 1, 1); // start from index 0 to the end
            }).start();
            new Thread(() -> {
                algorithmStep(searchFor, gridPane.getChildren().size() - 1, 0, -1); // start from the end to index 0
            }).start();
        } catch(NumberFormatException|NullPointerException n) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Invalid Search");
            a.setContentText("Please enter an integer to search for in the \"Search For\" text-field.");
            a.show();
        }
    }

}