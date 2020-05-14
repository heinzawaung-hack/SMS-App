package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Database.ProductDAO;
import sample.Database.ProductModel;
import sample.TransactionDB.Transactions;
import sample.TransactionDB.TransactionsDAO;
import sample.Util.Messages;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class balances  implements Initializable {
    public JFXTextField productField;
    public JFXTextField quantityField;
    public JFXTextField remarkField;
    public JFXRadioButton inRadioButton;
    public JFXRadioButton outRadioButton;
    public ToggleGroup inOutToggle;
    public JFXButton cancelButton;
    public JFXButton saveButton;
    TransactionsDAO transactionsDAO;
    ProductDAO productDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inRadioButton.setUserData("IN");
        outRadioButton.setUserData("OUT");
        transactionsDAO = new TransactionsDAO();
        productDAO = new ProductDAO();
    }

    public void saveButtonAction(ActionEvent event) {
        String productID = productField.getText();
        String quantity = quantityField.getText();
        String remark = remarkField.getText();
        if (productID.isEmpty() || quantity.isEmpty() || remark.isEmpty()){
            Messages.infoMessages("Input Error","Fields are invalid,Please fill out all fields.");
            return;
        }
        try {
            int id = Integer.parseInt(productID);
            int numberQuantity = Integer.parseInt(quantity);
            boolean count = transactionsDAO.isProductExists(id);
            if (!count){
                Messages.infoMessages("No Such ID","Product ID does't have in product Table.");
            }else {
                String inOutRadio = (String) inOutToggle.getSelectedToggle().getUserData();
                ProductModel productModel = productDAO.getProductWithId(id);
                int finalStock = productModel.getStock();
                if (inOutRadio.equals("IN")){
                    productModel.setStock(finalStock + numberQuantity );
                    productDAO.update(productModel);
                    Messages.infoMessages("Success","Congrats, Transactions Saved");
                }else {
                    if (numberQuantity <= finalStock ){
                        productModel.setStock(finalStock - numberQuantity);
                        productDAO.update(productModel);
                        Messages.infoMessages("Success","Congrats, Transactions Saved");
                    }else {
                        Messages.errorMessages("Error Occurred","Quantity is greater than balance Stock");
                        return;
                    }
                }
                Transactions transactions = new Transactions(inOutRadio,id,numberQuantity,remark);
                transactionsDAO.saveTransaction(transactions);
                clearForm();
            }
        } catch (NumberFormatException | SQLException e) {
            Messages.errorMessages("Invalid","Invalid Number, Please enter again");
        }
    }

    private void clearForm() {
        productField.clear();
        quantityField.clear();
        remarkField.clear();
    }

    public void cancelBalanceAction(ActionEvent event) {
        Stage stage = (Stage) remarkField.getScene().getWindow();
        stage.close();
    }

}
