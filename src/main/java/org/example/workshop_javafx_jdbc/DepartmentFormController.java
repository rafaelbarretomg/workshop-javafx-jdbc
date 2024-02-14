package org.example.workshop_javafx_jdbc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.workshop_javafx_jdbc.db.DbException;
import org.example.workshop_javafx_jdbc.gui.util.Alerts;
import org.example.workshop_javafx_jdbc.gui.util.Constraints;
import org.example.workshop_javafx_jdbc.gui.util.Utils;
import org.example.workshop_javafx_jdbc.model.entities.Department;
import org.example.workshop_javafx_jdbc.model.services.DepartmentService;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML Button btCancel;

    public void setDepartment(Department entity){
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }

    @FXML
    public void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        if(service == null){
            throw new IllegalStateException("service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            Utils.currentStage(event).close();
        }catch (DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Department getFormData() {
        Department obj = new Department();

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setName(txtName.getText());
        return obj;
    }

    @FXML
    private void onBTCancelActioN(ActionEvent event){
        Utils.currentStage(event).close();    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
