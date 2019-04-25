using System;
using System.Threading;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;

namespace BridgeSocketServer
{
    class Program
    {
        static void Main(string[] args)
        {

            TcpListener serverSocket = new TcpListener(IPAddress.Parse("127.0.0.1"),8888);
            TcpClient clientSocket = new TcpClient();
            var uri = "mongodb+srv://groupZ1:groupZ1@iotzuperteam-no7vb.mongodb.net/admin?retryWrites=true";
            IDatabaseHelper helper = new MongoDBHelper(uri);
            int counter = 0;

            serverSocket.Start();
            Console.WriteLine("Server Started on port 8888");

            counter = 0;
            while (true)
            {
                counter += 1;
                clientSocket = serverSocket.AcceptTcpClient(); // wait for connection and accept it when reached
                Console.WriteLine("Client with number :" + Convert.ToString(counter) + "has benn connected!");
                HandleClient client = new HandleClient(helper);
                client.StartClient(clientSocket, Convert.ToString(counter));
                
            }

        }
    }

    //Class to handle each client request separatly
    public class HandleClient
    {
        private TcpClient clientSocket;
        private string clientNo;
        private readonly IDatabaseHelper _helper;
        public HandleClient(IDatabaseHelper helper)
        {
            _helper = helper;
        }

        public void StartClient(TcpClient inClientSocket, string clientNo)
        {
            this.clientSocket = inClientSocket;
            this.clientNo = clientNo;
            Thread ctThread = new Thread(HandleRequest);
            ctThread.Start();
        }
        private void HandleRequest()
        {
            byte[] bytesFrom = new byte[1024]; // bytes from the connection
             NetworkStream networkStream = clientSocket.GetStream(); // initalize stream
            if (networkStream.CanRead)
            {
                byte[] myReadBuffer = new byte[1024];
                StringBuilder completeMessage = new StringBuilder();
                int numberOfBytesRead = 0;
                
                do // in case the incoming message is larger than the buffer size
                {
                    numberOfBytesRead = networkStream.Read(myReadBuffer, 0, myReadBuffer.Length);

                    completeMessage.AppendFormat("{0}", Encoding.ASCII.GetString(myReadBuffer, 0, numberOfBytesRead));

                }
                while (networkStream.DataAvailable);


                // Print out the received message to the console.
                Console.WriteLine("You received the following message : " +
                                             completeMessage);
                RequestHandler r = new RequestHandler(_helper);
                r.HandleRequest(RequestHandler.OperationNumber.SEND_READINGS_TO_DB, completeMessage.ToString());

            }

        }
    }
}