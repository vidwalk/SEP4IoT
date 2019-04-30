package warehouse_webservices.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAdapter
{
   
   String connectionUrl = new String("jdbc:sqlserver://10.200.131.2:1433;"
                                      + "database=climatizerDB;"
                                      + "user=groupZ1;"
                                      + "password=groupZ1;"
                                      + "encrypt=false;"
                                      + "trustServerCertificate=false;"
                                      + "loginTimeout=30;");
   
   public String getAll() throws SQLException {
      
      try (Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {

                  String selectSql = "SELECT TOP 10 Temperature, Device from climatizerDB.dbo.Reading";
                  ResultSet resultSet = statement.executeQuery(selectSql);

                  while (resultSet.next()) 
                     return resultSet.getString(1) + " " + resultSet.getString(2);
                  
      }catch(SQLException e) {
         
         e.printStackTrace();
         
      }
      
      return "";
  }

}
