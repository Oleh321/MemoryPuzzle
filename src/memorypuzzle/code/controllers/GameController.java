package memorypuzzle.code.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import memorypuzzle.code.models.Level;
import memorypuzzle.code.Rules;

import java.io.IOException;

public class GameController  {
    private Level currentLevel;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label couplesLabel;

    @FXML
    private Pane pane;

    @FXML
    private Canvas canvas;

    @FXML
    void onCloseGame(ActionEvent event) {
        // Возвращение на форму настройки игры
       openMenu();
    }

    public void init(Level currentLevel) {
        // Инициализация класса Rules, который отвечает за правила работы и логику игрового поля
        Rules rules = new Rules(currentLevel, canvas.getGraphicsContext2D());
        // Растягивание поля для рисованя по всему компоненту Pane
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().addListener(e->rules.paint());
        canvas.heightProperty().addListener(e->rules.paint());

        // Нажатие на поле
        pane.setOnMouseClicked(e ->{
            rules.clickedField(
                    (int) (e.getY()/currentLevel.getSize()),
                    (int) (e.getX()/currentLevel.getSize()));

            stepsLabel.setText(String.valueOf(rules.getSteps()));
            couplesLabel.setText(String.valueOf(rules.getCouples()));
            if(rules.isWin()){
                rules.finishGame();
            }
        });
    }

    private void openMenu(){
        try {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
            stage.setTitle("Меню");
            stage.setScene(new Scene(root, 400, 250));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
