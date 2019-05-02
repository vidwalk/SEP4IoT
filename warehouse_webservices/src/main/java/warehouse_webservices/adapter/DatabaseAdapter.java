package warehouse_webservices.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import warehouse_webservices.reading.Reading;

public class DatabaseAdapter
{
   
   String connectionUrl = new String("jdbc:sqlserver://10.200.131.2:1433;"
                                      + "database=climatizerDB;"
                                      + "user=groupZ1;"
                                      + "password=groupZ1;"
                                      + "encrypt=false;"
                                      + "trustServerCertificate=false;"
                                      + "loginTimeout=30;");
   
   public ArrayList<Reading> getAll() throws SQLException {
      ArrayList<Reading> readings = new ArrayList<>();
      
      
      try (Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {

                  String selectSql = "SELECT TOP 10 Device, Temperature, Sound, Humidity, CO2, Date from climatizerDB.dbo.Reading";
                  ResultSet resultSet = statement.executeQuery(selectSql);
                  Reading reading;
                  while (resultSet.next()) {
                     reading = new Reading(Integer.parseInt(resultSet.getString(1)));
                     reading.setTemperature(Float.parseFloat(resultSet.getString(2)));
                     reading.setSound(Float.parseFloat(resultSet.getString(3)));
                     reading.setHumidity(Float.parseFloat(resultSet.getString(4)));
                     reading.setCo2(Float.parseFloat(resultSet.getString(5)));
                     reading.setDatetime(resultSet.getString(6));
                     readings.add(reading);
                  }
                  
                  return readings;
                  
      }catch(SQLException e) {
         
         e.printStackTrace();
         
      }
      
      return readings;
  }

}
