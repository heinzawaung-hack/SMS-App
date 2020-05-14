package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import sample.Database.ProductDAO;
import sample.Database.ProductModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    public Button addButton;
    public TableView<ProductModel> tableView;
    public TableColumn<ProductModel, Integer> productColumn;
    public TableColumn<ProductModel, String> nameColumn;
    public TableColumn<ProductModel, Double> priceColumn;
    public TableColumn<ProductModel, Integer> stockColumn;
    public MenuItem deleteItem;
    public JFXTextField nameSearchField;
    private ProductDAO productDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productDAO = new ProductDAO();
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        initColumn();
        loadTableData();
    }

    private void initColumn() {
        productColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    public void loadNewProductWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/NewProduct/newProduct.fxml"));
        Scene scene = new Scene(root,600,154);
        Stage stage = new Stage();
        stage.initOwner(addButton.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.setTitle("Save");
        stage.showAndWait();
        loadTableData();
    }

    private void loadTableData(){
        try {
            List<ProductModel> productModelList = productDAO.getProduct();
            tableView.getItems().setAll(productModelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(ActionEvent event) {
       ProductModel productModel =  tableView.getSelectionModel().getSelectedItem();
       if (productModel == null){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Please select one item to delete");
           alert.show();
           return;
       }
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText(null);
       alert.setContentText("Are you sure you want to delete?");
       Optional<ButtonType> optional = alert.showAndWait();
       if (optional.get() == ButtonType.OK){
           try{
               productDAO.delete(productModel.getId());
               tableView.getItems().remove(productModel);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    }

    public void updateName(TableColumn.CellEditEvent<ProductModel, String> productModelStringCellEditEvent) {
        ProductModel model =  productModelStringCellEditEvent.getRowValue();
        model.setName(productModelStringCellEditEvent.getNewValue());
        try {
            productDAO.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(TableColumn.CellEditEvent<ProductModel, Double> productModelDoubleCellEditEvent) {
        ProductModel productModel = productModelDoubleCellEditEvent.getRowValue();
        productModel.setPrice(productModelDoubleCellEditEvent.getNewValue());
        try {
            productDAO.update(productModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchAction(ActionEvent event) {
        String name = nameSearchField.getText();
        try {
            List<ProductModel> modelList = productDAO.searchName(name);
            tableView.getItems().clear();
            tableView.getItems().setAll(modelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
