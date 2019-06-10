package data;

import model.ClientRequest;
import model.PurchaseRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PReqsMapper extends DBinit {

    private static Set<PurchaseRequest> cash = new HashSet<>();
    private Connection connection;

    public PReqsMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    public ArrayList<PurchaseRequest> allItems() throws SQLException {
        ResultSet rs = null;
        PurchaseRequest item = null;
        ArrayList<PurchaseRequest> allPReqs = new ArrayList<>();
        String select = "SELECT * FROM preq;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new PurchaseRequest(null, null, null, null, null,
                    null);
            item.setPReqID(rs.getInt("preq_id"));
            String status = rs.getString("status");
            PurchaseRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer creqID = rs.getInt("creq_id");
            item.setCReqID(creqID);
            allPReqs.add(item);
        }
        return allPReqs;
    }

    public PurchaseRequest.Status parseStatusFromDB(String statusFromDB) {
        PurchaseRequest.Status parsedStatus = null;
        switch (statusFromDB) {
            case "inProgress":
                parsedStatus = PurchaseRequest.Status.inProgress;
                break;
            case "inCheck":
                parsedStatus = PurchaseRequest.Status.inCheck;
                break;
            case "approved":
                parsedStatus = PurchaseRequest.Status.approved;
                break;
        }
        return parsedStatus;
    }

    public PurchaseRequest findItemByID(Integer pReqID) throws SQLException {
        //searching in cash
        for (PurchaseRequest item : cash) {
            if (item.getCReqID().equals(pReqID))
                return item;
        }
        ResultSet rs = null;
        String select = "SELECT * FROM preq WHERE preq_id='" + pReqID + "'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        PurchaseRequest item = new PurchaseRequest(null, null, null, null,
                null, null);
        while (rs.next()) {
            item.setPReqID(rs.getInt("pReq_id"));
            String status = rs.getString("status");
            PurchaseRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);
        }
        return item;
    }

    private ArrayList<PurchaseRequest> filterByStatus(ClientRequest.Status status) throws SQLException {
        ResultSet rs = null;
        String st = status.toString();
        String select = "SELECT * FROM preq WHERE status='" + st + "'";
        PreparedStatement searchByID = connection.prepareStatement(select);
        rs = searchByID.executeQuery();
        if (!rs.next())
            return null;
        ArrayList<PurchaseRequest> filtered = new ArrayList<>();
        PurchaseRequest item = null;
        while (rs.next()) {
            item = new PurchaseRequest(null, null, null, null, null,
                    null);
            item.setPReqID(rs.getInt("preq_id"));
            String statusFromDB = rs.getString("status");
            PurchaseRequest.Status parsedStatus = parseStatusFromDB(statusFromDB);
            item.setStatus(parsedStatus);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer creqID = rs.getInt("creq_id");
            item.setCReqID(creqID);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);

            filtered.add(item);
        }
        return filtered;
    }

    //admin creates purchaseRequest. by default its status is inProgress.
    // after admin finishes buying staff for the plant, status is changed on inCheck.
    // in such status it goes to landscaper and after check is finished its status is changed by him
    // on inProgress(if it`s needed some more staff to buy) or on approved (if everything is fine)

    public Integer addPReq(PurchaseRequest item) throws SQLException {
        ResultSet rs = null;
        PurchaseRequest.Status status = PurchaseRequest.Status.inProgress;
        Integer adminID = item.getAdminID();
        Integer plantID = item.getPlantID();
        Integer cReqID = item.getCReqID();
        String request = "INSERT INTO pReq (status, plant_id, admin_id, creq_id) " + "VALUES (?, ?, ?, ?)";
        PreparedStatement res = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        res.setString(1, status.toString());
        res.setInt(2, plantID);
        res.setInt(3, adminID);
        res.setInt(4, cReqID);
        res.executeUpdate();
        rs = res.getGeneratedKeys();
        Integer id = null;
        while (rs.next()) {
            id = rs.getInt(1);
            item.setPReqID(id);
        }
        return id;
    }

    public boolean updateStatus(Integer pReqID, PurchaseRequest.Status status) throws SQLException {
        String request = "UPDATE pReq SET status=? WHERE pReq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setString(1, status.toString());
        update.setInt(2, pReqID);
        update.executeUpdate();
        return true;
    }

    public boolean updateAdminID(Integer pReqID, Integer adminID) throws SQLException {
        String request = "UPDATE pReq SET admin_id=? WHERE preq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setInt(1, adminID);
        update.setInt(2, pReqID);
        update.executeUpdate();
        return true;
    }

    public boolean updateLandscaperID(Integer pReqID, Integer landscaperID) throws SQLException {
        PurchaseRequest pReq = findItemByID(pReqID);
        String request = "UPDATE pReq SET landscaper_id=? WHERE preq_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setInt(1, landscaperID);
        update.setInt(2, pReqID);
        update.executeUpdate();
        return true;
    }

    public List<PurchaseRequest> filterByUserID(Integer uid) throws SQLException {
        ResultSet rs = null;
        PurchaseRequest item = null;
        List<PurchaseRequest> allPReqs = new ArrayList<>();
        String select = "SELECT * FROM preq WHERE admin_id='"+uid+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new PurchaseRequest(null, null, null, null, null,
                    null);
            item.setPReqID(rs.getInt("preq_id"));
            String status = rs.getString("status");
            PurchaseRequest.Status parsedStatus = parseStatusFromDB(status);
            item.setStatus(parsedStatus);
            Integer plantID = rs.getInt("plant_id");
            item.setPlantID(plantID);
            Integer adminID = rs.getInt("admin_id");
            item.setAdminID(adminID);
            Integer landscaperID = rs.getInt("landscaper_id");
            item.setLandscaperID(landscaperID);
            Integer creqID = rs.getInt("creq_id");
            item.setCReqID(creqID);
            allPReqs.add(item);
        }
        return allPReqs;
    }
}
