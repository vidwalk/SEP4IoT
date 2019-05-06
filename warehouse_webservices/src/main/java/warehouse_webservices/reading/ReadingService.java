package warehouse_webservices.reading;

import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import warehouse_webservices.adapter.Adapter;
import warehouse_webservices.adapter.DatabaseAdapter;

@Service
public class ReadingService
{
      private Adapter adapter;
      
      public ReadingService() {
         adapter = new DatabaseAdapter();
      }
      
      @SuppressWarnings("unchecked")
      public ArrayList<Reading> getAll() throws SQLException{
         return (ArrayList<Reading>) adapter.getAll();
      }
}
