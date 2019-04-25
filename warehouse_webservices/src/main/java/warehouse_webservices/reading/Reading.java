package warehouse_webservices.reading;

public class Reading
{
   private double temperature;
   private int device;
   
   public Reading() {
      
   }
   public Reading(int device, double temperature) {
      this.device = device;
      this.temperature = temperature;
   }
   
   public double getTemp() {
      return temperature;
   }
   
   public int getDevice() {
      return device;
   }
   
   public void setTemp(double temp) {
      temperature = temp;
   }
   
   public void setDevice(int device) {
      this.device = device;
   }
}
