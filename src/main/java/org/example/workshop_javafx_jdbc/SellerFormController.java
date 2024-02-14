package org.example.workshop_javafx_jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.workshop_javafx_jdbc.db.DbException;
import org.example.workshop_javafx_jdbc.gui.listener.DataChangeListener;
import org.example.workshop_javafx_jdbc.gui.util.Alerts;
import org.example.workshop_javafx_jdbc.gui.util.Constraints;
import org.example.workshop_javafx_jdbc.gui.util.Utils;
import org.example.workshop_javafx_jdbc.model.entities.Department;
import org.example.workshop_javafx_jdbc.model.entities.Seller;
import org.example.workshop_javafx_jdbc.model.exceptions.ValidationException;
import org.example.workshop_javafx_jdbc.model.services.DepartmentService;
import org.example.workshop_javafx_jdbc.model.services.SellerService;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();


    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    Button btCancel;

    private ObservableList<Department> obsList;

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setServices(SellerService service, DepartmentService departmentService) {

        this.service = service;
        this.departmentService = departmentService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation error!");

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can`t be empty");
        }
        obj.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addError("email", "Field can`t be empty");
        }
        obj.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null) {
            exception.addError("birthDate", "Field cant`t be empty");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }


        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
            exception.addError("baseSalary", "Field can`t be empty");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());


        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }

    @FXML
    private void onBTCancelActioN(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if (entity.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }

    }

    public void loadAssociatedObjects() {
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));

        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));

        labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));

        labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));


    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
