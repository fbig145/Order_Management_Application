package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Fabian-Bob Ioan George
 */

public class adminPageController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button clientOperationsButton;

    /**
     * this method closes the current scene
     * @param event
     */
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * this is the method where I load the Client Operations panel
     */
    public void showClientOperations(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/clientOperations.fxml"));
            Parent root = (Parent) loader.load();
            clientOperationsController secController = loader.getController();

            Stage updateStage = new Stage();
            updateStage.initStyle(StageStyle.UNDECORATED);
            updateStage.setScene(new Scene(root, 600, 400));
            updateStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }


    /**
     * this method loads the Product Operations panel
     */

    public void showProductOperations(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/productOperations.fxml"));
            Parent root = (Parent) loader.load();
            productOperationsController secController = loader.getController();

            Stage updateStage = new Stage();
            updateStage.initStyle(StageStyle.UNDECORATED);
            updateStage.setScene(new Scene(root, 600, 400));
            updateStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * this method loads the Product Orders panel
     */

    public void showProductOrder(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/productOrders.fxml"));
            Parent root = (Parent) loader.load();
            productOrdersController secController = loader.getController();

            Stage updateStage = new Stage();
            updateStage.initStyle(StageStyle.UNDECORATED);
            updateStage.setScene(new Scene(root, 800, 400));
            updateStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * this method shows the the panel when the button is pressed by the user
     */
    public void orderButtonOnAction(ActionEvent event){
        showProductOrder();
    }

    /**
     * this method shows the the panel when the button is pressed by the user
     */
    public void productOperationsButtonOnAction(ActionEvent event){
        showProductOperations();
    }

    /**
     * this method shows the the panel when the button is pressed by the user
     */
    public void clientOperationsButtonOnAction(ActionEvent event){
        showClientOperations();
    }

}
