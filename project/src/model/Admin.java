package model;

import repo.*;

import java.text.ChoiceFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Admin extends User {

    private Integer adminID;
    private PlantRepoImpl plantsBase = new PlantRepoImpl();
    private PurchaseReqRepoImpl purchasesBase = new PurchaseReqRepoImpl();
    private UserRepoImpl users = new UserRepoImpl();
    private ResourceRepoImpl resources = new ResourceRepoImpl();

    public Admin(Integer adminID, User user) throws ParseException {
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

    public void workOnClientRequst(ClientRequest clientRequest) throws ParseException {
        if (clientRequest.getStatus().equals(ClientRequest.Status.newOne)) {
            ClientRequest.Type cReqType = clientRequest.getType();
            if (cReqType == ClientRequest.Type.planned) {
                Integer plantID = clientRequest.getPlantID();
                Plant plant = plantsBase.findItemByPlantID(plantID);
                String nextDate = addNextDate();
                plantsBase.setDateOfNextVisit(plant, nextDate);
                clientRequest.setStatus(ClientRequest.Status.inProgress);
            } else {
                workOnFirstOneReq(clientRequest);
            }
        }
    }

    private String addNextDate() {
        Scanner in = new Scanner(System.in);
        String nextDate = in.next();
        return nextDate;
    }

    public void workOnFirstOneReq(ClientRequest clientRequest) throws ParseException {
        Integer validID = generatePlantID();
        String type = null;
        String lastInspection = null;
        String nextInspection = null;
        Integer instructionID = null;
        List <Resource> resources = null;
        Integer clientID = clientRequest.getClientID();
        Plant plant = new Plant (validID, type, lastInspection, nextInspection, instructionID,  resources, clientID);
        plantsBase.add(plant);
        clientRequest.setStatus(ClientRequest.Status.inPurchase);
        boolean purchased = false;
        List<Resource> alreadyBought = null;
        while (clientRequest.getStatus() == ClientRequest.Status.inPurchase) {
            PurchaseRequest pReq = makePurchaseRequest(plant, clientRequest, alreadyBought);
            if (pReq.getStatus().equals(PurchaseRequest.Status.approved)) {
                clientRequest.setStatus(ClientRequest.Status.inProgress);
            }
        }
    }

    private PurchaseRequest makePurchaseRequest(Plant plant, ClientRequest clientRequest, List<Resource> alreadyBought) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(generatePreqID(), clientRequest.getcReqID(),
                clientRequest.getPlantID(), clientRequest.getLandscaperID(), adminID, PurchaseRequest.Status.inProgress,
                alreadyBought);
        purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
        //here landscaper approves purchase or not
        Landscaper checker = (Landscaper) users.findItemByUID(purchaseRequest.getLandscaperID());
        List<Resource> boughtByAdmin = testPurchase(clientRequest, alreadyBought);
        checker.checkPurchaseRequest(purchaseRequest, boughtByAdmin, clientRequest);
        while (purchaseRequest.getStatus() != PurchaseRequest.Status.approved) {
            //List<Resource> newPurchase = testSomeMoreStaff(plant.getPlantID(), boughtByAdmin);
            alreadyBought = purchaseRequest.getAlreadyBought();
            List<Resource> newPurchase = testSomeMoreStaff();
            purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
            checker.checkPurchaseRequest(purchaseRequest, newPurchase, clientRequest);
        }
        return purchaseRequest;
        //checker.makeGardening(clientRequest);
    }

    // набор для тестовой дозакупки

    List<Resource> testSomeMoreStaff() {
        Resource res1 = resources.getItemByID(1);
        Resource res3 = resources.getItemByID(3);
        Resource res4 = resources.getItemByID(4);
        Resource res5 = resources.getItemByID(5);
        List<Resource> alreadyBought = null;
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
        return purchasesBase.getValidPurchaseID();
    }

    public Integer generatePlantID() {
        return plantsBase.getValidPlantID();
    }
}


