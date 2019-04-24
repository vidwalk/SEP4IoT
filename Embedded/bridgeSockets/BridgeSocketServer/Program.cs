using System;
using System.Threading;
using System.Net.Sockets;
using System.Net;
using System.Text;

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
                HandleClient client = new HandleClient();
                client.startClient(clientSocket, Convert.ToString(counter));
            }

        }
    }

    //Class to handle each client request separatly
    public class HandleClient
    {
        TcpClient clientSocket;
        string clientNo;
        public void startClient(TcpClient inClientSocket, string clientNo)
        {
            this.clientSocket = inClientSocket;
            this.clientNo = clientNo;
            Thread ctThread = new Thread(doChat);
            ctThread.Start();
        }
        private void doChat()
        {
            int requestCount = 0;
            byte[] bytesFrom = new byte[10025]; // bytes from the connection
            string dataFromClient = null;
            Byte[] sendBytes = null;
            string serverResponse = null;
            requestCount = 0;

            while ((true))
            {
                try
                {
                    requestCount = requestCount + 1;
                    NetworkStream networkStream = clientSocket.GetStream(); // initalize stream
                    networkStream.Read(bytesFrom, 0, (int)clientSocket.ReceiveBufferSize); // read bytes from the stream
                    dataFromClient = System.Text.Encoding.ASCII.GetString(bytesFrom); // convert bytes to string
                    Console.WriteLine(" >> " + "From client-" + clientNo + dataFromClient);

                    networkStream.Write(sendBytes, 0, sendBytes.Length);
                    networkStream.Flush();
                    Console.WriteLine(" >> " + serverResponse);
                }
                catch (Exception ex)
                {
                    Console.WriteLine(" >> " + ex.ToString());
                }
            }
        }
    }
}