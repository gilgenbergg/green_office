package model;

import repo.*;

import java.util.*;

public class Admin extends User {

    private Integer adminID;
    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();
    private PurchaseReqRepoImpl purchasesBase = PurchaseReqRepoImpl.getInstance();
    private UserRepoImpl users = UserRepoImpl.getInstance();
    private ResourceRepoImpl resources = ResourceRepoImpl.getInstance();

    public Admin(Integer adminID, User user) {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
        this.adminID = adminID;
    }

    @Override
    public Role getRole() {
        return Role.admin;
    }

    public void workOnClientRequst(ClientRequest clientRequest) {
        while (clientRequest.getStatus().equals(ClientRequest.Status.newOne)) {
            ClientRequest.Type cReqType = clientRequest.getType();
            if (cReqType == ClientRequest.Type.planned) {
                Landscaper gardener = users.getLandscaperByUserID(clientRequest.getLandscaperID());
                Integer plantID = clientRequest.getPlantID();
                Plant plant = plantsBase.findItemByPlantID(plantID);
                String nextDate = addNextDate();
                plantsBase.setDateOfNextVisit(plant, nextDate);
                clientRequest.setStatus(ClientRequest.Status.gardening);
                gardener.makeGardening(clientRequest);
            } else {
                workOnFirstOneReq(clientRequest);
            }
        }
    }

    private String addNextDate() {
        //Scanner in = new Scanner(System.in);
        //String nextDate = in.next();
        String nextDate = "12.12.2020";
        return nextDate;
    }

    private List<Resource> testResources() {
        List<Resource> testResources = new ArrayList<Resource>();
        Resource resource = resources.getItemByID(2);
        testResources.add(resource);
        return testResources;
    }

    public void workOnFirstOneReq(ClientRequest clientRequest) {
        Integer validID = generatePlantID();
        String type = null;
        String lastInspection = null;
        String nextInspection = null;
        Integer instructionID = null;
        List <Resource> resources = testResources();
        Integer clientID = clientRequest.getClientID();
        Plant plant = new Plant (validID, type, lastInspection, nextInspection, instructionID,  resources, clientID);
        plantsBase.add(plant);
        clientRequest.setStatus(ClientRequest.Status.inPurchase);
        clientRequest.setAdminID(this.adminID);
        List<Resource> alreadyBought = new ArrayList<>();
        while (clientRequest.getStatus() == ClientRequest.Status.inPurchase) {
            PurchaseRequest pReq = makePurchaseRequest(plant, clientRequest, alreadyBought);
            //if (pReq.getStatus().equals(PurchaseRequest.Status.approved)) {
             //   clientRequest.setStatus(ClientRequest.Status.inProgress);
            //}
        }
    }

    public PurchaseRequest makePurchaseRequest(Plant plant, ClientRequest clientRequest, List<Resource> alreadyBought) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(generatePreqID(), clientRequest.getcReqID(),
                clientRequest.getPlantID(), clientRequest.getLandscaperID(), adminID, PurchaseRequest.Status.inProgress,
                alreadyBought);
        Landscaper checker = users.getLandscaperByUserID(clientRequest.getLandscaperID());
        List<Resource> boughtByAdmin = testPurchase(clientRequest, alreadyBought);
        //here landscaper approves purchase or not
        purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
        checker.checkPurchaseRequest(purchaseRequest, boughtByAdmin, clientRequest);
        while (purchaseRequest.getStatus() != PurchaseRequest.Status.approved) {
            alreadyBought = purchaseRequest.getAlreadyBought();
            List<Resource> newPurchase = buySomeMoreStaff(alreadyBought);
            purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
            checker.checkPurchaseRequest(purchaseRequest, newPurchase, clientRequest);
        }
        //sth below is already done by landscaper while gardening
        //clientRequest.setStatus(ClientRequest.Status.inProgress);
        //checker.makeGardening(clientRequest);
        return purchaseRequest;
    }

    // набор для тестовой дозакупки

    List<Resource> buySomeMoreStaff(List<Resource> alreadyBought) {
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
        Integer plantID = clientRequest.getPlantID();
        //toPurchase = resources.findResourcesByPlantID(plantID);
        Resource res1 = resources.getItemByID(2);
        alreadyBought.add(res1);
        return alreadyBought;
    }

    private Integer generatePreqID() {
        return purchasesBase.getValidPurchaseID();
    }

    public Integer generatePlantID() {
        return plantsBase.getValidPlantID();
    }
}


