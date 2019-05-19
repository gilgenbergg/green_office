import data.AuthMapper;
import data.DBinit;
import model.AuthData;

import java.sql.SQLException;
import java.util.ArrayList;

public class Starter {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        System.out.println("Call to connect from main app is made...");

        AuthMapper authMapper = new AuthMapper();
        //AuthData found = authMapper.findItemByUID(1);
        //System.out.println(found.getLogin());

        //ArrayList<AuthData> allItems = authMapper.allItems();
        //System.out.println(allItems);

        // TODO: fix removal on database configuration level
        //authMapper.removeByLogin("test");
    }
}
