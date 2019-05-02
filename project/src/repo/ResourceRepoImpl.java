package repo;

import model.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceRepoImpl implements ResourceRepository {

    private List<Resource> data = testBase();

    @Override
    public boolean add(Resource item) {
        return data.add(item);
    }

    @Override
    public void remove(Resource item) {
        data.remove(item);
    }

    public List<Resource> findResourcesByPlantID(Integer plantID) {
        List<Resource> plantResources = new ArrayList<>();
        for (Resource item:
                data) {
            if (item.getPlantID().equals(plantID)) {
                plantResources.add(item);
            }
        }
        return plantResources;
    }

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
        Resource res = new Resource(1, "testResource", 1);
        resources.add(res);
        for (int i=0; i<=4; i++) {
            Resource item = new Resource(i, "Lopatka", 1);
            resources.add(item);
        }
        return resources;
    }
}
