package warehouse_webservices.reading;

public class Reading
{
   private float temperature, humidity, sound, co2, light;
   private int device;
   private String datetime;
   
   public Reading() {
      
   }
   public Reading(int device) {
      this.device = device;
   }
   public String getDatetime()
   {
      return datetime;
   }
   public void setDatetime(String datetime)
   {
      this.datetime = datetime;
   }
   public float getLight()
   {
      return light;
   }
   public void setLight(float light)
   {
      this.light = light;
   }
   public float getTemperature()
   {
      return temperature;
   }
   public void setTemperature(float temperature)
   {
      this.temperature = temperature;
   }
   public float getHumidity()
   {
      return humidity;
   }
   public void setHumidity(float humidity)
   {
      this.humidity = humidity;
   }
   public float getSound()
   {
      return sound;
   }
   public void setSound(float sound)
   {
      this.sound = sound;
   }
   public float getCo2()
   {
      return co2;
   }
   public void setCo2(float co2)
   {
      this.co2 = co2;
   }
   public int getDevice()
   {
      return device;
   }
   public void setDevice(int device)
   {
      this.device = device;
   }
   
}
