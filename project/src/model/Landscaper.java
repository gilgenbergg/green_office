package model;

import repo.ClientReqRepoImpl;
import repo.PlantRepoImpl;
import repo.UserRepoImpl;

import java.text.ParseException;
import java.util.List;

public abstract class Landscaper extends User {

    UserRepoImpl userRepo = new UserRepoImpl();
    PlantRepoImpl plantBase = new PlantRepoImpl();
    ClientReqRepoImpl cReqsRepo = new ClientReqRepoImpl();

    public Landscaper(User user) throws ParseException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
    }

    @Override
    public Role getRole() {
        return Role.landscaper;
    }

    public User getUser(Integer userID) {
        return userRepo.findItemByUID(userID);
    }

    public Landscaper getLandscaper(User user) {
        return this;
    }

    public void checkPurchaseRequest(PurchaseRequest purchaseRequest, List<Resource> boughtResources,
                                     ClientRequest clientRequest){
        boolean checkResult;
        if (purchaseRequest.getStatus() == PurchaseRequest.Status.inCheck) {
            checkResult = checkPurchase(purchaseRequest, boughtResources);
            if (checkResult) {
                purchaseRequest.setStatus(PurchaseRequest.Status.approved);
                clientRequest.setStatus(ClientRequest.Status.gardening);
                makeGardening(clientRequest);
            }
            else {
                purchaseRequest.setStatus(PurchaseRequest.Status.inProgress);
            }
        }
    }

    private boolean checkPurchase(PurchaseRequest purchaseRequest, List<Resource> boughtResources) {
        Plant plant = plantBase.findItemByPlantID(purchaseRequest.getPlantID());
        List<Resource> neededResources = plant.getResources();
        for (Resource item:
             neededResources) {
            if (!boughtResources.contains(item)) {
               return false;
            }
        }
        return true;
    }

    private void makeGardening(ClientRequest clientRequest) {
        clientRequest.setStatus(ClientRequest.Status.done);
    }
}
