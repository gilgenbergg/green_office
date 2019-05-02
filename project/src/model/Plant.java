package model;

import repo.PlantRepoImpl;

import java.util.Date;
import java.util.List;

public class Plant {
    private Integer plantID;
    private String type;
    private Date lastInspection;
    private Date nextInspection;
    private Integer instructionID;
    private List<Resource> resources;
    private Integer ownerID;

    private PlantRepoImpl repo = new PlantRepoImpl();

    public Plant(Integer plantID,
                 String type,
                 Date lastInspection,
                 Date nextInspection,
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

    public Plant getPlantByID(Integer plantID) {
        return repo.findItemByPlantID(plantID);
    }

    private List<Plant> getAllPlants() {
        List<Plant> allPlants = repo.allPlants();
        return allPlants;
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

    public Date getLastInspection() {
        return lastInspection;
    }

    public void setLastInspection(Date lastInspection) {
        this.lastInspection = lastInspection;
    }

    public Date getNextInspection() {
        return nextInspection;
    }

    public void setNextInspection(Date nextInspection) {
        this.nextInspection = nextInspection;
    }

    public Integer getInstructionID() {
        return instructionID;
    }

    public void setInstructionID(Integer instructionID) {
        this.instructionID = instructionID;
    }

    public List<Resource> getResources(Integer plantID) {
        return repo.findResourcesByPlantID(this.plantID);
    }
}
