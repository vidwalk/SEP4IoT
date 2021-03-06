package warehouse_webservices.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import warehouse_webservices.reading.Reading;

public class DatabaseAdapter implements Adapter
{
   
   private String connectionUrl ;
   
   public DatabaseAdapter() {
      connectionUrl = new String("jdbc:sqlserver://10.200.131.2:1433;"
            + "database=climatizerDimensional;"
            + "user=groupZ1;"
            + "password=groupZ1;"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;");
   }
   @Async("threadPoolTaskExecutor")
   public CompletableFuture<Reading> getLast() throws SQLException {
      
      Reading reading = new Reading();
      
      
      try (Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {

                  String selectSql = 
                          "SELECT TOP 1 Time30, convert(date, CalendarDate) as [Date], CO2Value, HumidityValue, TemperatureValue, LightValue "
                        + "FROM climatizerDimensional.dbo.F_Reading "
                        + "JOIN D_Date ON D_Date.DateKey = F_Reading.DateKey "
                        + "JOIN D_Time ON D_Time.TimeKey = F_Reading.TimeKey "
                        + "ORDER BY Date desc, Time30 desc";

                  ResultSet resultSet = statement.executeQuery(selectSql);
                  
                  while (resultSet.next()) {
                     reading = new Reading();
                     reading.setDatetime(resultSet.getString(1) + " " + resultSet.getString(2));
                     reading.setCo2(Float.parseFloat(resultSet.getString(3)));
                     reading.setHumidity(Float.parseFloat(resultSet.getString(4)));                     
                     reading.setTemperature(Float.parseFloat(resultSet.getString(5)));                 
                     reading.setLight(Float.parseFloat(resultSet.getString(6)));
                     
                  }
                  
                  return CompletableFuture.completedFuture(reading);
                  
      }catch(SQLException e) {
         
         e.printStackTrace();
         
      }
      
      return CompletableFuture.completedFuture(reading);
  }
  @Async("threadPoolTaskExecutor")
  public CompletableFuture<ArrayList<Reading>> getAll(String something) {
     ArrayList<Reading> readings = new ArrayList<>();
     
     try (Connection connection = DriverManager.getConnection(connectionUrl);
           Statement statement = connection.createStatement();) {

                 String selectSql = 
                         "SELECT Hour30, " 
                       + "AVG(CO2Value) as AverageCO2, " 
                       + "AVG(HumidityValue) as AverageHumidity, " 
                       + "AVG(TemperatureValue) as AverageTemperature, " 
                       + "AVG(LightValue) as AverageLight "
                       + "FROM climatizerDimensional.dbo.F_Reading "
                       + "JOIN D_Date ON D_Date.DateKey = F_Reading.DateKey "
                       + "JOIN D_Time ON D_Time.TimeKey = F_Reading.TimeKey "
                       + "WHERE convert(date, [CalendarDate]) BETWEEN '" + something + "' AND '" + something + "' GROUP BY Hour30";
                 
                 ResultSet resultSet = statement.executeQuery(selectSql);
                 Reading reading;
                 while (resultSet.next()) {
                    reading = new Reading();
                    reading.setDatetime(resultSet.getString(1));
                    reading.setCo2(Float.parseFloat(resultSet.getString(2)));
                    reading.setHumidity(Float.parseFloat(resultSet.getString(3)));                     
                    reading.setTemperature(Float.parseFloat(resultSet.getString(4)));                 
                    reading.setLight(Float.parseFloat(resultSet.getString(5)));
                    readings.add(reading);
                 }
                 
                 return CompletableFuture.completedFuture(readings);
                 
     }catch(SQLException e) {
        
        e.printStackTrace();
        
     }
     return CompletableFuture.completedFuture(readings);
  }

}
