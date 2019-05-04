package model;

import repo.PlantRepoImpl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Plant {
    private Integer plantID;
    private String type;
    private String lastInspection;
    private String nextInspection;
    private Integer instructionID;
    private List<Resource> resources;
    private Integer ownerID;

    private PlantRepoImpl repo = new PlantRepoImpl();

    public Plant(Integer plantID,
                 String type,
                 String lastInspection,
                 String nextInspection,
                 Integer instructionID,
                 List<Resource> resources,
                 Integer clientID) throws ParseException {
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

    public List<Resource> getResources(Integer plantID) {
        return repo.findResourcesByPlantID(this.plantID);
    }
}
