package model;

public class PurchaseRequest {

    public enum Status {
        inProgress, inCheck, approved
    }

    private Integer pReqID;
    private Integer cReqID;
    private Integer plantID;
    private String plantName;
    private Integer adminID;
    private Integer landscaperID;
    private Status status;
    //private List<Resource> alreadyBought;

    public PurchaseRequest(Integer pReqID,
                         Integer cReqID,
                         Integer plantID,
                         String plantName,
                         Integer landscaperID,
                         Integer adminID,
                         Status status) {
        this.pReqID = pReqID;
        this.cReqID = cReqID;
        this.plantID = plantID;
        this.plantName = plantName;
        this.landscaperID = landscaperID;
        this.adminID = adminID;
        this.status = status;
        //this.alreadyBought = alreadyBought;
    }

    public Integer getCReqID() {
        return cReqID;
    }

    public Integer getPReqID() {
        return pReqID;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public Integer getLandscaperID() {
        return landscaperID;
    }

    public Status getStatus() {
        return status;
    }

    public void setCReqID(Integer cReqID) {
        this.cReqID = cReqID;
    }

    public void setPReqID(Integer pReqID) {
        this.pReqID = pReqID;
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

    public void setStatus(Status status) {
        this.status = status;
    }

}

