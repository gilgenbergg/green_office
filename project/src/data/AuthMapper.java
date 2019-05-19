package data;

import model.AuthData;

import java.sql.*;
import java.util.*;

public class AuthMapper extends DBinit{

    private static Set<AuthData> cash = new HashSet<>();

    public ArrayList<AuthData> allItems() throws SQLException {
        ResultSet rs = null;
        AuthData item = null;
        ArrayList<AuthData> usersInfo = new ArrayList<>();
        String select = "SELECT * FROM auth_data;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new AuthData(null, null, null);
            item.setuID(rs.getInt("auth_data_id"));
            item.setLogin(rs.getString("login"));
            item.setPassword(rs.getString("password"));
            usersInfo.add(item);
        }
        return usersInfo;
    }

    public boolean addAuthData(AuthData item) throws SQLException {
        String login = item.getLogin();
        String password = item.getPassword();
        ResultSet rs = null;
        String select = "INSERT INTO auth_data (login, password) VALUES ('"+login+"', '"+password+"');";
        PreparedStatement insertion = connection.prepareStatement(select, Statement.RETURN_GENERATED_KEYS);
        insertion.setString(1, login);
        insertion.setString(2, password);
        insertion.execute();
        rs = insertion.getGeneratedKeys();
        if (rs.next()) {
            Integer id = rs.getInt(1);
            item.setuID(id);
        }
        return true;
    }

    public void dropAuthData() throws SQLException {
        String script = "DROP TABLE auth_data";
        PreparedStatement statement = connection.prepareStatement(script);
        statement.execute();
    }

    public AuthData findItemByUID(Integer receivedUID) throws SQLException {
        //searching in cash
        for (AuthData item : cash) {
            if (item.getUID().equals(receivedUID))
                return item;
        }
        //if in cash item wasn`t found, searching in database
        ResultSet rs = null;
        String select = "SELECT * FROM auth_data WHERE auth_data_id='"+receivedUID+"'";
        PreparedStatement searchByID = connection.prepareStatement(select);
        rs = searchByID.executeQuery();
        if (!rs.next())
            return null;
        String login = rs.getString("login");
        String password = rs.getString("password");
        AuthData newAuthItem = new AuthData(receivedUID, login, password);
        cash.add(newAuthItem);
        return  newAuthItem;
    }

    public AuthData findItemByLogin(String login) throws SQLException {
        //searching in cash
        for (AuthData item : cash) {
            if (item.getLogin().equals(login))
                return item;
        }
        //if in cash item wasn`t found, searching in database
        ResultSet rs = null;
        String select = "SELECT * FROM auth_data WHERE login='"+login+"'";
        PreparedStatement searchByID = connection.prepareStatement(select);
        rs = searchByID.executeQuery();
        if (!rs.next())
            return null;
        int id = rs.getInt("auth_data_id");
        String password = rs.getString("password");
        AuthData newItem = new AuthData(id, login, password);
        cash.add(newItem);
        return newItem;
    }

    public void removeByLogin(String login) throws SQLException {
        AuthData toDelete = findItemByLogin(login);
        Integer id = toDelete.getUID();
        String script = "DELETE FROM auth_data WHERE auth_data_id = '"+id+"'";
        PreparedStatement removal = connection.prepareStatement(script);
        boolean rs = removal.execute();
        cash.remove(toDelete);
    }

}
