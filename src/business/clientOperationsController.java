package business;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.clientsModelTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @author Fabian-Bob Ioan George
 */

public class clientOperationsController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private TableView<clientsModelTable> table;
    @FXML
    private TableColumn<clientsModelTable, String> col_id;
    @FXML
    private TableColumn<clientsModelTable, String> col_first_name;
    @FXML
    private TableColumn<clientsModelTable, String> col_last_name;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField deleteTextField;
    @FXML
    private Label deleteLabel;
    @FXML
    private TextField addFirstTextField;
    @FXML
    private TextField addLastTextField;
    @FXML
    private Button addClient;
    @FXML
    private Label addLabel;
    @FXML
    private Button editButton;
    @FXML
    private TextField editFirstTextField;
    @FXML
    private TextField editLastTextField;
    @FXML
    private TextField editIDTextField;
    @FXML
    private Label editLabel;

    /**
     * ObservableList holds all the strings taken from the database
     */
    ObservableList<clientsModelTable> oblist = FXCollections.observableArrayList();

    /**
     * this method edits the client credentials by updating them in the database
     * @param event when the button is pressed
     */
    public void editButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String gg = editFirstTextField.getText();

        /**
         * this string is actually the sql string that will be executed
         */
        String editClient = "update clients set first_name = '" + editFirstTextField.getText() + "', last_name = '" + editLastTextField.getText() + "' where id =" + editIDTextField.getText();

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(editClient);
            /**
             *  this label is shown to the user when everything went right
             */
            editLabel.setText("Client edited successfully");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    /**
     * this class add a new client to the db
     * @param event when the button is pressed the event will happen
     */
    public void addClientOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * this string is the sql query for inserting new clients into the db
         */
        String addClient = "insert into clients (first_name, last_name) values('" + addFirstTextField.getText() + "','" + addLastTextField.getText() + "')";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addClient);
            addLabel.setText("Client added successfully");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
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
     * the method for deleting a client
     * @param event that happens when button is pressed
     */
    public void deleteButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * the query for deleting a client from the db
         */
        String deleteClient = "delete from clients where id='" + deleteTextField.getText() + "'";
        try{
            /**
             * new statement in order to execute the query
             */
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteClient);
            /**
             * message label for informing the user that the procedure was done
             */
            deleteLabel.setText("Client was deleted successfully");
        }catch(Exception e){
            /**
             * in case of any errors appear
             */
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * this is the method when the panel appears the data is directly shown in the table
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * the mighty sql query
         */
        String getAllClients = "select * from clients";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getAllClients);
            /**
             * here we iterate over the results and add them to the oblist described earlier
             */
            while(queryResult.next()){
                oblist.add(new clientsModelTable(queryResult.getString("id"), queryResult.getString("first_name"), queryResult.getString("last_name")));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        table.setItems(oblist);

    }
}
