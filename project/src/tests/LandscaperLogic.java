package tests;

import model.*;
import org.junit.Test;
import repo.PurchaseReqRepoImpl;
import repo.ResourceRepoImpl;
import repo.UserRepoImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LandscaperLogic {

    private UserRepoImpl users = new UserRepoImpl();
    private PurchaseReqRepoImpl purchases = new PurchaseReqRepoImpl();
    private ResourceRepoImpl resources = new ResourceRepoImpl();

    public LandscaperLogic() {}

    private ClientRequest createClientReq() {
        Integer landscaperID = 2;
        Integer adminID = 1;
        Integer plantID = 2;
        Client client = users.getClientByUserID(landscaperID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        cReq.setStatus(ClientRequest.Status.inProgress);
        cReq.setAdminID(adminID);
        return cReq;
    }

    private PurchaseRequest createPurchaseReq() {
        PurchaseRequest pReq = purchases.findItemBypReqID(3);
        return pReq;
    }

    /*PurchaseRequest pReq3 = new PurchaseRequest(
            pReqID = 3,
            cReqID = 3,
            plantID = 3,
            landscaperID = 2,
            adminID = 1,
            status = PurchaseRequest.Status.inCheck,
            alreadyBought = null
    );
    */


    @Test
    public void MakeGardeningTest() {
        System.out.println("************* MakeGardeningTest *************************************");
        ClientRequest cReq = createClientReq();
        Integer UID = 3;
        Landscaper landscaper = users.getLandscaperByUserID(UID);
        System.out.println("No assigned landscaper before starting of gardening------ " + cReq.getAdminID());
        System.out.println("Status in progress before making the gardening------ " + cReq.getStatus());
        cReq.setAdminID(landscaper.getUID());
        landscaper.makeGardening(cReq);
        System.out.println("Assigned landscaper now is with ID 3. ---------" + cReq.getAdminID());
        System.out.println("Status is done as work is finished. ---------" + cReq.getStatus());

    }

    @Test
    public void CheckCorrectPurchaseTest() {
        PurchaseRequest pReq = createPurchaseReq();
        List<Resource> madePurchase = new ArrayList<>();
        Resource correctResource = resources.getItemByID(1);
        madePurchase.add(correctResource);
        Landscaper checker = users.getLandscaperByUserID(3);
        boolean res = checker.checkPurchase(pReq, madePurchase);
        boolean expected = true;
        assertEquals(res, expected);
    }

    @Test
    public void CheckIncorrectPurchaseTest() {
        PurchaseRequest pReq = createPurchaseReq();
        List<Resource> madePurchase = new ArrayList<>();
        Resource correctResource = resources.getItemByID(2);
        madePurchase.add(correctResource);
        Landscaper checker = users.getLandscaperByUserID(3);
        boolean res = checker.checkPurchase(pReq, madePurchase);
        boolean expected = false;
        assertEquals(res, expected);
    }

    //already bought = null; admin bought incorrect resource with id = 2 --> incorrect purchase
    @Test
    public void CheckInvalidPurchaseRequestTest() {
        PurchaseRequest pReq = createPurchaseReq();
        List<Resource> madePurchase = new ArrayList<>();
        Resource correctResource = resources.getItemByID(2);
        madePurchase.add(correctResource);
        Landscaper checker = users.getLandscaperByUserID(3);
        ClientRequest cReq = createClientReq();

        checker.checkPurchaseRequest(pReq, madePurchase, cReq);

        PurchaseRequest.Status expected = PurchaseRequest.Status.inProgress;
        PurchaseRequest.Status res = pReq.getStatus();
        assertEquals(expected, res);
    }

    //already bought = null; admin bought correct resource with id = 1--> correct purchase
    @Test
    public void CheckCorrectPurchaseRequestTest() {
        ClientRequest cReq = createClientReq();
        PurchaseRequest pReq = createPurchaseReq();
        List<Resource> madePurchase = new ArrayList<>();
        Resource correctResource = resources.getItemByID(1);
        madePurchase.add(correctResource);
        Landscaper checker = users.getLandscaperByUserID(3);

        checker.checkPurchaseRequest(pReq, madePurchase, cReq);

        PurchaseRequest.Status pReqStatusExp = PurchaseRequest.Status.approved;
        PurchaseRequest.Status pReqStatusRes = pReq.getStatus();
        assertEquals(pReqStatusExp, pReqStatusRes);

        //As call for gardening is inside method then by the end of it we expected work to be done
        ClientRequest.Status cReqStatusExp = ClientRequest.Status.done;
        ClientRequest.Status cReqStatusRes = cReq.getStatus();
        assertEquals(cReqStatusExp, cReqStatusRes);
    }
}
