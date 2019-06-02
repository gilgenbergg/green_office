import data.*;
import model.*;

import java.sql.SQLException;

public class Starter {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        System.out.println("Call to connect from main app is made...");

        AuthMapper authMapper = new AuthMapper();
        CReqsMapper cReqs = new CReqsMapper();
        InstructionsMapper instructionsMapper = new InstructionsMapper();
        PlantsMapper plantsBase = new PlantsMapper();
        PReqsMapper pReqs = new PReqsMapper();
        ResourcesMapper resourcesMapper = new ResourcesMapper();
        UsersMapper users = new UsersMapper();
    }
}
