package repo;

import model.Plant;
import model.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PlantRepoImpl implements PlantRepository {

    private List<Plant> data = testBase();

    @Override
    public Plant findItemByPlantID(Integer receivedPlantID) {
        Plant found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getPlantID().equals(receivedPlantID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    @Override
    public List<Plant> filterPlantsByType(String type) {
        List<Plant> filtered = new ArrayList<>();
        for (Plant item:
                data) {
            if (item.getType() == type) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    @Override
    public boolean add(Plant item) {
        return data.add(item);
    }

    @Override
    public void remove(Plant item) {
        data.remove(item);
    }

    private List<Plant> testBase() {
        List<Plant> plants = new ArrayList<>();
        List<Resource> testResources = new ArrayList<>();
        Resource res = new Resource(1, "testResource", 1);
        testResources.add(res);
        for (int i=0; i<=4; i++) {
            Date prev = new Date(2018, 12, 8);
            Date next = new Date("2019-08-01");
            Plant item = new Plant(i, "test", prev, next, 1, testResources);
            plants.add(item);
        }
        return plants;
    }

    public List<Plant> allPlants() {
        return data;
    }

    public List<Resource> findResourcesByPlantID(Integer plantID) {
        ResourceRepoImpl resourceRepo = new ResourceRepoImpl();
        return resourceRepo.findResourcesByPlantID(plantID);
    }

    public Integer getValidPlantID() {
        Integer validID;
        List<Integer> allIDs = new ArrayList<>();
        for (Plant item:
             data) {
            allIDs.add(item.getPlantID());
        }
        if (allIDs.size() == 0) {
            validID = 1;
        }
        else {
            Integer prev = 0;
            for (Integer id:
                 allIDs) {
                if (id < allIDs.size()) {
                    if (id != prev + 1) {
                        validID = prev + 1;
                        prev = validID;
                        allIDs.add(validID);
                        allIDs.sort(Comparator.naturalOrder());
                    }
                    else if (id == prev + 1) {
                        prev = id;
                    }
                }
            }
            validID = prev + 1;
            allIDs.add(validID);
            allIDs.sort(Comparator.naturalOrder());
        }
        return validID;
    }

    public void setDateOfNextVisit(Plant plant, Date nextInspection) {
        plant.setNextInspection(nextInspection);
    }
}
