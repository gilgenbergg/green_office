package repo;

import java.util.List;

public class IDgenerator {

    public Integer generateNewID(List<Integer> allIDs) {
        Integer validID;
        if (allIDs.size() == 0) {
            validID = 1;
        } else {
            Integer prev = 0;
            for (Integer id :
                    allIDs) {
                if (id <= allIDs.size()) {
                    if (id != prev + 1) {
                        validID = prev + 1;
                        prev = validID;
                    } else if (id == prev + 1) {
                        prev = id;
                    }
                }
            }
            validID = prev + 1;
        }
        return validID;
    }
}
