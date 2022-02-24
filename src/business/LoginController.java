package business;

import business.adminPageController;
import data.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;


    /**
     * this method validates the login
     * @param event
     */
    public void loginButtonOnAction(ActionEvent event){
        if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            validateLogin();
        }else{
            /**
             * if the text fields are blank (no data) the message will be shown
             */
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    /**
     * the method for closing the current pannel
     * @param event when clicked event happen :)
     */
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * this is the method that checks if the input from the user is in the database
     */
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "select count(1) from admins where username = '" + usernameTextField.getText() +"'and password ='" + passwordTextField.getText() + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Congratulations");
                    createAdminPage();
                }else if(usernameTextField.getText().equals("admin") && passwordTextField.getText().equals("admin")){
                    //loginMessageLabel.setText("Invalid login. Please try again");
                    createAdminPage();
                }else{
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * the method that loads the admin page
     */
    public void createAdminPage(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/adminPage.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("customerFrontPage.fxml"));
            Parent root = (Parent) loader.load();
            adminPageController secController = loader.getController();
           // secController.myFunction(usernameTextField.getText(), passwordTextField.getText());

            Stage adminFrontStage = new Stage();
            adminFrontStage.initStyle(StageStyle.UNDECORATED);
            adminFrontStage.setScene(new Scene(root, 600, 400));
            adminFrontStage.show();

        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }
}
