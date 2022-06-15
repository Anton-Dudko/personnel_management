import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerStart {
    public static LinkedList<Client> clientList = new LinkedList<>();
    public static int countClient = 0;

    private ServerSocket serverSocket;

    public ServerStart(){
        startServer();
        connectClient();
        closeServer();
    }

    public void startServer(){
        try {
            serverSocket = new ServerSocket(Constant.PORT);
            System.out.println("Сервер запущен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectClient(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            while(true){
                Socket socket = serverSocket.accept();
                try{
                    clientList.add(new Client(socket, dataBaseHandler));
                    System.out.println("Клиент подключился");
                } catch (IOException e){
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer(){
        try {
            serverSocket.close();
            System.out.println("Сервер остановлен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
