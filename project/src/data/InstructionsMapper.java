package data;

import model.Instruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InstructionsMapper extends DBinit {
    private static Set<Instruction> cash = new HashSet<>();
    private Connection connection;

    public InstructionsMapper() throws SQLException, ClassNotFoundException {
        super();
        connection = DBinit.getInstance().getConnInst();
    }

    public ArrayList<Instruction> allItems() throws SQLException {
        ResultSet rs = null;
        Instruction item = null;
        ArrayList<Instruction> allInstructions = new ArrayList<>();
        String select = "SELECT * FROM instruction;";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();

        while (rs.next()) {
            item = new Instruction(null, null);
            item.setInstructionID(rs.getInt("instruction_id"));
            item.setInstruction(rs.getString("instruction"));
            allInstructions.add(item);
        }
        return allInstructions;
    }

    public Instruction getInstructionByID(Integer instructionID) throws SQLException {
        //searching in cash
        for (Instruction item : cash) {
            if (item.getInstructionID().equals(instructionID))
                return item;
        }
        ResultSet rs = null;
        Instruction item = null;
        String select = "SELECT * FROM instruction WHERE instruction_id='"+instructionID+"';";
        PreparedStatement statement = connection.prepareStatement(select);
        rs = statement.executeQuery();
        item.setInstructionID(rs.getInt("instruction_id"));
        item.setInstruction(rs.getString("instruction"));
        return item;
    }
}
