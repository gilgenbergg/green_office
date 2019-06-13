package model;

public class RequiredResource {
    private Integer resourceID;
    private String resource;
    private String plantType;

    public RequiredResource(Integer resourceID, String resource, String plantType) {
        this.resourceID = resourceID;
        this.resource = resource;
        this.plantType = plantType;
    }

    public Integer getResourceID() {
        return resourceID;
    }

    public String getResource() {
        return resource;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}