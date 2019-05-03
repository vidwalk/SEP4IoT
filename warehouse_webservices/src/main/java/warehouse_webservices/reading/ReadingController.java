package warehouse_webservices.reading;

import java.sql.SQLException;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ReadingController
{
      private ReadingService readingService = new ReadingService();
      
      @Async
      @RequestMapping("/readings")
      public Future<String> getAllReadings() throws SQLException{
         GsonBuilder builder = new GsonBuilder(); 
         builder.setPrettyPrinting(); 
         
         Gson gson = builder.create();
         
         String response = new String(gson.toJson(readingService.getAll()));
         
         return new AsyncResult<String>(response);
      }
      
      /*@RequestMapping("/readings/{id}")
      public CompletableFuture<String> getReading(@PathVariable String id) {
         return readingService.getReading(id);
      }*/
      
}
