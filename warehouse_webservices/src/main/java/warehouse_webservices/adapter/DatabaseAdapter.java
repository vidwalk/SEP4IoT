package warehouse_webservices.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import warehouse_webservices.reading.Reading;

public class DatabaseAdapter implements Adapter
{
   
   private String connectionUrl ;
   
   public DatabaseAdapter() {
      connectionUrl = new String("jdbc:sqlserver://10.200.131.2:1433;"
            + "database=climatizerDB;"
            + "user=groupZ1;"
            + "password=groupZ1;"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;");
   }
   
   public Reading getAll() throws SQLException {
      
      Reading reading = new Reading();
      
      
      try (Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {

                  String selectSql = "SELECT TOP 1 DeviceId, Temperature, Sound, Humidity, CO2, Light, convert(varchar(8), "
                        + "convert(time, [Date])) as [Time], convert(date, [Date]) as [Date] from climatizerDB.dbo.Reading ORDER BY Date desc";
                  ResultSet resultSet = statement.executeQuery(selectSql);
                  
                  while (resultSet.next()) {
                     reading = new Reading(Integer.parseInt(resultSet.getString(1)));
                     reading.setTemperature(Float.parseFloat(resultSet.getString(2)));
                     reading.setSound(Float.parseFloat(resultSet.getString(3)));
                     reading.setHumidity(Float.parseFloat(resultSet.getString(4)));
                     reading.setCo2(Float.parseFloat(resultSet.getString(5)));
                     reading.setLight(Float.parseFloat(resultSet.getString(6)));
                     reading.setDatetime(resultSet.getString(7) + " " + resultSet.getString(8));
                  }
                  
                  return reading;
                  
      }catch(SQLException e) {
         
         e.printStackTrace();
         
      }
      
      return reading;
  }
   
  public ArrayList<Reading> getReading(String something) {
     ArrayList<Reading> readings = new ArrayList<>();
     
     try (Connection connection = DriverManager.getConnection(connectionUrl);
           Statement statement = connection.createStatement();) {

                 String selectSql = "SELECT DeviceId, Temperature, Sound, Humidity, CO2, Light, convert(varchar(8), "
                       + "convert(time, [Date])) as [Time], convert(date, [Date]) as [Date] from climatizerDB.dbo.Reading"
                       + " WHERE convert(date, [Date]) between '" + something + "' and '" + something + "' ORDER BY Time desc";
                 ResultSet resultSet = statement.executeQuery(selectSql);
                 Reading reading;
                 while (resultSet.next()) {
                    reading = new Reading(Integer.parseInt(resultSet.getString(1)));
                    reading.setTemperature(Float.parseFloat(resultSet.getString(2)));
                    reading.setSound(Float.parseFloat(resultSet.getString(3)));
                    reading.setHumidity(Float.parseFloat(resultSet.getString(4)));
                    reading.setCo2(Float.parseFloat(resultSet.getString(5)));
                    reading.setLight(Float.parseFloat(resultSet.getString(6)));
                    reading.setDatetime(resultSet.getString(7) + " " + resultSet.getString(8));
                    readings.add(reading);
                 }
                 
                 return readings;
                 
     }catch(SQLException e) {
        
        e.printStackTrace();
        
     }
     return readings;
  }

}
