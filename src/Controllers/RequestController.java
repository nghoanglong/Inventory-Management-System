package Controllers;

import Models.USERS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RequestController
{
    @FXML
    private TextField tenkhTF;
    @FXML
    private TextField sdtTF;
    @FXML
    private TextField diachiTF;
    @FXML
    private ComboBox requestSelectionCB;
    @FXML
    private Label noticeLabel;

    int request_num = 0;
    String request_selected = null;

    @FXML
    void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList("Đơn Nhập","Đơn Xuất");
        requestSelectionCB.setItems(options);
        noticeLabel.setVisible(false);
    }

    public void requestSelectionCBAction(ActionEvent e)
    {
        requestSelectionCB.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        request_selected = requestSelectionCB.getValue().toString();
        switch (request_selected)
        {
            case "Đơn Nhập":
                request_num = 1;
                break;
            case "Đơn Xuất":
                request_num = 2;
                break;
            default:
                break;
        }
    }

    public void saveBTNAction(ActionEvent e) throws IOException
    {
        String name = tenkhTF.getText();
        String phone_number = sdtTF.getText();
        String address = diachiTF.getText();

        if(name.isEmpty())
        {
            noticeLabel.setText("Name should not be empty");
            noticeLabel.setVisible(true);
        }
        else if(phone_number.isEmpty())
        {
            noticeLabel.setText("Phone number should not be empty");
            noticeLabel.setVisible(true);
        }
        else if(address.isEmpty())
        {
            noticeLabel.setText("Address should not be empty");
            noticeLabel.setVisible(true);
        }
        else
        {
            noticeLabel.setText("Your request was created successfully");
            noticeLabel.setVisible(true);
        }
    }
}
