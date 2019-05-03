package warehouse_webservices.reading;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import warehouse_webservices.adapter.DatabaseAdapter;

@Service
public class ReadingService
{
      private DatabaseAdapter adapter;
      
      public ReadingService() {
         adapter = new DatabaseAdapter();
      }
      
      @Async
      public Future<ArrayList<Reading>> getAll() throws SQLException{
         return new AsyncResult<ArrayList<Reading>>((ArrayList<Reading>) adapter.getAll());
      }
}
