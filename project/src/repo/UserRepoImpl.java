package repo;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private static final UserRepoImpl USERS_REPO = new UserRepoImpl();
    private UserRepoImpl() {}
    public static UserRepoImpl getInstance() {
        return USERS_REPO;
    }

    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();

    private List<User> data = testUsers();

    public List<User> testUsers() {
        Integer uID;
        User.Role role;
        String firstName;
        String secondName;
        AuthData authData;
        ArrayList<User> allUsers = new ArrayList<>();
        User user1 = new User(
                uID = 1,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.admin
        );
        User user2 = new User(
                uID = 2,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.client
        );
        User user3 = new User(
                uID = 3,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.landscaper
        );
        User user4 = new User(
                uID = 4,
                firstName = "Arina",
                secondName = "Kalinina",
                role = User.Role.client
        );
        User user5 = new User(
                uID = 5,
                firstName = "Lusien",
                secondName = "Kornilova",
                role = User.Role.client
        );
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);
        return allUsers;
    }

    @Override
    public User findItemByUID(Integer receivedUID) {
        User found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getUID().equals(receivedUID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    public List<User> filterByRole(User.Role role) {
        List<User> filtered = new ArrayList<>();
        for (User item:
             data) {
            if (item.getRole().equals(role)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public List<Client> allClients() {
        List<User> usersThatAreClients = filterByRole(User.Role.client);
        List<Client> clients = new ArrayList<>();
        for (User item:
                usersThatAreClients) {
            Client client = new Client(plantsBase.filterPlantsByUserID(item.getUID()), item);
            clients.add(client);
        }
        return clients;
    }

    public List<Admin> allAdmins() {
        List<User> usersThatAreAdmins = filterByRole(User.Role.admin);
        List<Admin> admins = new ArrayList<>();
        for (User item:
                usersThatAreAdmins) {
            Admin admin = new Admin(item.getUID(), item);
            admins.add(admin);
        }
        return admins;
    }

    public List<Landscaper> allLandscapers() {
        List<User> usersThatAreLandscapers = filterByRole(User.Role.landscaper);
        List<Landscaper> landscapers = new ArrayList<>();
        for (User item:
                usersThatAreLandscapers) {
            Landscaper landscaper = new Landscaper(item);
            landscapers.add(landscaper);
        }
        return landscapers;
    }

    public Client getClientByUserID(Integer uID) {
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

    public Admin getAdminByUserID(Integer uID) {
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

    public Landscaper getLandscaperByUserID(Integer uID) {
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

    @Override
    public boolean add(User item) {
        return data.add(item);
    }

    @Override
    public void remove(User item) {
        data.remove(item);
    }
}
