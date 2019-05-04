package model;

public class Resource {
    private Integer resourceID;
    private String resource;

    public Resource(Integer resourceID, String resource) {
        this.resourceID = resourceID;
        this.resource = resource;
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
}
