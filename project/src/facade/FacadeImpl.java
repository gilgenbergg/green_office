package facade;

import data.*;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacadeImpl implements Facade {
    public static AuthMapper authBase;
    public static UsersMapper userBase;
    public static CReqsMapper creqsBase;
    public static PReqsMapper preqsBase;
    public static PlantsMapper plantsBase;
    public static RequiredResMapper requiresResBase;
    public static ResourcesMapper resourcesBase;

    public FacadeImpl() throws SQLException, ClassNotFoundException {
        try {
            if (authBase == null) authBase = new AuthMapper();
            if (resourcesBase == null) resourcesBase = new ResourcesMapper();
            if (plantsBase == null) plantsBase = new PlantsMapper(resourcesBase);
            if (userBase == null) userBase = new UsersMapper(authBase, plantsBase);
            if (creqsBase == null) creqsBase = new CReqsMapper(plantsBase, userBase);
            if (requiresResBase == null) requiresResBase = new RequiredResMapper();
            if (preqsBase == null) preqsBase = new PReqsMapper(plantsBase, userBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void authorize(User user, String login, String password) throws Exception {
        user.signIn(user, login, password);
    }

    @Override
    public List<ClientRequest> filterByType(ClientRequest.Type type) throws SQLException {
        return  creqsBase.filterByType(type);
    }

    @Override
    public User findItemByUID(Integer uid) throws SQLException {
        return userBase.findItemByUID(uid);
    }

    @Override
    public List<ClientRequest> filterByAdminID(Integer uid) throws SQLException {
        return creqsBase.filterByAdminID(uid);
    }

    @Override
    public List<PurchaseRequest> filterByUserID(Integer uid) throws SQLException {
        return preqsBase.filterByUserID(uid);
    }

    @Override
    public AuthData findItemByLogin(String text) throws SQLException {
        return authBase.findItemByLogin(text);
    }

    @Override
    public User findItemByAuthID(Integer authDataID) throws SQLException {
        return userBase.findItemByAuthID(authDataID);
    }

    @Override
    public Client getClientByUserID(Integer uid) throws SQLException, ClassNotFoundException {
        return userBase.getClientByUserID(uid);
    }

    @Override
    public Admin getAdminByUserID(Integer uid) throws SQLException, ClassNotFoundException {
        return userBase.getAdminByUserID(uid);
    }

    @Override
    public Landscaper getLandscaperByUserID(Integer uid) throws SQLException, ClassNotFoundException {
        return userBase.getLandscaperByUserID(uid);
    }

    @Override
    public void updatePReqStatus(Integer preqID, PurchaseRequest.Status approved) throws SQLException {
        preqsBase.updateStatus(preqID, approved);
    }

    @Override
    public PurchaseRequest findPReqByID(Integer preqID) throws SQLException {
        return preqsBase.findItemByID(preqID);
    }

    @Override
    public ArrayList<String> getTypesByPlantID(Integer plantID) throws SQLException {
        return resourcesBase.getTypesByPlantID(plantID);
    }

    @Override
    public Plant findItemByPlantID(Integer plantID) throws SQLException {
        return plantsBase.findItemByPlantID(plantID);
    }

    @Override
    public ArrayList<String> findRequiredByPlantType(String plantType) throws SQLException {
        return requiresResBase.findRequiredByPlantType(plantType);
    }

    @Override
    public List<Plant> filterPlantsByUserID(Integer clientID) throws SQLException {
        return plantsBase.filterPlantsByUserID(clientID);
    }

    @Override
    public List<Landscaper> allLandscapers() throws SQLException, ClassNotFoundException {
        return userBase.allLandscapers();
    }

    @Override
    public ClientRequest findCReqByID(Integer cReqID) throws SQLException {
        return creqsBase.findItemByID(cReqID);
    }

    @Override
    public void updatePlant(int parseInt, ClientRequest cReqByID) throws SQLException {
        creqsBase.updatePlant(parseInt, cReqByID);
    }

    @Override
    public void updateCReqLandscaperID(Integer cReqIDSetup, int parseInt) throws SQLException {
        creqsBase.updateLandscaperID(cReqIDSetup, parseInt);
    }

    @Override
    public ClientRequest.Status parseCReqStatusFromDB(String chosenStatus) {
        return creqsBase.parseStatusFromDB(chosenStatus);
    }

    @Override
    public void updateCReqStatus(Integer cReqIDSetup, ClientRequest.Status parseCReqStatusFromDB) throws SQLException {
        creqsBase.updateStatus(cReqIDSetup, parseCReqStatusFromDB);
    }

    @Override
    public ClientRequest.Type parseCReqTypeFromDB(String chosenType) {
        return creqsBase.parseTypeFromDB(chosenType);
    }

    @Override
    public void updateCReqType(Integer cReqIDSetup, ClientRequest.Type parseCReqTypeFromDB) throws SQLException {
        creqsBase.updateType(cReqIDSetup, parseCReqTypeFromDB);
    }

    @Override
    public List<ClientRequest> filterCReqsByUID(Integer uid) throws SQLException {
        return creqsBase.filterByUID(uid);
    }

    @Override
    public void setDateOfLastVisit(Integer assignedPlant, String lastInspection) throws SQLException {
        plantsBase.setDateOfLastVisit(assignedPlant, lastInspection);
    }

    @Override
    public void setDateOfNextVisit(Integer assignedPlant, String nextInspection) throws SQLException {
        plantsBase.setDateOfNextVisit(assignedPlant, nextInspection);
    }

    @Override
    public List<ClientRequest> filterCReqsByLandscaperID(Integer uid) throws SQLException {
        return creqsBase.filterByLandscaperID(uid);
    }

    @Override
    public List<PurchaseRequest> filterPReqsByLandscaperID(Integer uid) throws SQLException {
        return preqsBase.filterByLandscaperID(uid);
    }

    @Override
    public ClientRequest addCReq(ClientRequest creq) throws SQLException, ClassNotFoundException {
        return creqsBase.addCReq(creq);
    }

    @Override
    public List<Plant> allPlants() throws SQLException {
        return plantsBase.allPlants();
    }

    @Override
    public List<ClientRequest> filterCReqsByPlantID(Integer assignedPlantID) throws SQLException {
        return creqsBase.filterByPlantID(assignedPlantID);
    }

    @Override
    public Plant addNewPlant(Plant plant) throws SQLException {
        return plantsBase.addNewPlant(plant);
    }

    @Override
    public List<Client> allClients() throws SQLException, ClassNotFoundException {
        return userBase.allClients();
    }

    @Override
    public PurchaseRequest.Status parsePReqStatusFromDB(String chosenStatus) {
        return preqsBase.parseStatusFromDB(chosenStatus);
    }

    @Override
    public Integer addPReq(PurchaseRequest pReq) throws SQLException, ClassNotFoundException {
        return preqsBase.addPReq(pReq);
    }

    @Override
    public Resource addNewResourceItem(Resource newResource) throws SQLException {
        return resourcesBase.addNewItem(newResource);
    }

    @Override
    public User.Role parseRole(String chosenRole) {
        return userBase.parseRole(chosenRole);
    }

    @Override
    public void addUser(User user) throws SQLException {
        userBase.addUser(user);
    }

    @Override
    public Resource getResourceByID(int i) throws SQLException {
        return resourcesBase.getResourceByID(i);
    }

    @Override
    public void updateCReqAdminID(Integer cReqID, Integer adminID) throws SQLException {
        creqsBase.updateAdminID(cReqID, adminID);
    }

    @Override
    public void updatePReqLandscaperID(Integer pReqID, Integer assignedLandscaper) throws SQLException {
        preqsBase.updateLandscaperID(pReqID, assignedLandscaper);
    }

    @Override
    public List<AuthData> allAuthItems() throws SQLException {
        return authBase.allItems();
    }

    @Override
    public boolean addAuthData(AuthData insertion) throws SQLException {
        return authBase.addAuthData(insertion);
    }

    @Override
    public void updateCReqPlant(int i, ClientRequest cReqByID) throws SQLException {
        creqsBase.updatePlant(i, cReqByID);
    }


}
