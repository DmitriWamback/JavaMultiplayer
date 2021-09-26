import engine.*;
import network.*;

public class App {

    static Engine engine;
    static Client client;
    public static void main(String[] args) throws Exception {
        
        client = new Client();
        client.start();

        engine = new Engine(1200, 800);
        engine.startLoop(client);
    }
}
