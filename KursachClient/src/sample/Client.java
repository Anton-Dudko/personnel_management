package sample;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static ConnectionToServer connectionToServer;

    public Client(){
        connectToServer();
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket(Constants.HOST, Constants.PORT);
            connectionToServer = new ConnectionToServer(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
