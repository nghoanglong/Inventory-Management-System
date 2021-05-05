// Package
package Controllers;

// Import JavaFX libs
import Models.USERS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


// Import java libs


// Import java modules


public class LoginController
{
    // Define virables to store and get input data
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label noticeLabel;

    @FXML
    public void initialize()
    {
        noticeLabel.setVisible(false);
    }


    public static int type_cur_user;

    // Set action for Login Button
    public void loginButtonAction(ActionEvent event) throws IOException {
        // Define userName, passWord to get data from user input
        String userName = usernameTextField.getText();
        String passWord = enterPasswordField.getText();

        // Define object để check validate login
        // result = 1 -> login thành công
        //        = 2 -> username ko tồn tại
        //        = 3 -> username đúng, password sai
        USERS user_con = new USERS();
        int check_result = user_con.validate_login(userName, passWord);

        switch (check_result) {
            case 1:
                noticeLabel.setText("Login success");
                noticeLabel.setVisible(true);
                System.out.println(noticeLabel);

                LoginController.type_cur_user = user_con.getTypeUser(userName);
                Parent HelloPage_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HelloPage/hellopage.fxml"));
                Stage HelloPage_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene HelloPage_Scene = new Scene(HelloPage_Parent);

                HelloPage_Stage.setScene(HelloPage_Scene);
                HelloPage_Stage.show();
                break;
            case 2:
                noticeLabel.setText("User not exist");
                noticeLabel.setVisible(true);
                break;
            case 3:
                noticeLabel.setText("Wrong password");
                noticeLabel.setVisible(true);
                break;
        }
    }
}
