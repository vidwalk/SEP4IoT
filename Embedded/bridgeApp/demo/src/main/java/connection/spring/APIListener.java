package connection.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wsConnection.LoRaClient;

@RestController
public class APIListener {


    private LoRaClient loRa;

    @Autowired
    public APIListener(LoRaClient loRa){
        this.loRa = loRa;
    }

    @RequestMapping("/window")
    public void window() {
        System.out.println("API call received");
        loRa.sendText();
    }
}
