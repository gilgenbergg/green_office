package model;

import repo.PlantRepoImpl;
import repo.UserRepoImpl;

import java.util.List;

public abstract class Landscaper extends User {

    UserRepoImpl userRepo = new UserRepoImpl();
    PlantRepoImpl plantBase = new PlantRepoImpl();

    public Landscaper landscaper;

    public Landscaper(User user) {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
        this.landscaper = getLandscaper(user);
    }

    @Override
    public Role getRole() {
        return Role.landscaper;
    }

    public User getUser(Integer userID) {
        return userRepo.findItemByUID(userID);
    }

    public Landscaper getLandscaper(User user) {
        return landscaper;
    }

    public void checkPurchaseRequest(PurchaseRequest purchaseRequest, List<Resource> boughtResources){
        boolean checkResult;
        if (purchaseRequest.getStatus() == PurchaseRequest.Status.inCheck) {
            checkResult = checkPurchase(purchaseRequest, boughtResources);
            if (checkResult) {
                purchaseRequest.setStatus(PurchaseRequest.Status.approved);
            }
            else {
                purchaseRequest.setStatus(PurchaseRequest.Status.inProgress);
            }
        }
    }

    private boolean checkPurchase(PurchaseRequest purchaseRequest, List<Resource> boughtResources) {
        List<Resource> neededResources = plantBase.findResourcesByPlantID(purchaseRequest.getPlantID());
        for (Resource item:
             neededResources) {
            if (!boughtResources.contains(item)) {
               return false;
            }
        }
        return true;
    }
}
