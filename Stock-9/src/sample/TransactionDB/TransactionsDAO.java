package sample.TransactionDB;

import sample.Database.Database;
import sample.Database.ProductDAO;
import sample.Database.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDAO {
    public void saveTransaction(Transactions transactions) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String saveMethod = "insert into smsdb.transactions (type,productID,quantity,remark,date) values (?,?,?,?,now());";
        String type = transactions.getType();
        int productID = transactions.getProductID();
        int quantity = transactions.getQuantity();
        String remark = transactions.getRemark();
        PreparedStatement preparedStatement = connection.prepareStatement(saveMethod);
        preparedStatement.setString(1,type);
        preparedStatement.setInt(2,productID);
        preparedStatement.setInt(3,quantity);
        preparedStatement.setString(4,remark);
        preparedStatement.executeUpdate();
        System.out.println("Insert Data Success");
    }

    public List<Transactions> getTransactions(Date startDate,Date endDate) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String getMethod = "select transactions.id,transactions.type,product.name,transactions.quantity,transactions.date,transactions.remark from smsdb.transactions left join smsdb.product on transactions.productID = product.id where date between ? and ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getMethod);
        preparedStatement.setDate(1,startDate);
        preparedStatement.setDate(2,endDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        ProductDAO productDAO = new ProductDAO();
        List<Transactions> list = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type");
            String productName = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            String remark = resultSet.getString("remark");
            Timestamp date = resultSet.getTimestamp("date");
            Transactions transactions = new Transactions(id,type,productName,quantity,remark,date.toString());
            list.add(transactions);
        }
        return list;
    }

    public boolean isProductExists(int id) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String exists = "select count(*) as total from smsdb.product where id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(exists);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            int count = resultSet.getInt("total");
            if (count == 0){
                return false;
            }
        }
        return true;
    }
}
