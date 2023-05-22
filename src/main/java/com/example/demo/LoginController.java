package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController{
    DBConnection connector = new DBConnection();
    Main main = new Main();
    @FXML
    private TextField IDGroupTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Label loginWarningLabel;
    @FXML
    private Button signInButton;
    @FXML
    private Button registerButton;

    public void initialize(){
        connector.getConnection();
    }
    public void signInButtonOnAction(ActionEvent event){
        if(IDGroupTextField.getText().isEmpty() || enterPasswordField.getText().isEmpty()){
            loginWarningLabel.setText("Invalid login. Please try again!");
        }
        else{
            String groupID = IDGroupTextField.getText();
            String password = enterPasswordField.getText();

            try{
                if(validateLogin(groupID, password)){
                    loginWarningLabel.setText("Loading...");
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
            }
        }
    }
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean validateLogin(String groupID, String password) throws SQLException {
        try (Connection connection = connector.dataSource.getConnection();
             PreparedStatement checkData = connection.prepareStatement("SELECT * FROM users WHERE Group_ID = ?")) {
            checkData.setString(1, groupID);
            ResultSet accountFound = checkData.executeQuery();

            if (!accountFound.isBeforeFirst()) {
                loginWarningLabel.setText("Wrong GroupID or Password");
                return false;
            } else {
                accountFound.next();
                if (!accountFound.getString("password").equals(password)) {
                    loginWarningLabel.setText("Wrong GroupID or Password");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}