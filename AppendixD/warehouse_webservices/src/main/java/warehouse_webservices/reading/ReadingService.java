package warehouse_webservices.reading;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import warehouse_webservices.adapter.Adapter;
import warehouse_webservices.adapter.DatabaseAdapter;

@Service
public class ReadingService
{
      private Adapter adapter;
      private GsonBuilder bob;
      private Gson gson;
      private ReadingController controller;
      
      public ReadingService(ReadingController controller) {
         adapter = new DatabaseAdapter();
         bob = new GsonBuilder(); 
         bob.setPrettyPrinting();
         gson = bob.create();
         this.controller = controller;
      }
      @Async("threadPoolTaskExecutor")
      public void getAll(String date) throws SQLException, InterruptedException, ExecutionException{
         
         ArrayList<Reading> readings = adapter.getAll(date).get(); 
         String response = new String(gson.toJson(readings));
         controller.setResponse(response);
      }
      @Async("threadPoolTaskExecutor")
      public void getLast() throws SQLException, InterruptedException, ExecutionException{
         
         Reading reading = adapter.getLast().get();
         String response = new String(gson.toJson(reading));
         controller.setResponse(response);
      }
      
      public void operateWindow(String message) {
         
         message = gson.toJson(message);
         System.out.println(message);
      }
}
