package warehouse_webservices.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import warehouse_webservices.reading.Reading;

public interface Adapter
{
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Reading> getLast() throws SQLException;
    
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<ArrayList<Reading>> getAll(String date) throws SQLException;
}
