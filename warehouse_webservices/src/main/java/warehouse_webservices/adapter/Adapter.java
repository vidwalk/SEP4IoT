package warehouse_webservices.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import warehouse_webservices.reading.Reading;

public interface Adapter
{
    public ArrayList<Reading> getAll() throws SQLException;
    
    public ArrayList<Reading> getReading(String date) throws SQLException;
}
