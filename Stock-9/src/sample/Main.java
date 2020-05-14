package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.Database;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Database database = Database.getInstance();
        database.createDatabase();
        database.createTable();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Stock Exchange");
        primaryStage.setScene(new Scene(root, 900, 450));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
       launch(args);

    }
}
