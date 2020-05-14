package sample.NewProduct;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Database.ProductDAO;
import sample.Database.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewProduct implements Initializable {

    public JFXTextField productName;
    public JFXTextField productID;
    public JFXTextField unitPrice;
    public Button cancelButton;
    public Button saveButton;
    private ProductDAO productDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productDAO = new ProductDAO();
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void saveOnAction(ActionEvent event) {
        String name = productName.getText();
        String product = productID.getText();
        String price = unitPrice.getText();

        if ((name.isEmpty()) || (product.isEmpty()) || (price.isEmpty()) ) {
            System.out.println("Please fill out all field");
            return;
        }
        try {
            int productId = Integer.parseInt(product);
            double unitPrice = Double.parseDouble(price);
            ProductModel productModel = new ProductModel(productId,name,unitPrice,0);
            productDAO.saveProduct(productModel);
            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid Number");
        }catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Duplicate Error");
            alert.setContentText("ID is already have. Please enter new ID");
            alert.show();
        }

    }
}
