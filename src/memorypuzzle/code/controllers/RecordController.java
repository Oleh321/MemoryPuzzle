package memorypuzzle.code.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import memorypuzzle.code.dbmanagers.ScoreManager;
import memorypuzzle.code.models.Level;
import memorypuzzle.code.models.Score;

import java.io.IOException;

public class RecordController {

    @FXML
    private TableView<Score> table;

    @FXML
    private TableColumn<Score, String> nameColumn;

    @FXML
    private TableColumn<Score, Level> sizeColumn;

    @FXML
    private TableColumn<Score, Integer> varietyColumn;

    @FXML
    private TableColumn<Score, Integer> stepsColumn;

    private ScoreManager manager;

    @FXML
    public void initialize(){
        // Считывние из базы данных таблицы рекордов и отображение в таблице на форме
        manager = new ScoreManager();
        nameColumn.setCellValueFactory(new PropertyValueFactory("recordsman"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory("level"));
        varietyColumn.setCellValueFactory(new PropertyValueFactory("variety"));
        stepsColumn.setCellValueFactory(new PropertyValueFactory("steps"));

        table.setItems(FXCollections.observableList(manager.loadScores()));
    }

    @FXML
    void OnCloseAction(ActionEvent event) {
        // Возвращение на форму настройки игры
        try {
            Stage stage = (Stage) table.getScene().getWindow();
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
