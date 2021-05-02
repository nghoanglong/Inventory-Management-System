package Models.demo_QLSP;

import Models.QLSP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class demoimg implements Initializable {
    @FXML
    private ImageView imageView;
    private Image image;
    private FileInputStream fis;

    @FXML
    private Button addBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnBrowser;
    private FileChooser fileChooser;
    private File file;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Only", "*png", "*jpg")
        );
    }

    public void handleBrowserBtn(ActionEvent event){
        stage = (Stage) anchorPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);

        if(file != null){
            image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
        }
    }

    public void AddBtnAction(ActionEvent event) throws FileNotFoundException {
        fis = new FileInputStream(file);
        QLSP new_qlsp = new QLSP();
        new_qlsp.insert_qlsp("Dell", "Dell Vostro M20", 20, fis, file.length());
    }

}
