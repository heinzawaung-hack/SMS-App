package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Database.ProductDAO;
import sample.Database.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LowStocks implements Initializable {

    public TableView<ProductModel> tableView;
    public TableColumn<ProductModel, Integer> productColumn;
    public TableColumn<ProductModel, String> nameColumn;
    public TableColumn<ProductModel, Double> priceColumn;
    public TableColumn<ProductModel, Integer> stockColumn;
    ProductModel productModel;
    ProductDAO productDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productDAO = new ProductDAO();
        loadTable();
        try {
            List<ProductModel> modelList = productDAO.getLowStocks();
            tableView.getItems().setAll(modelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadTable() {
        productColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }


}
