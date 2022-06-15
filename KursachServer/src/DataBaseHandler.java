import java.sql.*;
import java.util.LinkedList;

public class DataBaseHandler {
    private Connection connection;
    private Statement statement;

    public DataBaseHandler(){
        connectionToDB();
        createTable(connection, statement);
    }

    public void connectionToDB(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(Constant.HOSTNAME_DB + Constant.NAME_DB,
                    Constant.USERNAME_DB, Constant.PASSWORD_DB);
            statement = connection.createStatement();
            System.out.println("База данных подключена !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection, Statement statement){
        new Tables(connection, statement);
    }

    public boolean addUser(String emailDb, String passwordDb, String rollDb,String name,String surname){
        if(!checkUser(emailDb)) {
            return false;
        }

        try {
            String query = " insert into users (email, password,roll,name,surname )"
                    + " values ( ?, ?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, emailDb);
            preparedStmt.setString (2, passwordDb);
            preparedStmt.setString (3, rollDb);
            preparedStmt.setString (4, name);
            preparedStmt.setString (5, surname);


            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean addHistoryUser(String email, String name,String surname){

        try {
            String query = " insert into history_users (email,name,surname )"
                    + " values ( ?, ?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, email);
            preparedStmt.setString (2, name);
            preparedStmt.setString (3, surname);


            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    private boolean checkUser(String emailUserDb){
        String query = "SELECT " + Constant.ID  + " FROM " + Constant.USERS_TABLE +
                " WHERE " + Constant.EMAIL + " = " + "'" + emailUserDb + "'";
        ResultSet rs = null;
        int idInDb=0;
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                idInDb =  rs.getInt(Constant.ID);
            }
            if(idInDb==0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String authUser(String email,String password) {
        String currentUser="";
        try {
            String query = "SELECT " + Constant.EMAIL + "," + Constant.PASSWORD+","+Constant.ID+","+Constant.ROLL + " FROM " + Constant.USERS_TABLE +
                    " WHERE " + Constant.EMAIL + " = " + "'" + email + "'" + " AND " + Constant.PASSWORD + " = " + "'" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (!rs.getString(Constant.EMAIL).equals("") &&
                        !rs.getString(Constant.PASSWORD).equals("")) {
                    currentUser+=rs.getString(Constant.ID)+" ";
                    currentUser+=rs.getString(Constant.ROLL);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(currentUser.equals("")) {
            return "false";
        }
        else {
            return currentUser;
        }
    }

    public LinkedList<String> showAllUsers() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.ID+" , " + Constant.EMAIL+" , "+Constant.PASSWORD+" , "
                +Constant.ROLL+ " FROM " + Constant.USERS_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.ID)+" ";
                contribution+=rs.getString(Constant.EMAIL)+" ";
                contribution+=rs.getString(Constant.PASSWORD)+" ";
                contribution+=rs.getString(Constant.ROLL)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public LinkedList<String> showAllUsersForClient() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.NAME_USER+" , " + Constant.EMAIL+" , "+Constant.SURNAME_USER
                + " FROM " + Constant.USERS_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.NAME_USER)+" ";
                contribution+=rs.getString(Constant.EMAIL)+" ";
                contribution+=rs.getString(Constant.SURNAME_USER)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public void redactionUser(int idUser, String email,String password,int roll) {
        String query = "update "+Constant.USERS_TABLE+" SET   email =?, password =?, roll=? WHERE " + Constant.ID + " = " + "'" + idUser + "'";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, email);
            preparedStmt.setString(2, password);
            preparedStmt.setInt   (3, roll);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(int id)  {

        String selectSQL = "DELETE FROM "+Constant.USERS_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String currentUserData(int id){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = this.connection;
            statement= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String currentUser="";
        try {
            String query = "SELECT " + Constant.NAME_USER + " , " + Constant.SURNAME_USER+" , "+Constant.PASSWORD +" , "+Constant.EMAIL+" , "+Constant.ROLL+ " FROM " + Constant.USERS_TABLE +
                    " WHERE " + Constant.ID + " = " + "'" + id + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                currentUser+=rs.getString(Constant.NAME_USER)+" ";
                currentUser+=rs.getString(Constant.SURNAME_USER)+" ";
                currentUser+=rs.getString(Constant.PASSWORD)+" ";
                currentUser+=rs.getString(Constant.EMAIL)+" ";
                currentUser+=rs.getString(Constant.ROLL)+" ";
                currentUser+=id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    public  void updateUser(int id,String name ,String surname,String email ,String password)  {

        String query = "UPDATE users SET name  = ?, surname = ? ,email = ?,password = ? WHERE id = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, name);
            preparedStmt.setString(2, surname);
            preparedStmt.setString(3, email);
            preparedStmt.setString(4, password);
            preparedStmt.setInt(5, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public boolean addTaskInDb(String title, String email){

        try {
            String query = " insert into " +Constant.TASKS_TABLE + " (email, title)"
                    + " values ( ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, title);
            preparedStmt.setString (2, email);

            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.TASKS_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean addTaskInHistory(String title){
        try {
            String query = " insert into " + Constant.HISTORY_TASKS_TABLE + " ( title)"
                    + " values ( ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, title);

            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.HISTORY_TASKS_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public LinkedList<String> showAllTasks() {
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+ Constant.ID_TASK + " , " + Constant.TASK_EMAIL + " , " + Constant.TASK_TITLE + " FROM "
                + Constant.TASKS_TABLE ;
        ResultSet rs = null;
        String task="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                task+=rs.getString(Constant.ID_TASK)+" ";
                task+=rs.getString(Constant.TASK_EMAIL)+" ";
                task+=rs.getString(Constant.TASK_TITLE) + " ";
                list.add(task);
                task="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showAllTaskName() {
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT " + Constant.TASK_TITLE + " FROM "
                + Constant.TASKS_TABLE ;
        ResultSet rs = null;
        String task="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                task+=rs.getString(Constant.TASK_TITLE) + " ";
                list.add(task);
                task="";
            }
            System.out.println(list);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void deleteTask(int id)  {

        String selectSQL = "DELETE FROM "+Constant.TASKS_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public LinkedList<String> showTaskByEmail(String email){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.TASK_TITLE+" FROM " + Constant.TASKS_TABLE +
                " WHERE " + Constant.TASK_EMAIL + " = " + "'" + email + "'";
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.TASK_TITLE);
                list.add(contribution);

                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showHistoryUsers() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.NAME_USER+" , " + Constant.EMAIL+" , "+Constant.SURNAME_USER
                + " FROM " + Constant.HISTORY_USERS_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.NAME_USER)+" ";
                contribution+=rs.getString(Constant.EMAIL)+" ";
                contribution+=rs.getString(Constant.SURNAME_USER)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public LinkedList<String> showHistoryTasks() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.HISTORY_TASK_TITLE
                + " FROM " + Constant.HISTORY_TASKS_TABLE;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.HISTORY_TASK_TITLE)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

}
