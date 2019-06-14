package model;

import facade.Facade;
import facade.Starter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private Integer adminID;

    private Facade facade = Starter.facade;

    public Admin(Integer adminID, User user) throws SQLException, ClassNotFoundException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole(), user.getAuthDataID());
        this.adminID = adminID;
    }

    @Override
    public Role getRole() {
        return Role.admin;
    }

    public void workOnClientRequest(ClientRequest clientRequest, String plantType) throws SQLException, ClassNotFoundException {
        while (facade.findCReqByID(clientRequest.getCReqID()).getStatus().equals(ClientRequest.Status.newOne)) {
            ClientRequest.Type cReqType = clientRequest.getType();
            if (cReqType == ClientRequest.Type.planned) {
                Integer assignedLandscaper = 3;
                Landscaper gardener = facade.getLandscaperByUserID(assignedLandscaper);
                Integer plantID = facade.findCReqByID(clientRequest.getCReqID()).getPlantID();
                Plant plant = facade.findItemByPlantID(plantID);
                facade.updateCReqStatus(clientRequest.getCReqID(), ClientRequest.Status.gardening);
                gardener.makeGardening(facade.findCReqByID(clientRequest.getCReqID()));
            } else {
                workOnFirstOneReq(clientRequest, plantType);
            }
        }
    }

    private String addNextDate() {
        String nextDate = "12.12.2020";
        return nextDate;
    }

    private List<Resource> testResources() throws SQLException {
        List<Resource> testResources = new ArrayList<Resource>();
        Resource resource = facade.getResourceByID(2);
        testResources.add(resource);
        return testResources;
    }

    public void workOnFirstOneReq(ClientRequest clientRequest, String plantType) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant(null, plantType, null, null, null, null,
                clientRequest.getClientID());
        plant = facade.addNewPlant(plant);
        facade.updatePlant(plant.getPlantID(), facade.findCReqByID(clientRequest.getCReqID()));
        facade.updateCReqStatus(clientRequest.getCReqID(), ClientRequest.Status.inPurchase);
        facade.updateCReqAdminID(clientRequest.getCReqID(), this.adminID);
        List<Resource> alreadyBought = new ArrayList<>();
        clientRequest = facade.findCReqByID(clientRequest.getCReqID());
        while (facade.findCReqByID(clientRequest.getCReqID()).getStatus() != ClientRequest.Status.done) {
            PurchaseRequest pReq = makePurchaseRequest(plant, facade.findCReqByID(clientRequest.getCReqID()), alreadyBought);
        }
    }

    public PurchaseRequest makePurchaseRequest(Plant plant, ClientRequest clientRequest, List<Resource> alreadyBought) throws SQLException, ClassNotFoundException {
        PurchaseRequest purchaseStart = new PurchaseRequest(null, clientRequest.getCReqID(),
                plant.getPlantID(), facade.findItemByPlantID(plant.getPlantID()).getType(),
                null, adminID, null);
        Integer pReqID = facade.addPReq(purchaseStart);
        //let`s for now we have just one landscaper
        Integer assignedLandscaper = 3;
        Landscaper checker = facade.getLandscaperByUserID(assignedLandscaper);
        facade.updatePReqLandscaperID(pReqID, assignedLandscaper);
        List<Resource> boughtByAdmin = testPurchase(clientRequest, alreadyBought);
        //here landscaper approves purchase or not
        facade.updatePReqStatus(pReqID, PurchaseRequest.Status.inCheck);
        PurchaseRequest purchaseReq = facade.findPReqByID(pReqID);
        checker.checkPurchaseRequest(pReqID, boughtByAdmin, clientRequest);
        while (facade.findPReqByID(pReqID).getStatus() != PurchaseRequest.Status.approved) {
            List<Resource> previouslyBought = alreadyBought;
            List<Resource> newPurchase = buySomeMoreStaff(previouslyBought);
            facade.updatePReqStatus(pReqID, PurchaseRequest.Status.inCheck);
            checker.checkPurchaseRequest(pReqID, newPurchase, clientRequest);
        }
        facade.updateCReqStatus(clientRequest.getCReqID(), ClientRequest.Status.gardening);
        facade.updateCReqLandscaperID(clientRequest.getCReqID(), assignedLandscaper);
        checker.makeGardening(facade.findCReqByID(clientRequest.getCReqID()));
        purchaseReq = facade.findPReqByID(pReqID);
        return purchaseReq;
    }

    // набор для тестовой дозакупки
    // get from UI

    List<Resource> buySomeMoreStaff(List<Resource> alreadyBought) throws SQLException {
        Resource res1 = facade.getResourceByID(1);
        Resource res3 = facade.getResourceByID(3);
        Resource res4 = facade.getResourceByID(4);
        Resource res5 = facade.getResourceByID(5);
        alreadyBought.add(res1);
        alreadyBought.add(res3);
        alreadyBought.add(res4);
        alreadyBought.add(res5);
        return alreadyBought;
    }

    List<Resource> testPurchase(ClientRequest clientRequest, List<Resource> alreadyBought) throws SQLException {
        Integer plantID = clientRequest.getPlantID();
        Resource res1 = facade.getResourceByID(2);
        alreadyBought.add(res1);
        return alreadyBought;
    }

}


