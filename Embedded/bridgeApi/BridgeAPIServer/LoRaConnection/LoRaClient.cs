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
                };

                ws.OnError += (sender, e) => {
                    Console.WriteLine("An error occured.");
                };

                ws.OnClose += (sender, e) => {
                    Console.WriteLine("Connection closed");
                };

                ws.Connect();
                ws.Close();
            }

        }
    }
}
