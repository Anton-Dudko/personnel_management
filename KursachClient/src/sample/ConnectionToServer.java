package sample;

import sample.model.Task;
import sample.model.User;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ConnectionToServer {
    private BufferedReader in;
    private BufferedWriter out;

    public ConnectionToServer(Socket socket){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserInDb() {
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAddTaskInDb(){
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getIdAndRoll(String email){
        try {

            String[] subStr = in.readLine().split(" ");
            User.currentUser= new User(email,Integer.parseInt(subStr[0]),Integer.parseInt(subStr[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<User> showAllUsers() throws IOException {
        LinkedList<User> lisUsers = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringUsers(in.readLine(),lisUsers);
        }
        return lisUsers;
    }

    private void parseStringUsers(String readLine, LinkedList<User> lisUsers) {
        String[] subStr;
        subStr = readLine.split(" ");
        lisUsers.add(new User(Integer.parseInt(subStr[0]),subStr[1],subStr[2],Integer.parseInt(subStr[3])));
    }

    public LinkedList<User> showAllUsersForClient() throws IOException {
        LinkedList<User> lisUsers = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringUsersForClient(in.readLine(),lisUsers);
        }
        return lisUsers;
    }

    private void parseStringUsersForClient(String readLine, LinkedList<User> lisUsers) {
        String[] subStr;
        subStr = readLine.split(" ");
        lisUsers.add(new User(subStr[0],subStr[1],subStr[2]));
    }

    public void initUserFullParams() throws IOException {
        String[] subStr;
        subStr = in.readLine().split(" ");
        User.currentUser.setName(subStr[0]);
        User.currentUser.setSurname(subStr[1]);
        User.currentUser.setPassword(subStr[2]);
    }

    public LinkedList<Task> showAllTasks() throws IOException {
        LinkedList<Task> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringInTasksAll(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseStringInTasksAll(String str, LinkedList<Task> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Task(Integer.parseInt(subStr[0]), subStr[1], subStr[2]));
    }

    public LinkedList<Task> showAllTasksForClient() throws IOException {
        LinkedList<Task> listContribution = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringInTasksAllClient(in.readLine(),listContribution);
        }
        return listContribution;
    }

    private void parseStringInTasksAllClient(String str, LinkedList<Task> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Task(subStr[0]));
    }

    public LinkedList<Task> showHistoryTasks() throws IOException {
        LinkedList<Task> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseHistory(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseHistory(String str, LinkedList<Task> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Task(subStr[0]));
    }

}
