package model;

import repo.PlantRepoImpl;
import repo.PurchaseReqRepoImpl;
import repo.ResourceRepoImpl;
import repo.UserRepoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Admin extends User {

    private Integer adminID;
    PlantRepoImpl plantsBase = new PlantRepoImpl();
    UserRepoImpl users = new UserRepoImpl();
    ResourceRepoImpl resources = new ResourceRepoImpl();

    public Admin(Integer adminID, User user) {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
        this.adminID = adminID;
    }

    @Override
    public Role getRole() {
        return Role.admin;
    }

    public User getUser() {
        return this;
    }

    public void workOnClientRequst(ClientRequest clientRequest) {
        ClientRequest.Type cReqType = clientRequest.getType();
        if (cReqType == ClientRequest.Type.planned) {
            Integer plantID = clientRequest.getPlantID();
            Plant plant = plantsBase.findItemByPlantID(plantID);
            Date nextDate = new Date("2019-03-04");
            plantsBase.setDateOfNextVisit(plant, nextDate);
            clientRequest.setStatus(ClientRequest.Status.inProgress);
        }
        else {
            workOnFirstOneReq(clientRequest);
        }
    }

    private void workOnFirstOneReq(ClientRequest clientRequest) {
        Integer validID = generatePlantID();
        String type = null;
        Date lastInspection = null;
        Date nextInspection = null;
        Integer instructionID = null;
        List <Resource> resources = null;
        Plant plant = new Plant (validID, type, lastInspection, nextInspection, instructionID,  resources);
        plantsBase.add(plant);
        clientRequest.setStatus(ClientRequest.Status.inPurchase);
        boolean purchased = false;
        List<Resource> alreadyBought = null;
        while (clientRequest.getStatus() == ClientRequest.Status.inPurchase) {
            makePurchaseRequest(plant, clientRequest, alreadyBought);
            if (purchased) {
                clientRequest.setStatus(ClientRequest.Status.inProgress);
            }
        }
    }

    private void makePurchaseRequest(Plant plant, ClientRequest clientRequest, List<Resource> alreadyBought) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(generatePreqID(), clientRequest.getcReqID(),
                clientRequest.getPlantID(), clientRequest.getLandscaperID(), adminID, PurchaseRequest.Status.inProgress);
        purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
        //here landscaper approves purchase or not
        Landscaper checker = (Landscaper) users.findItemByUID(purchaseRequest.getLandscaperID());
        List<Resource> boughtByAdmin = testPurchase(clientRequest, alreadyBought);
        checker.checkPurchaseRequest(purchaseRequest, boughtByAdmin);
        while (purchaseRequest.getStatus() != PurchaseRequest.Status.approved) {
            List<Resource> newPurchase = testSomeMoreStaff(plant.getPlantID(), boughtByAdmin);
            purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
            checker.checkPurchaseRequest(purchaseRequest, newPurchase);
        }
    }

    // набор для тестовой дозакупки

    List<Resource> testSomeMoreStaff(Integer plantID, List<Resource> alreadyBought) {
        Resource res1 = resources.getItemByID(1);
        Resource res3 = resources.getItemByID(3);
        Resource res4 = resources.getItemByID(4);
        Resource res5 = resources.getItemByID(5);

        alreadyBought.add(res1);
        alreadyBought.add(res3);
        alreadyBought.add(res4);
        alreadyBought.add(res5);
        return alreadyBought;
    }

    List<Resource> testPurchase(ClientRequest clientRequest, List<Resource> alreadyBought) {
        List<Resource> toPurchase = new ArrayList<>();
        Integer plantID = clientRequest.getPlantID();
        //toPurchase = resources.findResourcesByPlantID(plantID);
        Resource res1 = resources.getItemByID(2);
        alreadyBought.add(res1);
        return alreadyBought;
    }

    private Integer generatePreqID() {
        PurchaseReqRepoImpl purchasesBase = new PurchaseReqRepoImpl();
        Integer pReqID = purchasesBase.getValidPurchaseID();
        return pReqID;
    }

    public Integer generatePlantID() {
        Integer validID = plantsBase.getValidPlantID();
        return validID;
    }
}


