package warehouse_webservices.reading;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ReadingController
{
      private ReadingService readingService = new ReadingService();
      
      @RequestMapping("/readings")
      public String getAllReadings() throws SQLException{
         GsonBuilder builder = new GsonBuilder(); 
         builder.setPrettyPrinting(); 
         
         Gson gson = builder.create();
         
         String response = new String(gson.toJson(readingService.getAll()));
         
         return response;
      }
      
      /*@RequestMapping("/readings/{id}")
      public CompletableFuture<String> getReading(@PathVariable String id) {
         return readingService.getReading(id);
      }*/
      
}
