package tests;

import data.*;
import model.*;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class BP extends DBinit {

    private PlantsMapper plantsBase = new PlantsMapper();
    private PReqsMapper purchasesBase = new PReqsMapper();
    private UsersMapper users = new UsersMapper();
    private ResourcesMapper resources = new ResourcesMapper();
    private CReqsMapper cReqs = new CReqsMapper();

    public BP() throws SQLException, ClassNotFoundException {}

    @Test
    public void ClientFirstRequestTest() throws SQLException, ClassNotFoundException {
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer clientID = 2;

        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        Landscaper landscaper = users.getLandscaperByUserID(landscaperID);

        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, clientID);
        cReqs.updateAdminID(cReq.getCReqID(), adminID);
        cReqs.updateLandscaperID(cReq.getCReqID(), landscaperID);
        ClientRequest.Status expectedStatus = cReqs.findItemByID(cReq.getCReqID()).getStatus();
        // 1 //
        assertEquals(expectedStatus, ClientRequest.Status.newOne);

        // 2 //
        String plantType = "georgin";
        admin.workOnClientRequest(cReq, plantType);
        ClientRequest.Status res1 = cReqs.findItemByID(cReq.getCReqID()).getStatus();
        ClientRequest.Status exp1 = ClientRequest.Status.done;
        assertEquals(res1, exp1);

        // 3 (positive feedback) //
        Feedback feedback = client.makeFeedback(cReqs.findItemByID(cReq.getCReqID()), true);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.accepted;
        assertEquals(expected, res);
        assertEquals(cReqs.findItemByID(cReq.getCReqID()).getStatus(), ClientRequest.Status.done);
    }

    @Test
    public void ClientPlannedRequestTest() throws SQLException, ClassNotFoundException {
        Integer adminID = 1;
        Integer landscaperID = 1;
        Integer clientID = 2;
        Integer plantID = 1;

        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        Landscaper landscaper = users.getLandscaperByUserID(landscaperID);
        Plant plant = plantsBase.findItemByPlantID(plantID);

        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        cReqs.updateAdminID(cReq.getCReqID(), adminID);
        cReqs.updateLandscaperID(cReq.getCReqID(), landscaperID);

        // 1 //
        assertEquals(cReqs.findItemByID(cReq.getCReqID()).getStatus(), ClientRequest.Status.newOne);

        // 2 //
        String plantType = plantsBase.findItemByPlantID(1).getType();
        cReqs.updatePlant(1,cReqs.findItemByID(cReq.getCReqID()));
        admin.workOnClientRequest(cReq, plantType);
        assertEquals(cReqs.findItemByID(cReq.getCReqID()).getStatus(), ClientRequest.Status.done);

        // 3 (negative feedback) //
        Feedback feedback = client.makeFeedback(cReqs.findItemByID(cReq.getCReqID()), false);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.declined;
        assertEquals(res, expected);
    }
}
