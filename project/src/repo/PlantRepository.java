package repo;

import model.Plant;

import java.util.List;

public interface PlantRepository {

    Plant findItemByPlantID(Integer receivedPlantID);

    List<Plant> filterPlantsByType(String type);

    boolean add(Plant item);

    void remove(Plant item);

    List<Plant> allPlants();

    Integer getValidPlantID();
}
