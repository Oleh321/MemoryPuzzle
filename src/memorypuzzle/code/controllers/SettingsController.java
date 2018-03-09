package memorypuzzle.code.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import memorypuzzle.code.models.Level;
import memorypuzzle.code.dbmanagers.LevelManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private ComboBox<Level> sizeFieldComboBox;

    @FXML
    private Spinner<Integer> varietySpinner;

    private SpinnerValueFactory<Integer> valueFactory;

    private LevelManager levelManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Инициализируем форму настройки игры и загружаем уровни
        levelManager = new LevelManager();
        sizeFieldComboBox.setItems(FXCollections.observableArrayList(levelManager.loadLevels()));
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12, 4);
        varietySpinner.setValueFactory(valueFactory);
        sizeFieldComboBox.setValue(sizeFieldComboBox.getItems().get(0));
    }

    @FXML
    void changeComboBoxAction(ActionEvent event) {
        // При обновлении выпадающего списка, обновляется и рекомендованное разнообразие
        valueFactory.setValue(
                sizeFieldComboBox.getSelectionModel().getSelectedItem().getRecommendedVariety());
    }

    @FXML
    void onStratGame(ActionEvent event) {
        try {
            // Выбор размеров и разнообразия
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.hide();
            stage.setTitle("Игра");
            Level currentLevel = sizeFieldComboBox.getSelectionModel().getSelectedItem();
            // Проверка на допустимое количество разнообразия элементов для размера карты
            int variety = currentLevel.getRows()*currentLevel.getColumns()/2;
            if(variety > varietySpinner.getValue())
                variety = varietySpinner.getValue();
            currentLevel.setRecommendedVariety(variety);
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.init(currentLevel);
            // Загрузка карты с определенным высчитаным размером для текущего уровня
            stage.setScene(new Scene(root,
                    currentLevel.getColumns() * currentLevel.getSize() + 140,
                    currentLevel.getRows() * currentLevel.getSize() - 10));
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    @FXML
    void onRecordsOpen(ActionEvent event) {
        // Открытие формы рекордов
        try {
            Stage stage = (Stage) varietySpinner.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("../view/records.fxml"));
            stage.setTitle("Рекорды");
            stage.setScene(new Scene(root, 600, 510));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
