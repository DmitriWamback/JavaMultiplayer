using System;
using System.Net.Sockets;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Text;
using System.Net;
using System.Text.Json;

namespace socket_server
{
    class Program {
        Socket server;

        List<Socket> clients = new List<Socket>();

        bool[] occupiedPlaces = new bool[10];

        void ClientHandler(Socket client) {
            // reads from client

            int id = -1;
            for (int i = 0; i < occupiedPlaces.Length; i++) {
                if (occupiedPlaces[i] == false) {
                    occupiedPlaces[i] = true;
                    id = i;
                    break;
                }
            }

            if (id == -1) {
                SendToClient(client, "[DISCONNECT]");
                return;
            }

            SendToClient(client, $"ID: {id}\n");

            do {
                byte[] bytes = new byte[2048];

                int i = client.Receive(bytes);
                string command = Encoding.UTF8.GetString(bytes);

                Console.WriteLine(command);
                if (command.StartsWith("VALS")) {
                    SendToAllClients(client, command);
                }
                else if (command.StartsWith("[DISCONNECTED]")) {
                    SendToAllClients(client, "DISCONNECTION: " + id);
                    client.Close();
                    occupiedPlaces[id] = false;
                    break;
                }
            }
            while (client.Connected);

            clients.Remove(client);
        }

        void SendToClient(Socket receiver, string message) {
            byte[] msg = Encoding.UTF8.GetBytes(message);
            receiver.Send(msg);
        }

        void SendToAllClients(Socket sender, string jsonData) {
            foreach(Socket cl in clients) {
                if (cl != sender) {
                    byte[] messageBytes = Encoding.UTF8.GetBytes(jsonData);
                    cl.Send(messageBytes);
                }
            }
        }

        public void Run() {

            for (int i = 0; i < occupiedPlaces.Length; i++) {
                occupiedPlaces[i] = false;
            }

            server = new Socket(AddressFamily.InterNetwork,
                                SocketType.Stream,
                                ProtocolType.Tcp);

            IPEndPoint ipEndPoint = new IPEndPoint(IPAddress.Parse("ip"), 6060);
            server.Bind(ipEndPoint);
            server.Listen(5);

            do {
                Console.WriteLine("Waiting");
                Socket client = server.Accept();
                Console.WriteLine("Connection");
                clients.Add(client);
                Task.Run(() => ClientHandler(client));
            }
            while (true);
        }

        static void Main(string[] args) {
            new Program().Run();
        }
    }
}
