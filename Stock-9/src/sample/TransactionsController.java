package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.TransactionDB.Transactions;
import sample.TransactionDB.TransactionsDAO;
import sample.Util.Messages;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public JFXDatePicker startDatePicker;
    public JFXDatePicker endDatePicker;
    public JFXButton submitButton;
    public TableView<Transactions> tableView;
    public TableColumn<Transactions, Integer> idColumn;
    public TableColumn<Transactions, String> typeColumn;
    public TableColumn<Transactions, Integer> productColumn;
    public TableColumn<Transactions, Integer> quantityColumn;
    public TableColumn<Transactions, String> dateColumn;
    public TableColumn<Transactions, String> remarkColumn;
    private TransactionsDAO transactionsDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transactionsDAO = new TransactionsDAO();
        loadColumns();
    }

    private void loadColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productNameID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }

    public void submitTransact(ActionEvent event) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (startDate == null || endDate == null ){
            Messages.errorMessages("Date Error","Please select dates you want to check.");
            return;
        }
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate.plusDays(1));
        try {
            List<Transactions> transactionsList = transactionsDAO.getTransactions(startSqlDate,endSqlDate);
            tableView.getItems().setAll(transactionsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
