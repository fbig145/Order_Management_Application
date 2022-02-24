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
import model.productsModelTable;

import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import java.io.File;

public class productOrdersController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private TableView<productsModelTable> productTable;
    @FXML
    private TableColumn<productsModelTable, String> col_id_product;
    @FXML
    private TableColumn<productsModelTable, String> col_product_name;
    @FXML
    private TableColumn<productsModelTable, String> col_quantity;

    @FXML
    private TableView<clientsModelTable> clientTable;
    @FXML
    private TableColumn<clientsModelTable, String> col_id_client;
    @FXML
    private TableColumn<clientsModelTable, String> col_first_name;
    @FXML
    private TableColumn<clientsModelTable, String> col_last_name;

    @FXML
    private TextField clientTextField;
    @FXML
    private TextField productTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label messageLabel;

    /**
     * ObservableList holds all the strings taken from the database
     */
    ObservableList<productsModelTable> productOblist = FXCollections.observableArrayList();
    ObservableList<clientsModelTable> clientOblist = FXCollections.observableArrayList();


    /**
     * the method for closing the current pannel
     * @param event when clicked event happen :)
     */
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void orderButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String getQuantity = "select quantity from products where product_name = '" + productTextField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet result = statement.executeQuery(getQuantity);
            String aux = "";
            while(result.next()){
                aux = result.getString("quantity");
            }
            int availableValue = Integer.parseInt(aux);
            int askedValue = Integer.parseInt(quantityTextField.getText());
            /**
             * here we compare if there is enough on stock or not
             */
            if(availableValue < askedValue){
                messageLabel.setText("Quantity unavailable");
            }else{
                messageLabel.setText("Order placed successfully");
                int newValue = availableValue - askedValue;
                aux = String.valueOf(newValue);
                /**
                 * here are all the sql quries to be executed
                 */
                String updateQuantity = "update products set quantity = '" + aux + "' where product_name = '" + productTextField.getText() + "'";
                String getClientID = "select id from clients where first_name = '" + clientTextField.getText()+"'";
                String getProductID = "select id from products where product_name = '" + productTextField.getText()+"'";
                Statement statement2 = connectDB.createStatement();
                statement2.executeUpdate(updateQuantity);

                Statement statement3 = connectDB.createStatement();
                ResultSet cevaRes = statement3.executeQuery(getClientID);
                int ceva = 0;
                while(cevaRes.next()){
                    ceva = cevaRes.getInt("id");
                }

                Statement statement4 = connectDB.createStatement();
                ResultSet cevaRes2 = statement4.executeQuery(getProductID);
                int ceva2 = 0;
                while(cevaRes2.next()){
                    ceva2 = cevaRes2.getInt("id");
                }

                Statement statement5 = connectDB.createStatement();
                statement5.executeUpdate("insert into orders(client_id, product_id, ordered_quantity) values(" + ceva + "," + ceva2 + ",'" + quantityTextField.getText() + "')");

                /**
                 * billText is the string thats its given to the writer to write in the file
                 */
                String billText = "Here is your bill for your order: \n --client: " + clientTextField.getText() +
                        "\n --product: " + productTextField.getText() + "\n --quantity: " + quantityTextField.getText() +
                        "\n *HAVE A GREAT DAY*";
                String pathh = "bills/bill_" + clientTextField.getText() + ".txt";
                File file = new File(pathh);
                boolean fileRes = file.createNewFile();
                if(fileRes){
                    System.out.println("file created");
                    FileWriter myWriter = new FileWriter(pathh);
                    myWriter.write(billText);
                    myWriter.close();
                    System.out.println(billText);
                }else{
                    System.out.println("file already exists");
                }
            }
        }catch(Exception e){
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
        String getAllClients = "select * from clients";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getAllProducts);

            Statement statement2 = connectDB.createStatement();
            ResultSet queryResult2 = statement2.executeQuery(getAllClients);

            while(queryResult.next()){
                productOblist.add(new productsModelTable(queryResult.getString("id"), queryResult.getString("product_name"), queryResult.getString("quantity")));
            }
            while(queryResult2.next()){
                clientOblist.add(new clientsModelTable(queryResult2.getString("id"), queryResult2.getString("first_name"), queryResult2.getString("last_name")));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        col_id_product.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        col_id_client.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        productTable.setItems(productOblist);
        clientTable.setItems(clientOblist);

    }
}
