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
      
      public ArrayList<Reading> getAll() throws SQLException{
         return adapter.getAll();
      }
}
