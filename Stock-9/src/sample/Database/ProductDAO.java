package sample.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public void saveProduct(ProductModel productModel) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String save = "insert into smsdb.product (id,name,price,stock) values (?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(save);
        preparedStatement.setInt(1,productModel.getId());
        preparedStatement.setString(2,productModel.getName());
        preparedStatement.setDouble(3,productModel.getPrice());
        preparedStatement.setInt(4,productModel.getStock());
        preparedStatement.execute();
    }

    public List<ProductModel> getProduct() throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String getData = "select * from smsdb.product";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getData);

        List<ProductModel> modelList = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int stock = resultSet.getInt("stock");

            ProductModel productModel = new ProductModel(id,name,price,stock);
            modelList.add(productModel);
        }
        return modelList;
    }

    public ProductModel getProductWithId(int id) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String getWithId = "select * from smsdb.product where id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getWithId);
        preparedStatement.setInt(1,id);
        ResultSet resultSet =  preparedStatement.executeQuery();
        ProductModel productModel = null;
        if (resultSet.next()){
            int idOne = resultSet.getInt("id");
            String nameOne = resultSet.getString("name");
            double priceOne = resultSet.getDouble("price");
            int stockOne = resultSet.getInt("stock");
            productModel = new ProductModel(idOne,nameOne,priceOne,stockOne);
        }
        return productModel;
    }

    public void update(ProductModel productModel) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = productModel.getId();
        String name = productModel.getName();
        double price = productModel.getPrice();
        int stock = productModel.getStock();
        String updateData = "update smsdb.product set name=?,price=?,stock=? where id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updateData);
        preparedStatement.setString(1,name);
        preparedStatement.setDouble(2,price);
        preparedStatement.setInt(3,stock);
        preparedStatement.setInt(4,id);
        preparedStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException{
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String deleteData = "delete from smsdb.product where id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteData);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
        System.out.println("Delete Data Success");
    }

    public List<ProductModel> searchName(String name) throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String search = "select * from smsdb.product where name like '%" + name + "%';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(search);
        List<ProductModel> modelList = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String searchName = resultSet.getString("name");
            double searchPrice = resultSet.getDouble("price");
            int searchStock = resultSet.getInt("stock");
            ProductModel productModel = new ProductModel(id,searchName,searchPrice,searchStock);
            modelList.add(productModel);
        }
        return modelList;
    }

    public List<ProductModel> getLowStocks() throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String lowStock = "select * from smsdb.product where stock < 5;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(lowStock);
        List<ProductModel> list = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int stock = resultSet.getInt("Stock");
            ProductModel productModel = new ProductModel(id,name,price,stock);
            list.add(productModel);
        }
        return list;
    }

    public int countProduct() throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String countMethod = "select count(*) as count from smsdb.product;";
        Statement statement = connection.createStatement();
        int count = 0;
        ResultSet resultSet = statement.executeQuery(countMethod);
        if (resultSet.next()){
            count = resultSet.getInt("count");
        }
        return count;
    }

    public int lowStockCount() throws SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String stockCount = "select count(*) as count from smsdb.product where stock <= 5;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(stockCount);
        int count = 0;
        if (resultSet.next()){
            count = resultSet.getInt("count");
        }
        return count;
    }
}
