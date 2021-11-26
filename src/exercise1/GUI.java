package exercise1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.Scene;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class GUI extends Application {

    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    static final String USERNAME = "COMP228_F21_015_7";
    static final String PASSWORD = "password";


    // default query retrieves all data from authors table
    static  String DEFAULT_QUERY = "SELECT * FROM game";

    private PreparedStatement n;
    private BorderPane borderPane;
    private GridPane empPanel;
    private Label lblGameId, lblGameName, lblPlayerId,lblFName,lblLname, lblAddress, lblPC,lblProvince,lblPhoneN;
    private TextField txtGameId, txtGameName,txtPlayerId,txtFName,txtLname, txtAddress, txtPC,txtProvince,txtPhoneN;
    private Button  btnUpdateDB, btnAddPlayer, btnAddGame;
    private TableView table;
    /*private ObservableList<PlayerGameInfo> tableData = FXCollections.observableArrayList(
            new PlayerGameInfo("PLAYERGAMEID","GAMEID","PLAYERID","DATE","SCORE"));*/

    private HBox hBox;
    private VBox coursesBox;
    private HBox east;
    private String[] courses1 = {"Java","C#","Python","JavaScript"};
    private String[] courses2 = {"Project Management","Economics","Global Logistics", "Entrepreneurship "};
    private ComboBox<String> cbo;
    private TextArea tArea;
    private CheckBox chkStudentCouncil;
    private CheckBox chkVolunteer;
    //
    private ToggleGroup group;
    private RadioButton rb1, rb2, rb3;
    private ListView<String> lv;
    ArrayList<String> coursesIT = new ArrayList<>();
    ArrayList<String> coursesIB = new ArrayList<>();

    Crud db = new Crud();

    private ObservableList data = FXCollections.observableArrayList();

    public GUI() throws SQLException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        borderPane = new BorderPane();



        //create the grid pane for entries
        empPanel = new GridPane();
        empPanel.setHgap(5);
        empPanel.setVgap(5);
        //create labels

        lblGameId =new Label("Game Id:");
        lblGameName =new Label("Game Name:");




        lblPlayerId = new Label("Player ID:");
        lblFName=new Label("First Name:");
        lblLname= new Label("Last Name:");
        lblAddress = new Label("Address:");
        lblPC = new Label("Postal Code:");
        lblProvince= new Label("Province:");
        lblPhoneN=new Label("Phone Number:");


        //create text fields
        txtGameId =new TextField();
        txtGameName =new TextField();
        txtPlayerId = new TextField();
        txtFName =new TextField();
        txtLname =new TextField();
        txtAddress = new TextField();
        txtPC =new TextField();
        txtProvince =new TextField();
        txtPhoneN = new TextField();

        btnAddGame=new Button("Add Game");
        btnAddPlayer=new Button("Add Player");

        table = new TableView();
        table.setEditable(false);
        TableColumn playerGameID = new TableColumn("Player-Game-ID");
        TableColumn   gameID =new TableColumn("Game ID");
        TableColumn   playerID =new TableColumn("Player ID");
        TableColumn   playingDate =new TableColumn("Playing Date");
        TableColumn    score = new TableColumn("Score");

        playerGameID.setCellFactory(new PropertyValueFactory<PlayerGameInfo,String>("Player-Game-ID"));
        playerGameID.setCellFactory(new PropertyValueFactory<PlayerGameInfo,String>("Game-ID"));
        playerGameID.setCellFactory(new PropertyValueFactory<PlayerGameInfo,String>("Player-ID"));
        playerGameID.setCellFactory(new PropertyValueFactory<PlayerGameInfo,String>("Date"));
        playerGameID.setCellFactory(new PropertyValueFactory<PlayerGameInfo,String>("Score"));


        //tableData.addAll("15662","46848","assf46","20/12/20","35356");
        //table.setItems(tableData);
        table.getColumns().addAll(playerGameID,playerID,gameID,playingDate,score);




        VBox panelChkBox = new VBox(20);
        panelChkBox.setPadding(new Insets(10,10,10,10));
        chkStudentCouncil = new CheckBox("Student Council");
        chkVolunteer = new CheckBox("Volunteer Work");


        //create buttons

        btnUpdateDB = new Button("Update DB");
        //add controls to grid pane
        empPanel.add(lblGameId,0,0);
        empPanel.add(txtGameId,1,0);
        empPanel.add(lblGameName,0,1);
        empPanel.add(txtGameName,1,1);
        empPanel.add(btnAddGame,1,2);

        empPanel.add(lblPlayerId,2,0);
        empPanel.add(txtPlayerId,3,0);
        empPanel.add(lblFName,2,1);
        empPanel.add(txtFName,3,1);
        empPanel.add(lblLname,2,2);
        empPanel.add(txtLname,3,2);
        empPanel.add(lblAddress,2,3);
        empPanel.add(txtAddress,3,3);
        empPanel.add(lblPC,2,4);
        empPanel.add(txtPC,3,4);
        empPanel.add(lblProvince,2,5);
        empPanel.add(txtProvince,3,5);
        empPanel.add(lblPhoneN,2,6);
        empPanel.add(txtPhoneN,3,6);
        empPanel.add(btnAddPlayer,3,7);


        //place grid pane in the center of border pane
        borderPane.setCenter(empPanel);
        borderPane.setBottom(table);

       /* //align buttons in grid pane


        //create the text area
        tArea=new TextArea();
        tArea.setPrefSize(1000,100);



        //create the toggle group to group radio buttons
        group = new ToggleGroup();
        //create radio buttons and add them to the toggle group
        rb1 = new RadioButton("Computer Science");
        rb1.setToggleGroup(group);
        //rb1.setSelected(true);
        rb2 = new RadioButton("Business");
        rb2.setToggleGroup(group);
        // Create a scroll pane to hold the text area
        ScrollPane scrollPane = new ScrollPane(tArea);




        //create the box pane and place to the right

        coursesBox = new VBox();

        HBox hbox = new HBox();
        hbox.getChildren().add(rb1);
        hbox.getChildren().add(rb2);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(20));


        Text coursesTitle = new Text("Courses");
        coursesBox.getChildren().add(hbox);
        coursesBox.getChildren().add(coursesTitle);

        cbo = new ComboBox<>();

        ObservableList<String> items1=FXCollections.observableArrayList(courses1);
        ObservableList<String> items2=FXCollections.observableArrayList(courses2);




        cbo.setPrefSize(200, 50);
        lv = new ListView<>(FXCollections.observableArrayList());
        lv.setPrefSize(200, 200);



        coursesBox.getChildren().addAll(cbo,lv);
        //
        east = new HBox();
        east.getChildren().addAll(coursesBox);

        borderPane.setRight(east);
*/

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 600, 350);

        primaryStage.setTitle("Student Info"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        //Events
        btnUpdateDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

//                COMP228_F21_015_7
//
//                        password
                try {
                    connect2("COMP228_F21_015_7", "password");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        int gameId=4;
        btnAddGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int number = Integer.parseInt(txtGameId.getText());
                if(number>gameId)
                db.insertGame(txtGameId.getText(),txtGameName.getText());
                else JOptionPane.showMessageDialog(null,"Invalid Game Id");

            }
        });

        btnAddPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //db.openConnection();
                db.insertPlayer(txtPlayerId.getText(), txtFName.getText(),txtLname.getText(), txtAddress.getText(),txtPC.getText(), txtProvince.getText(),txtPhoneN.getText());
            }
        });





    }


    // display entries in text area


    public void connect2(String user, String password) throws SQLException {
        Crud crud = new Crud();

        //crud.saveRow();
        //crud.updateRow();
        crud.update();
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);

    }

}

