package com.example.demo;

import connection.spring.wsConnection.UplinkMessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplinkMessageFormatter.class)
public class TestingUplinkMessageFormatter {

	@Test
	public void dataConvertion() throws JSONException {
		String openWindowMessage = new JSONObject()
				.put("data", "011503E7022B0312").toString();

		String returned = UplinkMessageFormatter.receiveMessage(openWindowMessage);
		String correctReturned = new JSONObject()
				.put("temperature", 27.7)
				.put("humidity", 99.9)
				.put("CO2", 555)
				.put("light", 786)
				.put("date","")
				.put("device", "11dc3bc663ea64c5").toString();
		Assert.assertEquals(returned, correctReturned);
	}

	@Test
    public void noDataField() throws JSONException {
        String openWindowMessage = new JSONObject()
                .put("cmd", "011503E7022B0312").toString();
        String returned = UplinkMessageFormatter.receiveMessage(openWindowMessage);
        Assert.assertEquals(null, returned);

    }

}
