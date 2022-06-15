import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private DataBaseHandler dataBaseHandler;
    private int currentCount = ServerStart.countClient++;
    private Boolean checkAuth =false;

    public Client(Socket socket, DataBaseHandler dataBaseHandler) throws IOException {
        this.socket = socket;
        this.dataBaseHandler = dataBaseHandler;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        start();
    }

    @Override
    public void run(){
        try {
            workWithPersonal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void workWithPersonal() throws IOException{
        while(true){
            switch (in.readLine()){
                case "registration":
                    registrationUser();
                    break;
                case "auth":
                    checkAuth();
                    break;
                case "showAllUser":
                    showAllUsers();
                    break;
                case "redactionUser":
                    redactionUser();
                    break;
                case "showAllUserForClient":
                    showAllUsersForClient();
                    break;
                case "deleteUser":
                    deleteUser();
                    break;
                case "currentUserData":
                    getFullParamsUser();
                    break;
                case "updateUserData":
                    updateUserData();
                    break;
                case "addTask":
                    addTaskInDb();
                    break;
                case "showAllTasks":
                    showAllTasks();
                    break;
                case "deleteTask":
                    deleteTask();
                    break;
                case "showAllTasksForClient":
                    showAllTasksForClient();
                    break;
                case "showHistoryUsers":
                    showHistoryUsers();
                    break;
                case "showHistoryTasks":
                    showHistoryTasks();
                    break;

            }
        }
    }

    private void registrationUser(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            if(dataBaseHandler.addUser(subStr[0], subStr[1],"0",subStr[2],subStr[3]) &&
                    dataBaseHandler.addHistoryUser(subStr[0], subStr[2],subStr[3])) {
                ServerStart.clientList.get(currentCount);
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAuth(){
        try {
            String[] subStr = in.readLine().split(" ");
            String check= dataBaseHandler.authUser(subStr[0], subStr[1]);
            if (!check.equals("false")) {
                checkAuth = true;
                ServerStart.clientList.get(currentCount).send("1");
                ServerStart.clientList.get(currentCount).send(check);
            } else {
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllUsers(){
        LinkedList<String> list = dataBaseHandler.showAllUsers();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void showAllUsersForClient(){
        LinkedList<String> list = dataBaseHandler.showAllUsersForClient();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void redactionUser(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            dataBaseHandler.redactionUser(Integer.parseInt(subStr[0]),subStr[1],subStr[2], Integer.parseInt(subStr[3]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(){
        try {
            dataBaseHandler.deleteUser(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFullParamsUser(){
        try {

            ServerStart.clientList.get(currentCount).send(dataBaseHandler.currentUserData(Integer.parseInt(in.readLine())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserData(){
        String[] str;

        try {
            str=in.readLine().split(" ");
            dataBaseHandler.updateUser(Integer.parseInt(str[0]),str[1],str[2],str[3],str[4]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTaskInDb(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            if(validateInput(subStr[0],subStr[1])) {
                if (dataBaseHandler.addTaskInDb(subStr[0], subStr[1]) && dataBaseHandler.addTaskInHistory(subStr[1])) {
                    ServerStart.clientList.get(currentCount).send("1");
                } else {
                    ServerStart.clientList.get(currentCount).send("0");
                }
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllTasks(){
        LinkedList<String> list = dataBaseHandler.showAllTasks();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){

            ServerStart.clientList.get(currentCount).send(s);

        }
    }

    private void deleteTask(){
        try {
            dataBaseHandler.deleteTask(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllTasksForClient() throws IOException {
        LinkedList<String> list = dataBaseHandler.showTaskByEmail(in.readLine());
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void showHistoryUsers() throws IOException {
        LinkedList<String> list = dataBaseHandler.showHistoryUsers();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }
    private void showHistoryTasks() throws IOException {
        LinkedList<String> list = dataBaseHandler.showHistoryTasks();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private boolean validateInput(String email, String title){
        LinkedList<String> aaa = dataBaseHandler.showAllTaskName();

        if((!email.equals("") || !title.equals("")) && !aaa.contains(title)) {
            return true;
        }else {
            return false;
        }

    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }

}
