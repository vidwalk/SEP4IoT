package warehouse_webservices;

import java.sql.SQLException;
import java.util.concurrent.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class WarehouseApiApp
{

   public static void main(String[] args) throws SQLException
   {
      SpringApplication.run(WarehouseApiApp.class, args);
      
   }

   @Bean("threadPoolTaskExecutor")
   public Executor getAsyncExecutor() {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(20);
       executor.setMaxPoolSize(1000);
       executor.setWaitForTasksToCompleteOnShutdown(true);
       executor.setThreadNamePrefix("Async-");
       return executor;
   }
}
