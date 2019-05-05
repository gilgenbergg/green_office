package model;

import java.util.List;

public class Plant {
    private Integer plantID;
    private String type;
    private String lastInspection;
    private String nextInspection;
    private Integer instructionID;
    private List<Resource> resources;
    private Integer ownerID;

//    private PlantRepoImpl repo = new PlantRepoImpl();

    public Plant(Integer plantID,
                 String type,
                 String lastInspection,
                 String nextInspection,
                 Integer instructionID,
                 List<Resource> resources,
                 Integer clientID) {
        this.plantID = plantID;
        this.type = type;
        this.lastInspection = lastInspection;
        this.nextInspection = nextInspection;
        this.instructionID = instructionID;
        this.resources = resources;
        this.ownerID = clientID;
    }

    public Plant getPlant(Integer plantID) {
        return this;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public void setPlantID(Integer plantID) {
        this.plantID = plantID;
    }

    public String getType() {
        return this.type;
    }

    public Integer getOwnerID() {
        return this.ownerID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastInspection() {
        return lastInspection;
    }

    public void setLastInspection(String lastInspection) {
        this.lastInspection = lastInspection;
    }

    public String getNextInspection() {
        return nextInspection;
    }

    public void setNextInspection(String nextInspection) {
        this.nextInspection = nextInspection;
    }

    public Integer getInstructionID() {
        return instructionID;
    }

    public void setInstructionID(Integer instructionID) {
        this.instructionID = instructionID;
    }

    public List<Resource> getResources() {
        return resources;
    }
}
