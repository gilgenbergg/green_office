package model;

import data.*;

import java.sql.SQLException;
import java.util.*;

public class Admin extends User {

    private Integer adminID;
    private PlantsMapper plantsBase = new PlantsMapper();
    private PReqsMapper purchasesBase = new PReqsMapper();
    private UsersMapper users = new UsersMapper();
    private ResourcesMapper resources = new ResourcesMapper();
    private CReqsMapper cReqsMapper = new CReqsMapper();

    public Admin(Integer adminID, User user) throws SQLException, ClassNotFoundException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole(), user.getAuthDataID());
        this.adminID = adminID;
    }

    @Override
    public Role getRole() {
        return Role.admin;
    }

    public void workOnClientRequest(ClientRequest clientRequest, String plantType) throws SQLException, ClassNotFoundException {
        while (cReqsMapper.findItemByID(clientRequest.getCReqID()).getStatus().equals(ClientRequest.Status.newOne)) {
            ClientRequest.Type cReqType = clientRequest.getType();
            if (cReqType == ClientRequest.Type.planned) {
                Integer assignedLandscaper = 3;
                Landscaper gardener = users.getLandscaperByUserID(assignedLandscaper);
                Integer plantID = cReqsMapper.findItemByID(clientRequest.getCReqID()).getPlantID();
                Plant plant = plantsBase.findItemByPlantID(plantID);
                //String nextDate = addNextDate();
                //plantsBase.setDateOfNextVisit(plant.getPlantID(), nextDate);
                cReqsMapper.updateStatus(clientRequest.getCReqID(), ClientRequest.Status.gardening);
                gardener.makeGardening(cReqsMapper.findItemByID(clientRequest.getCReqID()));
            } else {
                workOnFirstOneReq(clientRequest, plantType);
            }
        }
    }

    private String addNextDate() {
        //Scanner in = new Scanner(System.in);
        //String nextDate = in.next();
        String nextDate = "12.12.2020";
        return nextDate;
    }

    private List<Resource> testResources() throws SQLException {
        List<Resource> testResources = new ArrayList<Resource>();
        Resource resource = resources.getResourceByID(2);;
        testResources.add(resource);
        return testResources;
    }

    public void workOnFirstOneReq(ClientRequest clientRequest, String plantType) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant(null, plantType, null, null, null, null,
                clientRequest.getClientID());
        plant = plantsBase.addNewPlant(plant);
        cReqsMapper.updatePlant(plant.getPlantID(), cReqsMapper.findItemByID(clientRequest.getCReqID()));
        cReqsMapper.updateStatus(clientRequest.getCReqID(), ClientRequest.Status.inPurchase);
        cReqsMapper.updateAdminID(clientRequest.getCReqID(), this.adminID);
        List<Resource> alreadyBought = new ArrayList<>();
        clientRequest = cReqsMapper.findItemByID(clientRequest.getCReqID());
        while (cReqsMapper.findItemByID(clientRequest.getCReqID()).getStatus() != ClientRequest.Status.done) {
            PurchaseRequest pReq = makePurchaseRequest(plant, cReqsMapper.findItemByID(clientRequest.getCReqID()), alreadyBought);
        }
    }

    public PurchaseRequest makePurchaseRequest(Plant plant, ClientRequest clientRequest, List<Resource> alreadyBought) throws SQLException, ClassNotFoundException {
        PurchaseRequest purchaseStart = new PurchaseRequest(null, clientRequest.getCReqID(),
                plant.getPlantID(), null, adminID, null);
        Integer pReqID = purchasesBase.addPReq(purchaseStart);
        //let`s for now we have just one landscaper
        Integer assignedLandscaper = 3;
        Landscaper checker = users.getLandscaperByUserID(assignedLandscaper);
        purchasesBase.updateLandscaperID(pReqID, assignedLandscaper);
        List<Resource> boughtByAdmin = testPurchase(clientRequest, alreadyBought);
        //here landscaper approves purchase or not
        purchasesBase.updateStatus(pReqID, PurchaseRequest.Status.inCheck);
        PurchaseRequest purchaseReq = purchasesBase.findItemByID(pReqID);
        checker.checkPurchaseRequest(pReqID, boughtByAdmin, clientRequest);
        while (purchasesBase.findItemByID(pReqID).getStatus() != PurchaseRequest.Status.approved) {
            List<Resource> previouslyBought = alreadyBought;
            List<Resource> newPurchase = buySomeMoreStaff(previouslyBought);
            purchasesBase.updateStatus(pReqID, PurchaseRequest.Status.inCheck);
            checker.checkPurchaseRequest(pReqID, newPurchase, clientRequest);
        }
        cReqsMapper.updateStatus(clientRequest.getCReqID(), ClientRequest.Status.gardening);
        cReqsMapper.updateLandscaperID(clientRequest.getCReqID(), assignedLandscaper);
        checker.makeGardening(cReqsMapper.findItemByID(clientRequest.getCReqID()));
        purchaseReq = purchasesBase.findItemByID(pReqID);
        return purchaseReq;
    }

    // набор для тестовой дозакупки
    // get from UI

    List<Resource> buySomeMoreStaff(List<Resource> alreadyBought) throws SQLException {
        Resource res1 = resources.getResourceByID(1);
        Resource res3 = resources.getResourceByID(3);
        Resource res4 = resources.getResourceByID(4);
        Resource res5 = resources.getResourceByID(5);
        alreadyBought.add(res1);
        alreadyBought.add(res3);
        alreadyBought.add(res4);
        alreadyBought.add(res5);
        return alreadyBought;
    }

    List<Resource> testPurchase(ClientRequest clientRequest, List<Resource> alreadyBought) throws SQLException {
        Integer plantID = clientRequest.getPlantID();
        //toPurchase = resources.findResourcesByPlantID(plantID);
        Resource res1 = resources.getResourceByID(2);
        alreadyBought.add(res1);
        return alreadyBought;
    }

}


