package model;

public class ClientRequest {

    public enum Status {
        newOne, inPurchase, inProgress, gardening, done
    }

    public enum Type {
        firstOne, planned
    }

    private Integer cReqID;
    private Integer plantID;
    private String plantName;
    private Integer clientID;
    private Integer landscaperID;
    private Integer adminID;
    private Status status;
    private Type creqType;

    public ClientRequest(Integer cReqID,
                         Integer plantID,
                         String plantName,
                         Integer clientID,
                         Integer landscaperID,
                         Integer adminID,
                         Status status,
                         Type type) {
        this.cReqID = cReqID;
        this.plantID = plantID;
        this.plantName = plantName;
        this.clientID = clientID;
        this.landscaperID = landscaperID;
        this.adminID = adminID;
        this.status = status;
        this.creqType = type;
    }

    public Integer getCReqID() {
        return cReqID;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public Integer getClientID() {
        return clientID;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public Integer getLandscaperID() {
        return landscaperID;
    }

    public Type getType() {
        return creqType;
    }

    public Status getStatus() {
        return status;
    }

    public void setCReqID(Integer cReqID) {
        this.cReqID = cReqID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public void setPlantID(Integer plantID) {
        this.plantID = plantID;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public void setLandscaperID(Integer landscaperID) {
        this.landscaperID = landscaperID;
    }

    public void setType(Type type) {
        this.creqType= type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
