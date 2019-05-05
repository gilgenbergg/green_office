package repo;

import model.Plant;
import model.Resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ResourceRepoImpl implements ResourceRepository {

    private List<Resource> data = testBase();

    public ResourceRepoImpl() {
    }

    @Override
    public boolean add(Resource item) {
        return data.add(item);
    }

    @Override
    public void remove(Resource item) {
        data.remove(item);
    }

    /*public List<Resource> findResourcesByPlantID(Integer plantID) {
        List<Resource> plantResources = new ArrayList<>();
        Plant item = plants.findItemByPlantID(plantID);
        return item.getResources();
    }
    */

    public Resource getItemByID (Integer resourceID) {
        Resource found = null;
        for (Resource item:
             data) {
            if (item.getresourceID().equals(resourceID)) {
                found = item;
            }
        }
        return found;
    }

    private List<Resource> testBase() {
        List<Resource> resources = new ArrayList<>();
        Resource res1 = new Resource(1, "testResource1");
        Resource res2 = new Resource(2, "testResource2");
        Resource res3 = new Resource(3, "testResource3");
        resources.add(res1);
        resources.add(res2);
        resources.add(res3);
        return resources;
    }
}
