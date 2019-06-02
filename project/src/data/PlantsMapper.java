package data;

import model.Instruction;
import model.Plant;
import model.Resource;

import java.sql.*;
import java.util.*;

public class PlantsMapper extends DBinit {

    private static Set<Plant> cash = new HashSet<>();
    private Connection connection;
    private ResourcesMapper resourcesMapper = new ResourcesMapper();
    private InstructionsMapper instructionsMapper = new InstructionsMapper();

    private static PlantsMapper PLANT_TABLE;

    static {
        try {
            PLANT_TABLE = new PlantsMapper();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PlantsMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    public List<Plant> filterPlantsByUserID(Integer uid) throws SQLException {
        ResultSet rs = null;
        ArrayList<Plant> filtered = new ArrayList<>();
        String select = "SELECT * FROM plant WHERE client_id='"+uid+"'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        while (rs.next()) {
            Plant item = new Plant(null, null, null, null, null, null, null);
            item.setPlantID(rs.getInt("plant_id"));
            item.setType(rs.getString("type"));
            item.setLastInspection("last_inspection");
            item.setNextInspection("nextInspection");
            item.setInstructionID(rs.getInt("instruction_id"));
            item.setClientID(rs.getInt("client_id"));
            filtered.add(item);
        }
        return filtered;
    }

    public Plant findItemByPlantID(Integer receivedPlantID) throws SQLException {
        for (Plant item : cash) {
            if (item.getPlantID().equals(receivedPlantID))
                return item;
        }
        //if in cash item wasn`t found, searching in database
        ResultSet rs = null;
        String select = "SELECT * FROM plant WHERE plant_id='"+receivedPlantID+"'";
        PreparedStatement searchByID = connection.prepareStatement(select);
        rs = searchByID.executeQuery();
        if (!rs.next())
            return null;
        Integer plantID = rs.getInt("plant_id");
        Integer clientID = rs.getInt("client_id");
        String type = rs.getString("type");
        String lastInspection = rs.getString("last_inspection");
        String nextInspection = rs.getString("next_inspection");
        Integer instructionID = rs.getInt("instruction_id");
        ArrayList resources = resourcesMapper.findResourcesByPlantID(receivedPlantID);
        Plant newPlantItem = new Plant(plantID, type, lastInspection, nextInspection, instructionID, resources, clientID);
        cash.add(newPlantItem);
        return  newPlantItem;
    }

    private List<Resource> findResources(Integer plantID) throws SQLException {
        ArrayList resources = resourcesMapper.findResourcesByPlantID(plantID);
        return resources;
    }

    public Plant addNewPlant(Plant item) throws SQLException {
        Integer clientID = item.getOwnerID();
        String type = item.getType();
        ResultSet rs = null;
        String request = "INSERT INTO plant (type, client_id) " +
                "VALUES (?, ?);";
        PreparedStatement insertion = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        insertion.setString(1, type);
        insertion.setInt(2, clientID);
        insertion.executeUpdate();
        rs = insertion.getGeneratedKeys();
        Integer id = null;
        if (rs.next()) {
            id = rs.getInt(1);
            item.setPlantID(id);
        }
        return findItemByPlantID(id);
    }

    public Instruction getInstruction(Integer plantID) throws SQLException {
        Plant plant = findItemByPlantID(plantID);
        Integer instructionID = plant.getInstructionID();
        Instruction instruction = instructionsMapper.getInstructionByID(instructionID);
        return instruction;
    }

    public ArrayList<Plant> allPlants() throws SQLException {
        ResultSet rs = null;
        Plant item = null;
        ArrayList<Plant> allPlants = new ArrayList<>();
        String select = "SELECT * FROM plant;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new Plant(null, null, null, null, null, null, null);
            item.setPlantID(rs.getInt("plant_id"));
            item.setType(rs.getString("type"));
            item.setLastInspection("last_inspection");
            item.setNextInspection("nextInspection");
            item.setInstructionID(rs.getInt("instruction_id"));
            item.setClientID(rs.getInt("client_id"));
            allPlants.add(item);
        }
        return allPlants;
    }

    public boolean setDateOfLastVisit(Integer plantID, String lastInspection) throws SQLException {
        String request = "UPDATE plant SET last_inspection=? WHERE plant_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setString(1, lastInspection);
        update.setInt(2, plantID);
        update.executeUpdate();
        return true;
    }

    public boolean setDateOfNextVisit(Integer plantID, String nextInspection) throws SQLException {
        String request = "UPDATE plant SET next_inspection=? WHERE plant_id = ?;";
        PreparedStatement update = connection.prepareStatement(request);
        update.setString(1, nextInspection);
        update.setInt(2, plantID);
        update.executeUpdate();
        return true;
    }

}
