package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Fabian-Bob Ioan George
 * This is the main function where I intitialize the first panel
 */

public class Main extends Application {

    /**
     * @param primaryStage actual stage to be loaded
     * @throws Exception if something goes wrong
     * Here I set my primaryStage to be Undecorated so the user can't resize it
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/presentation/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
