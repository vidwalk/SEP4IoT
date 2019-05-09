using WebSocketSharp;
using System;

namespace BridgeAPIServer.LoRaConnection
{
    public class LoRaClient {
      


        public void Run_Actuator()
        {
            using (var ws = new WebSocket("wss://iotnet.teracom.dk/app?token=vnoRfwAAABFpb3RuZXQudGVyYWNvbS5ka_ct-QZ1DMiwgA-TLGfiomI="))
            {
                ws.OnOpen += (sender, e) =>
                {
                    ws.Send("Open Window");
                    Console.WriteLine("Connection established.");
                };

                ws.OnError += (sender, e) => {
                    Console.WriteLine("An error occured.");
                };

                ws.OnClose += (sender, e) => {
                    Console.WriteLine("Connection closed");
                };

                ws.OnMessage += (sender, e) =>
                {
                    Console.WriteLine("Message received.");
                    Console.WriteLine($"Message received, bytes: {e.RawData}");
                };

                ws.Connect();
            }

        }
    }
}
