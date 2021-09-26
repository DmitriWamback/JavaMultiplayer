package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import engine.Engine;
import engine.math.Vector2;
import engine.entities.*;

public class Client extends Thread {

    Socket client;
    public static int id;

    public static int MAX_PLAYERS = 10;

    public static NPC[] NPCs = new NPC[MAX_PLAYERS];
    public static Vector2[] positions = new Vector2[MAX_PLAYERS];

    public Client() {
        try {
            client = new Socket("ip", 6060);
        }
        catch (IOException e) {}
    }

    public void closeClient() {
        try {
            sendToServer("[DISCONNECTED]");
        }
        catch (Exception e) {}
    }
    
    public void sendToServer(String data) throws IOException {

        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
        writer.println(data);
    }

    public void run() {

        System.out.println("Running");

        while (!Engine.shouldClose) {
            // reads from server

            InputStream in;
            try {

                in = client.getInputStream();
                
                byte[] buffer = new byte[2048];
                int status;
                while ((status = in.read(buffer)) != -1) {

                    String out = new String(buffer, 0, status);
                    if (out.startsWith("ID:")) {
                        String _id = out.split("ID: ")[1].split("\n")[0];
                        System.out.println(_id);
                        id = Integer.parseInt(_id);
                    }

                    if (out.startsWith("[DISCONNECT]")) {
                        client.close();
                        System.exit(-1);
                    }

                    if (out.startsWith("DISCONNECTION:")) {
                        int _id = Integer.parseInt(out.split("DISCONNECTION: ")[1].split("\n")[0]);
                        NPCs[_id].setValid(false);
                    }

                    if (out.startsWith("VALS")) {
                        String x_string;
                        String y_string;
                        String id_string;

                        float x;
                        float y;
                        int _id;

                        String vals = out.split("VALS")[1];
                        x_string = vals.split(" ")[0];
                        y_string = vals.split(" ")[1];
                        id_string = vals.split(" ")[2];
                        x = Float.parseFloat(x_string);
                        y = Float.parseFloat(y_string);
                        _id = Integer.parseInt(id_string.split("\n")[0]);
                        
                        try {
                            positions[_id] = new Vector2(x, y);
                            NPCs[_id].setValid(true);
                        }
                        catch (Exception e) {}
                    }

                    System.out.println(out);
                    System.out.flush();
                }
            } 
            catch (IOException e) {}
        }
    }
}
