package connection.spring.wsConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class LoRaClient implements WebSocket.Listener{

    private WebSocket webSocket;
    private MongoDBHelper dbHelper;
    private String openWindowMessage;

    @Autowired
    public LoRaClient(MongoDBHelper helper) {
        openWindowMessage = new JSONObject()
                .put("cmd", "tx")
                .put("EUI", "11dc3bc663ea64c5")
                .put("port", 1)
                .put("data", "42").toString();
        dbHelper = helper;
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("wss://iotnet.teracom.dk/app?token=vnoRfwAAABFpb3RuZXQudGVyYWNvbS5ka_ct-QZ1DMiwgA-TLGfiomI="), this);
    }

    //onOpen()
    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);
        System.out.println("WebSocket Listener has been opened for requests.");
        this.webSocket = webSocket;
    }
    //onError()
    public void onError​(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    };
    //onClose()
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return null; //new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    };
    //onPing()
    public CompletionStage<?> onPing​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return null; // new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    };
    //onPong()
    public CompletionStage<?> onPong​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return null; // new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
    };
    //onText()
    public CompletionStage<?> onText​(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("A message was received:");
        System.out.println(data);
        JSONObject received = new JSONObject(data);
        String cmd = received.getString("cmd");
        if(!cmd.equals("tx")) {
            webSocket.request(1);
            String cleanMessage = UplinkMessageFormatter.receiveMessage(data);
            //dbHelper.send(cleanMessage);
        } else {
            System.out.println("The message received is an echo from LoRa.");
        }
        
        return null; // new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
    };
    //sendText
    public CompletionStage<WebSocket> sendText() {
        System.out.println("Hello from send message");
        return webSocket.sendText(openWindowMessage, true);
    };
}