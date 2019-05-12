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

public class BP2 {

    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();
    private PurchaseReqRepoImpl purchasesBase = PurchaseReqRepoImpl.getInstance();
    private UserRepoImpl users = UserRepoImpl.getInstance();
    private ResourceRepoImpl resources = ResourceRepoImpl.getInstance();

    public BP2() {}

    /* Закупка растений
    1) Для новых растений в рамках первичного озеленения диспетчер создает карточки в системе,
    присваивая им идентификационные номера.
    2) Диспетчер оформляет заявку на закупку для клиентской заявки на “первичное озеленение”,
    переводя ее в статус: “в закупке”.
    3) По итогу выполнения внешними службами заказа на закупку и получения службой озеленения
    новых растений диспетчер передает заявку на закупку сотруднику службы озеленения для проверки,
    переводя ее в статус “на проверке”.
    4) При подтверждении корректности закупки, сотрудник службы озеленения передает заявку на закупку
    обратно диспетчеру, меняя ее статус на “выполнена”.
    */

    @Test
    public void PurchaseRequestTest() {
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer clientID = 2;

        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        Landscaper landscaper = users.getLandscaperByUserID(landscaperID);

        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, null);
        cReq.setLandscaperID(landscaperID);
        cReq.setAdminID(adminID);
        cReq.setStatus(ClientRequest.Status.inPurchase);

        String lastInspection = null;
        String nextInspection = null;
        Integer instructionID = null;
        List <Resource> resources = testResources();

        Plant plant = new Plant (4, null, lastInspection, nextInspection, instructionID,  resources, clientID);
        plantsBase.add(plant);

        cReq.setStatus(ClientRequest.Status.inPurchase);
        List<Resource> alreadyBought = new ArrayList<>();
        PurchaseRequest pReq = new PurchaseRequest(4, cReq.getcReqID(),
                plant.getPlantID(), cReq.getLandscaperID(), adminID, PurchaseRequest.Status.inProgress,
                alreadyBought);
        // makePurchaseRequest test
        while (cReq.getStatus() == ClientRequest.Status.inPurchase) {
            pReq = admin.makePurchaseRequest(plant, cReq, alreadyBought);
        }
        assertEquals(PurchaseRequest.Status.approved, pReq.getStatus());
    }

    private List<Resource> testResources() {
        List<Resource> testResources = new ArrayList<Resource>();
        Resource resource = resources.getItemByID(2);
        testResources.add(resource);
        return testResources;
    }

}
