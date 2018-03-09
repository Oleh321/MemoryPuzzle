package memorypuzzle.code;

import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Первым делом вызывается окно настройки игры Settings
        Parent root = FXMLLoader.load(getClass().getResource("view/settings.fxml"));
        primaryStage.setTitle("Меню");
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
