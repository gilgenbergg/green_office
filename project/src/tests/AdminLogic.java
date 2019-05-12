package tests;

import model.*;
import org.junit.Test;
import repo.PlantRepoImpl;
import repo.PurchaseReqRepoImpl;
import repo.ResourceRepoImpl;
import repo.UserRepoImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AdminLogic {

    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();
    private PurchaseReqRepoImpl purchasesBase = PurchaseReqRepoImpl.getInstance();
    private UserRepoImpl users = UserRepoImpl.getInstance();
    private ResourceRepoImpl resources = ResourceRepoImpl.getInstance();

    public AdminLogic() {}

    @Test
    public void WorkOnFirstPReqStage1Test(){
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer clientID = 2;
        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, null);
        cReq.setLandscaperID(landscaperID);
        ClientRequest.Status expected = ClientRequest.Status.done;
        admin.workOnClientRequst(cReq);

        ClientRequest.Status res = cReq.getStatus();
        assertEquals(res, expected);
    }

    @Test
    public void WorkOnFirstPReqStage2Test(){
        Integer clientID = 2;
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer plantID = 1;
        Client client = users.getClientByUserID(clientID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, plantID);
        cReq.setLandscaperID(landscaperID);
        Admin admin = users.getAdminByUserID(adminID);
        admin.workOnFirstOneReq(cReq);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.done;
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

    @Test
    public void WorkOnPlannedCReqTest(){
        Integer UID = 2;
        Integer landscaperID = 3;
        Integer adminID = 1;
        Integer plantID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        Admin admin = users.getAdminByUserID(adminID);
        cReq.setLandscaperID(landscaperID);
        cReq.setAdminID(adminID);
        admin.workOnClientRequst(cReq);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.done;
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
