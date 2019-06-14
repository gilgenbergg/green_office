package model;

import data.*;
import facade.Facade;
import facade.Starter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Landscaper extends User {

    private Facade facade = Starter.facade;

    private ResourcesMapper resourcesMapper = new ResourcesMapper();

    public Landscaper(User user) throws SQLException, ClassNotFoundException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole(), user.getAuthDataID());
    }

    @Override
    public Role getRole() {
        return Role.landscaper;
    }

    public User getUser(Integer userID) throws SQLException {
        return facade.findItemByUID(userID);
    }

    public Landscaper getLandscaper(User user) {
        return this;
    }

    public void checkPurchaseRequest(Integer pReqID, List<Resource> boughtResources,
                                     ClientRequest clientRequest) throws SQLException {
        boolean checkResult;
        PurchaseRequest purchaseRequest = facade.findPReqByID(pReqID);
        if (purchaseRequest.getStatus() == PurchaseRequest.Status.inCheck) {
            checkResult = checkPurchase(purchaseRequest, boughtResources);
            if (checkResult) {
                facade.updatePReqStatus(pReqID, PurchaseRequest.Status.approved);
            }
            else {
                facade.updatePReqStatus(pReqID, PurchaseRequest.Status.inProgress);
            }
        }
    }

    public boolean checkPurchase(PurchaseRequest purchaseRequest, List<Resource> boughtResources) throws SQLException {
        Plant plant = facade.findItemByPlantID(purchaseRequest.getPlantID());
        List<Resource> neededResources = resourcesMapper.findResourcesByPlantID(purchaseRequest.getPlantID());
        List<Integer> ids = new ArrayList<>();
        for (Resource item:
             boughtResources) {
            ids.add(item.getresourceID());
        }
        for (Resource item:
                neededResources) {
            if (!ids.contains(item.getresourceID())) {
                return false;
            }
        }
        return true;
    }

    public void makeGardening(ClientRequest clientRequest) throws SQLException {
        if (clientRequest.getStatus().equals(ClientRequest.Status.gardening)) {
            //cReqsMapper.updateLandscaperID(clientRequest.getCReqID(), this.getUID());
            facade.updateCReqStatus(clientRequest.getCReqID(), ClientRequest.Status.done);
            Plant plant = facade.findItemByPlantID(facade.findCReqByID(clientRequest.getCReqID()).getPlantID());
            //getting from landscapers ui
            facade.setDateOfLastVisit(plant.getPlantID(), "today");
            facade.setDateOfNextVisit(plant.getPlantID(), "someday");
        }
        else {
            facade.updateCReqStatus(clientRequest.getCReqID(), ClientRequest.Status.newOne);
        }
    }
}
