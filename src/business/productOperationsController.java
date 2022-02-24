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
import model.productsModelTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class productOperationsController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private TableView<productsModelTable> table;
    @FXML
    private TableColumn<productsModelTable, String> col_id;
    @FXML
    private TableColumn<productsModelTable, String> col_product_name;
    @FXML
    private TableColumn<productsModelTable, String> col_quantity;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField deleteTextField;
    @FXML
    private Label deleteLabel;
    @FXML
    private TextField addProductTextField;
    @FXML
    private TextField addQuantityTextField;
    @FXML
    private Button addProduct;
    @FXML
    private Label addLabel;
    @FXML
    private Button editButton;
    @FXML
    private TextField editNameTextField;
    @FXML
    private TextField editQuantityTextField;
    @FXML
    private TextField editIDTextField;
    @FXML
    private Label editLabel;

    /**
     * ObservableList holds all the strings taken from the database
     */
    ObservableList<productsModelTable> oblist = FXCollections.observableArrayList();

    /**
     * this method edits the product info by updating them in the database
     * @param event when the button is pressed
     */
    public void editButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * this string is actually the sql string that will be executed
         */
        String editClient = "update products set product_name = '" + editNameTextField.getText() + "', quantity = '" + editQuantityTextField.getText() + "' where id =" + editIDTextField.getText();

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(editClient);
            /**
             *  this label is shown to the user when everything went right
             */
            editLabel.setText("Product edited successfully");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    /**
     * this class add a new product to the db
     * @param event when the button is pressed the event will happen
     */
    public void addProductOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * this string is the sql query for inserting new clients into the db
         */
        String addClient = "insert into products (product_name, quantity) values('" + addProductTextField.getText() + "','" + addQuantityTextField.getText() + "')";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addClient);
            addLabel.setText("Product added successfully");
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
     * @param event
     */
    public void deleteButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        /**
         * the query for deleting a product from the db
         */
        String deleteClient = "delete from products where id='" + deleteTextField.getText() + "'";
        try{
            /**
             * new statement in order to execute the query
             */
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteClient);
            /**
             * message label for informing the user that the procedure was done
             */
            deleteLabel.setText("Product was deleted successfully");
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

        String getAllProducts = "select * from products";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getAllProducts);
            while(queryResult.next()){
                oblist.add(new productsModelTable(queryResult.getString("id"), queryResult.getString("product_name"), queryResult.getString("quantity")));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table.setItems(oblist);

    }

}
