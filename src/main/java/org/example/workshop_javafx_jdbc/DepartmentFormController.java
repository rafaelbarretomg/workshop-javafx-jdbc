package org.example.workshop_javafx_jdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.workshop_javafx_jdbc.gui.util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML Button btCancel;

    @FXML
    public void onBtSaveAction(){
        System.out.println("Save");
    }

    @FXML
    private void onBTCancelActioN(){
        System.out.println("Cancel");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
}
