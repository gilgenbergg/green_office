package repo;

import model.Plant;
import model.Resource;

import java.text.ParseException;
import java.util.*;

public class PlantRepoImpl implements PlantRepository {
    //private DateParser parser = new DateParser();
    private IDgenerator generator = new IDgenerator();
    private ResourceRepoImpl resources = new ResourceRepoImpl();
    private List<Plant> data = testBase();

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

    private List<Resource> findResources(Integer plantID) throws ParseException {
        Plant plant = findItemByPlantID(plantID);
        return plant.getResources();
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
        //String prev = parser.parseDate("Jan 18 20:56 MSK 2019");
        //String next = parser.parseDate("Feb 01 20:56 MSK 2019");
        String prev = "2009-06-18";
        String next = "2009-07-18";
        Resource res = resources.getItemByID(1);
        testResources.add(res);
        Plant first = new Plant(1, "test", prev, next, 1, testResources, 2);
        plants.add(first);
        Plant second = new Plant(2, "test", prev, next, 1, testResources, 2);
        plants.add(second);
        Plant third = new Plant(3, "test", prev, next, 1, testResources, 2);
        plants.add(third);
        return plants;
    }

    public List<Plant> allPlants() {
        return data;
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
