package sample;

import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sample.Database.ProductDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button dashboardButton;
    public Button productButton;
    public HBox dashboardView;
    public BorderPane borderPane;
    public Button balancesButton;
    public Button lowStockButton;
    public Button transactionsButton;
    public Label productPreview;
    public Label lowStockPreview;
    private ProductDAO productDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productDAO = new ProductDAO();
        loadDashboardData();
    }

    private void loadDashboardData() {
        try {
            int productTotal = productDAO.countProduct();
            int lowStockTotal = productDAO.lowStockCount();
            String product = Integer.toString(productTotal);
            String lowStock = Integer.toString(lowStockTotal);
            productPreview.setText(product);
            lowStockPreview.setText(lowStock);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardView(ActionEvent event) {
        borderPane.setCenter(dashboardView);
        loadDashboardData();
    }

    public void productAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("product.fxml"));
        borderPane.setCenter(root);
    }

    public void balancesAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("balances.fxml"));
        borderPane.setCenter(root);
    }

    public void lowStockButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LowStocks.fxml"));
        borderPane.setCenter(root);
    }

    public void transactionsView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Transactions.fxml"));
        borderPane.setCenter(root);
    }
}
