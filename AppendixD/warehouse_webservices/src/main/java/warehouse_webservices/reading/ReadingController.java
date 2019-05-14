package warehouse_webservices.reading;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadingController
{
      private ReadingService readingService;
      private String response;
      
      public ReadingController() {
         readingService = new ReadingService(this);
      }
      
      //Returns the latest reading
      @Async("threadPoolTaskExecutor")
      @RequestMapping("/readings")
      public CompletableFuture<String> GetLast() throws SQLException, InterruptedException, ExecutionException{       
         readingService.getLast();
         return CompletableFuture.completedFuture(response);
      }
      
      //Returns all the readings from the specified date {yyyy-mm-dd}
      @Async("threadPoolTaskExecutor")
      @RequestMapping("/readings/{date}")
      public CompletableFuture<String> GetReading(@PathVariable String date) throws SQLException, InterruptedException, ExecutionException {
         readingService.getAll(date);
         System.out.println(date);
         return CompletableFuture.completedFuture(response);
      }
      
      @RequestMapping(method = RequestMethod.PUT, value ="/window")
      public void operateWindow(@RequestBody byte[] bytes) throws IOException {
         String message = new String(bytes);
         readingService.operateWindow(message);
      }
      
      public void setResponse(String response) {
         this.response = response;
      }
}
