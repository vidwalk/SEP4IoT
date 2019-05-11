package warehouse_webservices.reading;

import java.io.IOException;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ReadingController
{
      private Gson gson;
      private GsonBuilder builder;
      private ReadingService readingService;
      
      public ReadingController() {
         builder = new GsonBuilder(); 
         builder.setPrettyPrinting();
         gson = builder.create();
         readingService = new ReadingService();
      }
      
      //Returns the latest 10 readings
      @RequestMapping("/readings")
      public String getAllReadings() throws SQLException{        
         String response = new String(gson.toJson(readingService.getAll()));
         
         return response;
      }
      //Returns all the readings from the specified date {yyyy-mm-dd}
      @RequestMapping("/readings/{id}")
      public String getReading(@PathVariable String id) throws SQLException {
         String response = new String(gson.toJson(readingService.getReading(id)));
         return response;
      }
      @RequestMapping(method = RequestMethod.PUT, value ="/window")
      public void operateWindow(@RequestBody byte[] bytes) throws IOException {
         
         String message = new String(bytes);
         message = gson.toJson(message);
         System.out.println(message);
      }
      
      
}
