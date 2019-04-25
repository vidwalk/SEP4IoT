package warehouse_webservices.reading;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import warehouse_webservices.adapter.DatabaseAdapter;

@Service
public class ReadingService
{
      private DatabaseAdapter adapter;
      
      public ReadingService() {
         adapter = new DatabaseAdapter();
      }
      
      public Reading getAll() throws SQLException{
         String result = adapter.getAll();
         String[] split = result.split(" ");
         Reading reading = new Reading(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
         return reading;
      }
}
