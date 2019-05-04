package repo;

import model.Plant;
import model.Resource;

import java.text.ParseException;
import java.util.*;

public class PlantRepoImpl implements PlantRepository {

    private List<Plant> data = testBase();
    private IDgenerator generator = new IDgenerator();
    private DateParser parser = new DateParser();
    private ResourceRepoImpl resources = new ResourceRepoImpl();

    public PlantRepoImpl() throws ParseException {
    }

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
            if (item.getType().equals(type)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    @Override
    public List<Plant> filterPlantsByUserID(Integer userID) {
        List<Plant> filtered = new ArrayList<>();
        for (Plant item:
                data) {
            if (item.getOwnerID().equals(userID)) {
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



    private List<Plant> testBase() throws ParseException {
        List<Plant> plants = new ArrayList<>();
        List<Resource> testResources = new ArrayList<>();
        String prev = parser.parseDate("Jan 18 20:56 MSK 2019");
        String next = parser.parseDate("Feb 01 20:56 MSK 2019");
        Resource res = new Resource(1, "testResource", 1);
        resources.add(res);
        testResources.add(res);
        Plant first = new Plant(1, "test", prev, next, 1, testResources, 1);
        plants.add(first);
        for (int i=2; i<=5; i++) {
            Plant item = new Plant(i, "test", prev, next, 1, testResources, 2);
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
        validID = generator.generateNewID(allIDs);
        allIDs.add(validID);
        allIDs.sort(Comparator.naturalOrder());
        return validID;
    }

    public void setDateOfNextVisit(Plant plant, String nextInspection) {
        plant.setNextInspection(nextInspection);
    }
}
