package sample.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database database;
    private Connection connection;

    private Database() throws SQLException {
        connect();
    }

    public static Database getInstance() throws SQLException {
        if (database == null){
            database = new Database();
        }
        return database;
    }

    private void connect() throws SQLException {
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3307/";
        connection = DriverManager.getConnection(url, userName, password);
        System.out.println("Connected to Database");
    }

    public void createDatabase() throws SQLException {
        String create = "create database if not exists smsdb";
        Statement statement = connection.createStatement();
        statement.execute(create);
    }

    public void createTable() throws SQLException {
        String createTable = "create table if not exists smsdb.product (id int primary key,name varchar(50),price double,stock int);";
        Statement statement = connection.createStatement();
        statement.execute(createTable);

        String createTransactions = "create table if not exists smsdb.transactions (id int auto_increment primary key,type varchar(10),productID int,quantity int,remark varchar(255),date timestamp,foreign key (productID) references product(id));";
        Statement createStatement = connection.createStatement();
        createStatement.execute(createTransactions);
    }

    public Connection getConnection(){
        return connection;
    }
}
