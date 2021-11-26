package exercise1;

//Data Access tier

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;

 public  class Crud {
  //driver and database info
  static final String DRIVER = "oracle.jdbc.OracleDriver";
  static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
  static final String USERNAME = "COMP228_F21_015_19";
  static final String PASSWORD = "password";
  //
  private String record[] = new String[8];

  //JDBC objects
  private ResultSet rs;
  private Connection connection;
  private Statement st;
  private PreparedStatement pst, updateSt;
  private Statement maxSt;
  private ResultSet maxrs;
  boolean more=true;
  int nCols;
  //
  public Crud() throws SQLException {
    super();
    openConnection();
    //maxSt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
  }
  //
  public void openConnection()
  {
    try {
    	// load the driver class
	    Class.forName( DRIVER );
	    // establish connection to database                              
	    connection = DriverManager.getConnection( DATABASE_URL, USERNAME, PASSWORD );

    }
    catch(Exception e) {
      System.out.println( e.getMessage());
    }
  } //openConnection
  
  public String[] loadCurrentRecord(String strQuery)
  {
      try {
        st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = st.executeQuery(strQuery);
        ResultSetMetaData md = rs.getMetaData();
        nCols=md.getColumnCount();
        if(rs.last())
        {
            record=getRow();
        }
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
    return record;
  } //loadCurrentRecord


  public void closeConnection()
  {
      try {
        rs.close();
      }
      catch(Exception e) {
      System.out.println("Database close failed");
      System.out.println(e.toString());
      }
  } //closeConnection


  public String[] moveNext()
  {
      try {
        if(rs.next() && !rs.isAfterLast())
        {
          record=getRow();
        }
        else
        {
            record=moveLast();
            JOptionPane.showMessageDialog(null,"End of Records!", "TestJDBC",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      catch(Exception e) {
      }
    return record;
  }


  public String[] moveLast()
  {
      try {
            rs.last();
            record=getRow();
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
      return record;
  } //moveLast()

  public String[] moveFirst()
  {
      try {
            rs.first();
            record=getRow();
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
      return record;
  } //moveFirst()


  public String[] movePrevious()
  {
      try {
            if(rs.previous() && !rs.isBeforeFirst())
            {
              record=getRow();
            }
            else
            {
              record=moveFirst();
              JOptionPane.showMessageDialog(null,"Beginning of Records!", "TestJDBC",JOptionPane.INFORMATION_MESSAGE);
            }
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
    return record;
  } //movePrevious()


  public String[] getRow()
  {
      try{
              for(int i=1; i<= nCols; i++)
              	record[i-1]=rs.getString(i);
        }
      catch(SQLException e)
      	{e.printStackTrace();}
    return record;
  }

  public void saveRow(/*String record[]*/)
  {

      try {
  	    	pst = connection.prepareStatement("INSERT INTO GAME (id,name) VALUES(5, 'test')");

            int val = pst.executeUpdate();
            pst.close();
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
  } // saveRow

  public void insertGame( String id, String title){
      try {
          pst = connection.prepareStatement("INSERT INTO GAME (game_id,game_title) VALUES('"+id+"','"+title+"')");

          int val = pst.executeUpdate();
          pst.close();
      }
      catch(Exception e) {
          e.printStackTrace();
      }
  }



  public void insertPlayer( String id, String fName, String lName, String address, String pc, String province, String phone){
         try {
             pst = connection.prepareStatement("INSERT INTO Player (player_id, first_name, last_name, address, postal_code, province, phone_number)\n" +
                     "VALUES(?, ?,?, ?,?, ?,?)");
             pst.setString(1,id);
             pst.setString(2,fName);
             pst.setString(3,lName);
             pst.setString(4,address);
             pst.setString(5,pc);
             pst.setString(6,province);
             pst.setString(7,phone);
             int val = pst.executeUpdate();
             pst.close();
         }
         catch(Exception e) {
             e.printStackTrace();
         }
     }


  public void updateRow(/*String record[]*/)
  {
      //JOptionPane.showMessageDialog(null,record, "update row",JOptionPane.INFORMATION_MESSAGE);

      try 
      {
            //
          	//this code updates the row using a prepared statement
            updateSt = connection.prepareStatement("UPDATE GAME set id=?,name=? where id=1000");
           updateSt.setString(1,"1000");
          updateSt.setString(2,"the witcher");


            int val = updateSt.executeUpdate();
            updateSt.close();

          System.out.println("update successful");
      }
      catch(Exception e) {
      	e.printStackTrace();
          System.out.println(e.toString());
      }
  } // updateRow

     public boolean update()  {
         try (
              PreparedStatement statement =
                      connection.prepareStatement("UPDATE  GAME set name = 'ssssss' where id=1000")) {

             return statement.executeUpdate() > 0;
         } catch (SQLException ex) {
             System.out.println(ex);
         }

         return true;
     }



     public void deleteRow()
  {
      try {
              rs.deleteRow();
              rs.close();
              loadCurrentRecord("Select * from Students"); //open it again
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
  } // deleteRow
} // end of StudentData
