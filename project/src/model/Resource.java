package model;

public class Resource {
    private Integer resourceID;
    private String resource;
    private Integer plantID;

    public Resource(Integer resourceID, String resource, Integer plantID) {
        this.resourceID = resourceID;
        this.resource = resource;
        this.plantID = plantID;
    }

    public Integer getresourceID() {
        return resourceID;
    }

    public String getResource() {
        return resource;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public void setPlantID(Integer plantID) {
        this.plantID = plantID;
    }
}
