package data;

import model.Admin;
import model.ClientRequest;

import java.sql.*;
import java.util.*;

public class CReqsMapper extends DBinit {

    private static Set<ClientRequest> cash = new HashSet<>();
    private Connection connection;
    PlantsMapper plantsBase = new PlantsMapper();
    UsersMapper usersBase = new UsersMapper();

    public CReqsMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = getConnInst();
    }

    public ArrayList<ClientRequest> filterByStatus(ClientRequest.Status status) throws SQLException {
        ResultSet rs = null;
        String st = status.toString();
        String select = "SELECT * FROM creq WHERE status='"+st+"';";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        ArrayList<ClientRequest> filtered = new ArrayList<>();
        ClientRequest item = null;
        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String stringStatus = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(stringStatus);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);
            item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            filtered.add(item);
        }
        return filtered;
    }

    public ArrayList<ClientRequest> filterByType(ClientRequest.Type type) throws SQLException {
        ResultSet rs = null;
        String tp = type.toString();
        String select = "SELECT * FROM creq WHERE type='"+tp+"';";
        PreparedStatement st = connection.prepareStatement(select);
        rs = st.executeQuery();
        ArrayList<ClientRequest> filtered = new ArrayList<>();
        ClientRequest item = null;
        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String stringStatus = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(stringStatus);
            item.setStatus(parsedStatus);
            String stringType = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(stringType);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);
            item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            filtered.add(item);
        }
        return filtered;
    }

    // system expects from client Requset addition with not null type (firstOne/planned)
    // by creating a new request, systems sets cReq`s status to 'newOne' by default
    // then admin or landscaper can update others fields according to generated cReqID

    public ClientRequest addCReq(ClientRequest item) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Integer clientID = item.getClientID();
        ClientRequest.Status status = ClientRequest.Status.newOne;
        //getting from Client
        ClientRequest.Type type = item.getType();
        Integer defaultAdmin = assignAdmin();
        String request = "INSERT INTO cReq (client_id, type, status, admin_id) " + "VALUES (?, ?, ?, ?);";
        PreparedStatement res = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        res.setInt(1, clientID);
        res.setString(2, type.toString());
        res.setString(3, status.toString());
        res.setInt(4, defaultAdmin);
        res.executeUpdate();
        rs = res.getGeneratedKeys();
        Integer id = null;
        while (rs.next()) {
            id = rs.getInt(1);
            item.setCReqID(id);
        }
        return findItemByID(id);
    }

    private Integer assignAdmin() throws SQLException, ClassNotFoundException {
        Integer adminID;
        List<Integer> adminsIDS = new ArrayList<>();
        List<Admin> allAdmins = usersBase.allAdmins();
        for (Admin item:
                allAdmins) {
            adminsIDS.add(item.getUID());
        }
        Integer rnd = new Random().nextInt(adminsIDS.size());
        adminID = adminsIDS.get(rnd);
        return adminID;
    }

    public boolean updateStatus(Integer cReqID, ClientRequest.Status status) throws SQLException {
        String request = "UPDATE cReq SET status=? WHERE cReq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setString(1, status.toString());
        update.setInt(2, cReqID);
        update.executeUpdate();
        return true;
    }

    public boolean updateType(Integer cReqID, ClientRequest.Type type) throws SQLException {
        String request = "UPDATE cReq SET type=? WHERE cReq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setString(1, type.toString());
        update.setInt(2, cReqID);
        update.executeUpdate();
        return true;
    }

    public boolean updateAdminID(Integer cReqID, Integer adminID) throws SQLException {
        String request = "UPDATE cReq SET admin_id=? WHERE creq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setInt(1, adminID);
        update.setInt(2, cReqID);
        update.executeUpdate();
        return true;
    }

    public boolean updateLandscaperID(Integer cReqID, Integer landscaperID) throws SQLException {
        String request = "UPDATE cReq SET landscaper_id=? WHERE creq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setInt(1, landscaperID);
        update.setInt(2, cReqID);
        update.executeUpdate();
        return true;
    }

    public ArrayList<ClientRequest> allItems() throws SQLException {
        ResultSet rs = null;
        ClientRequest item = null;
        ArrayList<ClientRequest> allCReqs = new ArrayList<>();
        String select = "SELECT * FROM creq;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);
            item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            allCReqs.add(item);
        }
        return allCReqs;
    }

    public List<ClientRequest> filterByUID(Integer uid) throws SQLException {
        ResultSet rs = null;
        ClientRequest item = null;
        Integer plantID = 0;
        String plantName = "---";
        List<ClientRequest> allCReqs = new ArrayList<>();
        String select = "SELECT * FROM creq WHERE client_id='"+uid+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            if (rs.getInt("plant_id") != 0) {
                plantID = rs.getInt("plant_id");
                item.setPlantID(plantID);
                item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            }
            else {
                item.setPlantID(plantID);
                item.setPlantName(plantName);
            }
            allCReqs.add(item);
        }
        return allCReqs;
    }

    public List<ClientRequest> filterByAdminID(Integer uid) throws SQLException {
        ResultSet rs = null;
        ClientRequest item = null;
        Integer plantID = 0;
        String plantName = "---";
        List<ClientRequest> allCReqs = new ArrayList<>();
        String select = "SELECT * FROM creq WHERE admin_id='"+uid+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            if (rs.getInt("plant_id") != 0) {
                plantID = rs.getInt("plant_id");
                item.setPlantID(plantID);
                item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            }
            else {
                item.setPlantID(plantID);
                item.setPlantName(plantName);
            }
            allCReqs.add(item);
        }
        return allCReqs;
    }

    public List<ClientRequest> filterByLandscaperID(Integer uid) throws SQLException {
        ResultSet rs = null;
        ClientRequest item = null;
        Integer plantID = 0;
        String plantName = "---";
        List<ClientRequest> allCReqs = new ArrayList<>();
        String select = "SELECT * FROM creq WHERE landscaper_id='"+uid+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            if (rs.getInt("plant_id") != 0) {
                plantID = rs.getInt("plant_id");
                item.setPlantID(plantID);
                item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            }
            else {
                item.setPlantID(plantID);
                item.setPlantName(plantName);
            }
            allCReqs.add(item);
        }
        return allCReqs;
    }

    public ClientRequest findItemByID(Integer cReqID) throws SQLException {
        //searching in cash
        for (ClientRequest item : cash) {
            if (item.getCReqID().equals(cReqID))
                return item;
        }
        ResultSet rs = null;
        Integer plantID = 0;
        String plantName = "---";
        ClientRequest item = new ClientRequest(null, null, null, null, null, null,
                null, null);
        String select = "SELECT * FROM creq WHERE creq_id='"+cReqID+"'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        while (rs.next()) {
            item.setCReqID(rs.getInt("cReq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            if (rs.getInt("plant_id") != 0) {
                plantID = rs.getInt("plant_id");
                item.setPlantID(plantID);
                item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            }
            else {
                item.setPlantID(plantID);
                item.setPlantName(plantName);
            }
        }
        return item;
    }

    public ClientRequest.Status parseStatusFromDB(String statusFromDB) {
        ClientRequest.Status parsedStatus = null;
        switch (statusFromDB) {
            case "newOne":
                parsedStatus = ClientRequest.Status.newOne;
                break;
            case "inPurchase":
                parsedStatus = ClientRequest.Status.inPurchase;
                break;
            case "inProgress":
                parsedStatus = ClientRequest.Status.inProgress;
                break;
            case "gardening":
                parsedStatus = ClientRequest.Status.gardening;
                break;
            case "done":
                parsedStatus = ClientRequest.Status.done;
                break;
        }
        return parsedStatus;
    }

    public ClientRequest.Type parseTypeFromDB(String typeFromDB) {
        ClientRequest.Type parsedType = null;
        switch (typeFromDB) {
            case "firstOne":
                parsedType = ClientRequest.Type.firstOne;
                break;
            case "planned":
                parsedType = ClientRequest.Type.planned;
                break;
        }
        return parsedType;
    }

    public boolean updatePlant(Integer plantID, ClientRequest itemByID) throws SQLException {
        String request = "UPDATE cReq SET plant_id=? WHERE creq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setInt(1, plantID);
        update.setInt(2, itemByID.getCReqID());
        update.executeUpdate();
        return true;
    }

    public List<ClientRequest> filterByPlantID(Integer plantID) throws SQLException {
        ResultSet rs = null;
        ClientRequest item = null;
        List<ClientRequest> allCReqs = new ArrayList<>();
        String select = "SELECT * FROM creq WHERE plant_id='"+plantID+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new ClientRequest(null, null, null, null, null, null,
                    null, null);
            item.setCReqID(rs.getInt("creq_id"));
            String status = rs.getString("status");
            ClientRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            String type = rs.getString("type");
            ClientRequest.Type parsedType = parseTypeFromDB(type);
            item.setType(parsedType);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer clientID = rs.getInt("client_id");
            item.setClientID(clientID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            item.setPlantID(plantID);
            item.setPlantName(plantsBase.findItemByPlantID(plantID).getType());
            allCReqs.add(item);
        }
        return allCReqs;
    }
}
