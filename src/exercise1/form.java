package exercise1;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;


public class form  extends Application {

    // JDBC database URL, username and password
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    static final String USERNAME = "COMP228_F21_015_7";
    static final String PASSWORD = "password";

    // default query retrieves all data from authors table
    static final String DEFAULT_QUERY = "SELECT * FROM game";

    private BorderPane borderPane;
    private GridPane empPane;
    private Label lblGameName, lblGameId;
    private TextField txtGameName, txtGameId;
    private HBox east;

    private Button btnUpdateDB;

    public form(){}


    @Override
    public void start(Stage stage) throws Exception {
        //initializing panes
       borderPane = new BorderPane();
       empPane = new GridPane();
        //styling panes
        empPane.setHgap(5);
        empPane.setVgap(5);

       //initializing components
       lblGameId = new Label("Game ID:");
       lblGameName = new Label("Game Name:");
       txtGameId = new TextField();
       txtGameName = new TextField();
       btnUpdateDB = new Button("update DB");

       // filling
        empPane.add(lblGameId,0, 0);



        Scene scene = new Scene(borderPane, 600, 350);



        //events
        btnUpdateDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ResultSetTableModel rsm = new ResultSetTableModel(DRIVER,DATABASE_URL,USERNAME,PASSWORD,DEFAULT_QUERY);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}
