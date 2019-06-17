package data;

import model.RequiredResource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RequiredResMapper extends DBinit {
    private static Set<RequiredResource> cash = new HashSet<>();
    private Connection connection;

    public RequiredResMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    public ArrayList<String> findRequiredByPlantType(String plantType) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> filtered = new ArrayList<>();
        String select = "SELECT * FROM required_resource WHERE plant_type='"+plantType+"'";
        PreparedStatement filter = connection.prepareStatement(select);
        rs = filter.executeQuery();
        while (rs.next()) {
            String item = rs.getString("type");
            filtered.add(item);
        }
        return filtered;
    }
}
