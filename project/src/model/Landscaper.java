package model;

import data.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Landscaper extends User {

    private UsersMapper userRepo = new UsersMapper();
    private PlantsMapper plantBase = new PlantsMapper();
    private PReqsMapper pReqsMapper = new PReqsMapper();
    private CReqsMapper cReqsMapper = new CReqsMapper();
    private ResourcesMapper resourcesMapper = new ResourcesMapper();

    public Landscaper(User user) throws SQLException, ClassNotFoundException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole(), user.getAuthDataID());
    }

    @Override
    public Role getRole() {
        return Role.landscaper;
    }

    public User getUser(Integer userID) throws SQLException {
        return userRepo.findItemByUID(userID);
    }

    public Landscaper getLandscaper(User user) {
        return this;
    }

    public void checkPurchaseRequest(Integer pReqID, List<Resource> boughtResources,
                                     ClientRequest clientRequest) throws SQLException {
        boolean checkResult;
        PurchaseRequest purchaseRequest = pReqsMapper.findItemByID(pReqID);
        if (purchaseRequest.getStatus() == PurchaseRequest.Status.inCheck) {
            checkResult = checkPurchase(purchaseRequest, boughtResources);
            if (checkResult) {
                pReqsMapper.updateStatus(pReqID, PurchaseRequest.Status.approved);
            }
            else {
                pReqsMapper.updateStatus(pReqID, PurchaseRequest.Status.inProgress);
            }
        }
    }

    public boolean checkPurchase(PurchaseRequest purchaseRequest, List<Resource> boughtResources) throws SQLException {
        Plant plant = plantBase.findItemByPlantID(purchaseRequest.getPlantID());
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
            cReqsMapper.updateStatus(clientRequest.getCReqID(), ClientRequest.Status.done);
            Plant plant = plantBase.findItemByPlantID(cReqsMapper.findItemByID(clientRequest.getCReqID()).getPlantID());
            //getting from landscapers ui
            plantBase.setDateOfLastVisit(plant.getPlantID(), "today");
            plantBase.setDateOfNextVisit(plant.getPlantID(), "someday");
        }
        else {
            cReqsMapper.updateStatus(clientRequest.getCReqID(), ClientRequest.Status.newOne);
        }
    }
}
