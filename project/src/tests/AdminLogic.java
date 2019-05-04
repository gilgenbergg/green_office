package tests;

import model.*;
import org.junit.Test;
import repo.PlantRepoImpl;
import repo.PurchaseReqRepoImpl;
import repo.ResourceRepoImpl;
import repo.UserRepoImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AdminLogic {

    private PlantRepoImpl plantsBase = new PlantRepoImpl();
    private PurchaseReqRepoImpl purchasesBase = new PurchaseReqRepoImpl();
    private UserRepoImpl users = new UserRepoImpl();
    private ResourceRepoImpl resources = new ResourceRepoImpl();

    public AdminLogic() throws ParseException {}

    @Test
    public void WorkOnFirstPReqStage1Test() throws ParseException {
        Integer UID = 1;
        Integer plantID = 7;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, plantID);
        Admin admin = users.getAdminByUserID(UID);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.newOne;
        //admin.workOnFirstOneReq(cReq);
        assertEquals(res, expected);
    }

    @Test
    public void WorkOnFirstPReqStage2Test() throws ParseException {
        Integer UID = 1;
        Integer plantID = 7;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, plantID);
        Admin admin = users.getAdminByUserID(UID);
        admin.workOnFirstOneReq(cReq);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.inProgress;
        assertEquals(res, expected);
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

    //with a first try
    /*@Test
    public void MakePReqTest1() throws ParseException {
        PurchaseRequest purchaseRequest = new PurchaseRequest(generatePreqID(), clientRequest.getcReqID(),
                clientRequest.getPlantID(), clientRequest.getLandscaperID(), adminID, PurchaseRequest.Status.inProgress);
        purchaseRequest.setStatus(PurchaseRequest.Status.inCheck);
    }

    //needs to buy more staff
    @Test
    public void MakePReqTest2() throws ParseException {

    }
    */

    @Test
    public void WorkOnPlannedCReqTest() throws ParseException {
        Integer UID = 1;
        Integer plantID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        Admin admin = users.getAdminByUserID(UID);
        admin.workOnClientRequst(cReq);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.inProgress;
        assertEquals(res, expected);
    }

    @Test
    public void GetValidPReqIDTest() {
        Integer res = purchasesBase.getValidPurchaseID();
        Integer expected = 6;
        assertEquals(res, expected);
    }

    @Test
    public void GetValidPlantIDTest() {
        Integer res = plantsBase.getValidPlantID();
        Integer expected = 4;
        assertEquals(res, expected);
    }
}
