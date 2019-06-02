package data;

import model.*;
import model.User.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("ALL")
public class UsersMapper extends DBinit {

    private static Set<User> cash = new HashSet<>();
    private Connection connection;
    private static UsersMapper USER_TABLE;

    static {
        try {
            USER_TABLE = new UsersMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    AuthMapper authMapper = new AuthMapper();
    PlantsMapper plantsMapper = new PlantsMapper();

    public UsersMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    //public static UsersMapper getInstance() {
      //  return USER_TABLE;
    //}

    public ArrayList<User> allItems() throws SQLException {
        ResultSet rs = null;
        User item = null;
        ArrayList<User> usersInfo = new ArrayList<>();
        String select = "SELECT * FROM user_info;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new User(null, null, null, null);
            item.setuID(rs.getInt("user_id"));
            item.setFirstName("firstName");
            item.setSecondName("secondName");
            String roleFromBase = rs.getString("role");
            item.setRole(parseRole(roleFromBase));
            AuthData authData = authMapper.findItemByUID(rs.getInt("auth_data_id"));
            item.setLogin(authData.getLogin());
            item.setPassword(authData.getPassword());

            usersInfo.add(item);
        }
        return usersInfo;
    }

    public Role parseRole(String roleFromBase) {
        Role parsedRole = null;
        switch (roleFromBase) {
            case "client":
                parsedRole = Role.client;
                break;
            case "admin":
                parsedRole = Role.admin;
                break;
            case "landscaper":
                parsedRole = Role.landscaper;
                break;
        }
        return parsedRole;
    }

    public User findItemByUID(Integer receivedUID) throws SQLException {
        for (User item : cash) {
            if (item.getUID().equals(receivedUID))
                return item;
        }
        //if in cash item wasn`t found, searching in database
        ResultSet rs = null;
        String select = "SELECT * FROM user_info WHERE user_id='"+receivedUID+"'";
        PreparedStatement searchByID = connection.prepareStatement(select);
        rs = searchByID.executeQuery();
        if (!rs.next())
            return null;
        String firstName = rs.getString("first_name");
        String secondName = rs.getString("second_name");
        String roleFromBase = rs.getString("role");
        Role role = parseRole(roleFromBase);
        User newUserItem = new User(receivedUID, firstName, secondName, role);
        cash.add(newUserItem);
        return newUserItem;
    }

    private ArrayList<User> filterByRole(Role role) throws SQLException {
        ResultSet rs = null;
        String parsedRole = role.toString();
        String select = "SELECT * FROM user_info WHERE role='"+parsedRole+"'";
        PreparedStatement search = connection.prepareStatement(select);
        rs = search.executeQuery();
        if (!rs.next())
            return null;
        ArrayList<User> allItems = allItems();
        ArrayList<User> filtered = new ArrayList<>();
        for (User item:
                allItems) {
            if (item.getRole().equals(role)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public ArrayList allClients() throws SQLException, ClassNotFoundException {
        ArrayList<User> filtered = filterByRole(Role.client);
        ArrayList clients = new ArrayList();
        assert filtered != null;
        for (User item: filtered) {
            Client client = new Client(plantsMapper.filterPlantsByUserID(item.getUID()), item);
            clients.add(client);
        }
        return clients;
    }

    public ArrayList<Admin> allAdmins() throws SQLException, ClassNotFoundException {
        ArrayList<User> filtered = filterByRole(Role.admin);
        ArrayList admins = new ArrayList();
        assert filtered != null;
        for (User item: filtered) {
            Admin admin = new Admin(item.getUID(), item);
            admins.add(admin);
        }
        return admins;
    }

    public List<Landscaper> allLandscapers() throws SQLException, ClassNotFoundException {
        ArrayList<User> filtered = filterByRole(Role.landscaper);
        ArrayList landscapers = new ArrayList();
        assert filtered != null;
        for (User item: filtered) {
            Landscaper landscaper = new Landscaper(item);
            landscapers.add(landscaper);
        }
        return landscapers;
    }

    public Client getClientByUserID(Integer uID) throws SQLException, ClassNotFoundException {
        Client found = null;
        List<Client> allClients = allClients();
        for (Client item:
                allClients) {
            if (item.getUID().equals(uID)) {
                found = item;
                break;
            }
        }
        return found;
    }

    public Admin getAdminByUserID(Integer uID) throws SQLException, ClassNotFoundException {
        Admin found = null;
        List<Admin> allAdmins = allAdmins();
        for (Admin item:
                allAdmins) {
            if (item.getUID().equals(uID)) {
                found = item;
                break;
            }
        }
        return found;
    }

    public Landscaper getLandscaperByUserID(Integer uID) throws SQLException, ClassNotFoundException {
        Landscaper found = null;
        List<Landscaper> allLandcapers = allLandscapers();
        for (Landscaper item:
                allLandcapers) {
            if (item.getUID().equals(uID)) {
                found = item;
                break;
            }
        }
        return found;
    }

    public boolean addUser(User item) throws SQLException {
        ResultSet rs = null;
        String login = item.getLogin();
        String password = item.getPassword();
        AuthData authInfo = new AuthData(null, login, password);
        authMapper.addAuthData(authInfo);
        String selectToGetAuthDataID = "SELECT auth_data_id from auth_data WHERE login = '"+login+"'";
        PreparedStatement authdata = connection.prepareStatement(selectToGetAuthDataID, Statement.RETURN_GENERATED_KEYS);
        rs = authdata.executeQuery();

        Role role = item.getRole();
        String parsedForDbRole = "";
        if (role == Role.client) {
            parsedForDbRole = "client";
        }
        else if (role == Role.admin) {
            parsedForDbRole = "admin";
        }
        else if (role == Role.landscaper) {
            parsedForDbRole = "landscaper";
        }
        String firstName = item.getFirstName();
        String secondName = item.getSecondName();
        Integer authDataID = rs.getInt(1);

        String request = "INSERT INTO user_info (role, firstName, secondName, auth_data_id) " + "VALUES (?, ?, ?, ?)";
        PreparedStatement res = connection.prepareStatement(request);
        res.setString(1, parsedForDbRole);
        res.setString(2, firstName);
        res.setString(3, secondName);
        res.setInt(4, authDataID);
        res.executeUpdate();
        rs = res.getGeneratedKeys();
        if (rs.next()) {
            Integer id = rs.getInt(1);
            item.setuID(id);
        }
        return true;
    }
}
