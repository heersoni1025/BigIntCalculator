import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BigIntCalculatorUI extends Application {

    private StringBuilder currentInput = new StringBuilder();
    private BigInt firstOperand = null;
    private String operator = null;
    private TextField display;

    @Override
    public void start(Stage primaryStage) {
        display = new TextField();
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(60);
        display.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-family: 'Comic Sans MS';" +
                        "-fx-background-color: #ffe0f0;" +
                        "-fx-text-fill: #d63384;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: #f7a8d0;" +
                        "-fx-border-width: 2;"
        );

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(12);
        grid.setVgap(12);

        String[][] buttonLabels = {
                {"7", "8", "9", "＋"},
                {"4", "5", "6", "－"},
                {"1", "2", "3", "×"},
                {"0", "C", "=", "÷"}
        };

        for (int row = 0; row < buttonLabels.length; row++) {
            for (int col = 0; col < buttonLabels[row].length; col++) {
                String label = buttonLabels[row][col];
                if (label.isEmpty()) continue;

                Button btn = new Button(label);
                btn.setPrefSize(70, 70);
                btn.setStyle(
                        "-fx-font-size: 22;" +
                                "-fx-font-family: 'Comic Sans MS';" +
                                "-fx-background-color: #ffb6c1;" +
                                "-fx-text-fill: #8b008b;" +
                                "-fx-background-radius: 35;" +
                                "-fx-border-radius: 35;" +
                                "-fx-border-color: #f7a8d0;" +
                                "-fx-border-width: 2;"
                );
                btn.setEffect(new DropShadow(5, Color.PINK));

                btn.setOnAction(e -> {
                    animateButtonClick(btn);
                    handleButton(label);
                });

                btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                    btn.setStyle(btn.getStyle() + "; -fx-effect: dropshadow(three-pass-box, #ff69b4, 10, 0, 0, 0);");
                });
                btn.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    btn.setStyle(btn.getStyle().replaceAll("; -fx-effect:.*", ""));
                });

                grid.add(btn, col, row);
            }
        }

        VBox root = new VBox(15, display, grid);
        root.setPadding(new Insets(20));
        root.setStyle(
                "-fx-background-image: url('background.jpeg');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-color: #fff0f5;" +
                        "-fx-padding: 20;"
        );

        Scene scene = new Scene(root, 330, 420);
        primaryStage.setScene(scene);
        primaryStage.setTitle("\uD83C\uDF38 BigInt Calculator by Heer \uD83C\uDF38");
        primaryStage.show();
    }

    private void handleButton(String label) {
        switch (label) {
            case "C":
                currentInput.setLength(0);
                display.clear();
                firstOperand = null;
                operator = null;
                break;

            case "＋": case "×": case "÷":
                if (currentInput.length() == 0) return;
                firstOperand = new BigInt(currentInput.toString());
                operator = label;
                currentInput.append(" ").append(operator).append(" ");
                display.setText(currentInput.toString());
                break;

            case "－":
                if (currentInput.length() == 0 || currentInput.toString().matches(".*[＋×÷]\s*$")) {
                    currentInput.append("-");
                } else if (firstOperand == null) {
                    firstOperand = new BigInt(currentInput.toString());
                    operator = "－";
                    currentInput.append(" － ");
                }
                display.setText(currentInput.toString());
                break;

            case "=":
                try {
                    String[] parts = currentInput.toString().split(" ");
                    if (parts.length < 3) return;

                    BigInt left = new BigInt(parts[0]);
                    String op = parts[1];
                    StringBuilder rightStr = new StringBuilder();
                    for (int i = 2; i < parts.length; i++) {
                        rightStr.append(parts[i]);
                    }
                    BigInt right = new BigInt(rightStr.toString());
                    BigInt result;

                    switch (op) {
                        case "＋": result = left.add(right); break;
                        case "－": result = left.sub(right); break;
                        case "×": result = left.mul(right); break;
                        case "÷":
                            if (right.toString().equals("0")) {
                                display.setText("\uD83D\uDEAB Divide by Zero!");
                                return;
                            }
                            result = left.div(right); break;
                        default: result = new BigInt("0");
                    }

                    display.setText(result.toString());
                    animateResultFade();
                    currentInput.setLength(0);
                    currentInput.append(result.toString());
                    firstOperand = null;
                    operator = null;

                } catch (Exception ex) {
                    display.setText("\u274C Error: " + ex.getMessage());
                }
                break;

            default:
                if (label.equals("－")) {
                    if (currentInput.length() == 0 || currentInput.toString().matches(".*[＋×÷]\s*$")) {
                        currentInput.append("-");
                        display.setText(currentInput.toString());
                        return;
                    }
                }
                currentInput.append(label);
                display.setText(currentInput.toString());
        }
    }

    private void animateButtonClick(Button btn) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), btn);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void animateResultFade() {
        FadeTransition fade = new FadeTransition(Duration.millis(300), display);
        fade.setFromValue(0.2);
        fade.setToValue(1.0);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
