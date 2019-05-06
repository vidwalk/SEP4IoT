package warehouse_webservices.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import warehouse_webservices.reading.Reading;

public interface Adapter
{
    public ArrayList<Reading> getAll() throws SQLException;
}
