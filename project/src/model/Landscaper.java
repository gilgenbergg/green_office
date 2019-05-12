package model;

import repo.PlantRepoImpl;
import repo.UserRepoImpl;

import java.util.ArrayList;
import java.util.List;

public class Landscaper extends User {

    private UserRepoImpl userRepo = UserRepoImpl.getInstance();
    private PlantRepoImpl plantBase = PlantRepoImpl.getInstance();

    public Landscaper(User user) {
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

    public boolean checkPurchase(PurchaseRequest purchaseRequest, List<Resource> boughtResources) {
        Plant plant = plantBase.findItemByPlantID(purchaseRequest.getPlantID());
        List<Resource> neededResources = plant.getResources();
        List<Integer> ids = new ArrayList<>();
        for (Resource item:
             neededResources) {
            ids.add(item.getresourceID());
        }
        for (Resource item:
             boughtResources) {
            if (!ids.contains(item.getresourceID())) {
               return false;
            }
        }
        return true;
    }

    public void makeGardening(ClientRequest clientRequest) {
        if (clientRequest.getStatus().equals(ClientRequest.Status.gardening)) {
            clientRequest.setLandscaperID(this.getUID());
            clientRequest.setStatus(ClientRequest.Status.done);
        }
        else {
            clientRequest.setStatus(ClientRequest.Status.newOne);
        }
    }
}
