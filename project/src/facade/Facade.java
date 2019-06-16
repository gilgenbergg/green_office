package facade;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Facade {

    void authorize(User user, String login, String password) throws Exception;

    List<ClientRequest> filterByType(ClientRequest.Type type) throws SQLException;

    User findItemByUID(Integer userID) throws SQLException;

    List<ClientRequest> filterByAdminID(Integer uid) throws SQLException;

    List<PurchaseRequest> filterByUserID(Integer uid) throws SQLException;

    AuthData findItemByLogin(String text) throws SQLException;

    User findItemByAuthID(Integer authDataID) throws SQLException;

    Client getClientByUserID(Integer uid) throws SQLException, ClassNotFoundException;

    Admin getAdminByUserID(Integer uid) throws SQLException, ClassNotFoundException;

    Landscaper getLandscaperByUserID(Integer uid) throws SQLException, ClassNotFoundException;

    void updatePReqStatus(Integer preqID, PurchaseRequest.Status approved) throws SQLException;

    PurchaseRequest findPReqByID(Integer preqID) throws SQLException;

    ArrayList<String> getTypesByPlantID(Integer plantID) throws SQLException;

    Plant findItemByPlantID(Integer plantID) throws SQLException;

    ArrayList<String> findRequiredByPlantType(String plantType) throws SQLException;

    List<Plant> filterPlantsByUserID(Integer clientID) throws SQLException;

    List<Landscaper> allLandscapers() throws SQLException, ClassNotFoundException;

    ClientRequest findCReqByID(Integer cReqID) throws SQLException;

    void updatePlant(int parseInt, ClientRequest cReqByID) throws SQLException;

    void updateCReqLandscaperID(Integer cReqIDSetup, int parseInt) throws SQLException;

    ClientRequest.Status parseCReqStatusFromDB(String chosenStatus);

    void updateCReqStatus(Integer cReqIDSetup, ClientRequest.Status parseCReqStatusFromDB) throws SQLException;

    ClientRequest.Type parseCReqTypeFromDB(String chosenType);

    void updateCReqType(Integer cReqIDSetup, ClientRequest.Type parseCReqTypeFromDB) throws SQLException;

    List<ClientRequest> filterCReqsByUID(Integer uid) throws SQLException;

    void setDateOfLastVisit(Integer assignedPlant, String lastInspection) throws SQLException;

    void setDateOfNextVisit(Integer assignedPlant, String nextInspection) throws SQLException;

    List<ClientRequest> filterCReqsByLandscaperID(Integer uid) throws SQLException;

    List<PurchaseRequest> filterPReqsByLandscaperID(Integer uid) throws SQLException;

    ClientRequest addCReq(ClientRequest creq) throws SQLException, ClassNotFoundException;

    List<Plant> allPlants() throws SQLException;

    List<ClientRequest> filterCReqsByPlantID(Integer assignedPlantID) throws SQLException;

    Plant addNewPlant(Plant plant) throws SQLException;

    List<Client> allClients() throws SQLException, ClassNotFoundException;

    PurchaseRequest.Status parsePReqStatusFromDB(String chosenStatus);

    Integer addPReq(PurchaseRequest pReq) throws SQLException, ClassNotFoundException;

    Resource addNewResourceItem(Resource newResource) throws SQLException;

    User.Role parseRole(String chosenRole);

    void addUser(User user) throws SQLException;

    Resource getResourceByID(int i) throws SQLException;

    void updateCReqAdminID(Integer cReqID, Integer adminID) throws SQLException;

    void updatePReqLandscaperID(Integer pReqID, Integer assignedLandscaper) throws SQLException;

    List<AuthData> allAuthItems() throws SQLException;

    boolean addAuthData(AuthData insertion) throws SQLException;

    void updateCReqPlant(int i, ClientRequest cReqByID) throws SQLException;
}
