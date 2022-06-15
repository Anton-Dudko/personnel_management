import java.sql.*;

public class Tables {
    private Connection connection;
    private Statement statement;

    public Tables(Connection connection, Statement statement){
        this.connection = connection;
        this.statement = statement;

        createTableUsers();
        createTableTasks();
        createTableHistoryTasks();
        createTableHistoryUsers();
        createTableFullInformationUsers();
    }

    public void createTableUsers(){
        if(tableExist(Constant.USERS_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.USERS_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " email VARCHAR (30), "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30), "
                        + " password VARCHAR (30), "
                        + " roll VARCHAR (30)"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.USERS_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableTasks(){

        if(tableExist(Constant.TASKS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Constant.TASKS_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " title VARCHAR (100), " +
                        " email VARCHAR (30)" +
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Constant.TASKS_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableHistoryTasks(){

        if(tableExist(Constant.HISTORY_TASKS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Constant.HISTORY_TASKS_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " title VARCHAR (100)" +
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Constant.HISTORY_TASKS_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableHistoryUsers(){
        if(tableExist(Constant.HISTORY_USERS_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.HISTORY_USERS_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " email VARCHAR (30), "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30)"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.HISTORY_USERS_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableFullInformationUsers(){
        if(tableExist(Constant.FULL_INFORMATION_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.FULL_INFORMATION_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " email VARCHAR (30), "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30), "
                        + " age INTEGER, "
                        + " university VARCHAR (30)"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.FULL_INFORMATION_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean tableExist(String name){
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, name, null);
            resultSet.last();
            return resultSet.getRow() <= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
