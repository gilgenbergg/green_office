package data;

import model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ResourcesMapper extends DBinit {

    private static Set<Resource> cash = new HashSet<>();
    private Connection connection;

    public ResourcesMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    public ArrayList<Resource> findResourcesByPlantID(Integer receivedPlantID) throws SQLException {
        ResultSet rs = null;
        ArrayList<Resource> filtered = new ArrayList<>();
        String select = "SELECT * FROM resource WHERE plant_id='"+receivedPlantID+"'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        while (rs.next()) {
            Resource item = new Resource(null, null, null);
            item.setResourceID(rs.getInt("resource_id"));
            item.setResource(rs.getString("type"));
            filtered.add(item);
        }
        return filtered;
    }

    public Resource getResourceByID(Integer resourceID) throws SQLException {
        //searching in cash
        for (Resource item : cash) {
            if (item.getresourceID().equals(resourceID))
                return item;
        }
        ResultSet rs = null;
        Resource item = new Resource(null, null, null);
        String select = "SELECT * FROM resource WHERE resource_id='"+resourceID+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();
        while (rs.next()) {
            item.setResourceID(rs.getInt("resource_id"));
            item.setPlantID(rs.getInt("plant_id"));
            item.setResource(rs.getString("type"));
        }
        return item;
    }

    public ArrayList<Resource> allItems() throws SQLException {
        ResultSet rs = null;
        Resource item = null;
        ArrayList<Resource> allResources = new ArrayList<>();
        String select = "SELECT * FROM resource;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new Resource(null, null, null);
            item.setResourceID(rs.getInt("resource_id"));
            item.setResource(rs.getString("type"));
            allResources.add(item);
        }
        return allResources;
    }

    public Resource addNewItem(Resource newResource) throws SQLException {
        String resource = newResource.getResource();
        Integer plantID = newResource.getPlantID();
        ResultSet rs = null;
        String insertion = "INSERT into resource (type, plant_id) VALUES (?, ?);";
        PreparedStatement st = connection.prepareStatement(insertion);
        st.setString(1, resource);
        st.setInt(2, plantID);
        st.executeUpdate();
        rs = st.getGeneratedKeys();
        if (rs.next()) {
            Integer id = rs.getInt(1);
            newResource.setResourceID(id);
        }
        return newResource;
    }

    public ArrayList<String> getTypesByPlantID(Integer plantID) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> filtered = new ArrayList<>();
        String select = "SELECT * FROM resource WHERE plant_id='"+plantID+"'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        while (rs.next()) {
            String item = rs.getString("type");
            filtered.add(item);
        }
        return filtered;
    }
}
