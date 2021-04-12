package app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginPage/loginpage.fxml"));
        primaryStage.setTitle("Login page");
        primaryStage.setScene(new Scene(root, 690, 540));


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
