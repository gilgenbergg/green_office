package repo;

import model.Resource;

import java.util.List;

public interface ResourceRepository {

    boolean add(Resource item);

    void remove(Resource item);

    List<Resource> findResourcesByPlantID(Integer plantID);
}
