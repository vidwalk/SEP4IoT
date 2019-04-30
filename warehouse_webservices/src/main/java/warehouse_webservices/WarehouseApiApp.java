package warehouse_webservices;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import warehouse_webservices.adapter.DatabaseAdapter;

@SpringBootApplication
@EnableAsync
public class WarehouseApiApp
{

   public static void main(String[] args) throws SQLException
   {
      SpringApplication.run(WarehouseApiApp.class, args);

   }

}
