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

public class BP1 {

    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();
    private PurchaseReqRepoImpl purchasesBase = PurchaseReqRepoImpl.getInstance();
    private UserRepoImpl users = UserRepoImpl.getInstance();
    private ResourceRepoImpl resources = ResourceRepoImpl.getInstance();

    public BP1() {}

    /*
    1) Клиент создает заявку (статус заявки: новая), помечая ее тип (“первичное озеленение”,
    т.е. с установкой нового растения и создания нового объекта в БД службы по озеленению или
    “плановый осмотр” для уже заведенных в системе объектов).
    2)  обрабатывает заявку: в случае заявки на первичное озеленение переводит заявку в статус “закупка”,
    оформляя заказ на покупку требуемых растений в соответствии с заказом и заводит новые объекты в системе.
    3) По результатам произведенной закупки диспетчер устанавливает даты выезда по работе над заявками,
    описывая в карточках растений необходимый для работы с ними инвентарь.
    4) Диспетчер переводит заявку в статус: “в работе”.
    5) Сотрудник выезжает в офис клиента и осуществляет установку растений, внося информацию о дате
    осмотра и его результатах,фиксируя рекомендованную дату следующего выезда, меняя статус заявки
    на “выполнена” Клиент принимает/не принимает выполненную работу.
     */

    @Test
    public void ClientFirstRequestTest() {
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer clientID = 2;

        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        Landscaper landscaper = users.getLandscaperByUserID(landscaperID);

        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, null);
        cReq.setLandscaperID(landscaperID);
        cReq.setAdminID(adminID);

        // 1 //
        assertEquals(cReq.getStatus(), ClientRequest.Status.newOne);

        // 2 //
        //TODO:check this test more
        admin.workOnClientRequst(cReq);
        ClientRequest.Status res1 = cReq.getStatus();
        ClientRequest.Status exp1 = ClientRequest.Status.done;
        assertEquals(res1, exp1);

        // 3 (positive feedback) //
        Feedback feedback = client.makeFeedback(cReq, true);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.accepted;
        assertEquals(expected, res);
        assertEquals(cReq.getStatus(), ClientRequest.Status.done);
    }

    @Test
    public void ClientPlannedRequestTest() {
        Integer adminID = 1;
        Integer landscaperID = 3;
        Integer clientID = 2;
        Integer plantID = 1;

        Admin admin = users.getAdminByUserID(adminID);
        Client client = users.getClientByUserID(clientID);
        Landscaper landscaper = users.getLandscaperByUserID(landscaperID);
        Plant plant = plantsBase.findItemByPlantID(plantID);

        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        cReq.setLandscaperID(landscaperID);
        cReq.setAdminID(adminID);

        // 1 //
        assertEquals(cReq.getStatus(), ClientRequest.Status.newOne);

        // 2 //
        admin.workOnClientRequst(cReq);
        assertEquals(cReq.getStatus(), ClientRequest.Status.done);

        // 3 (negative feedback) //
        Feedback feedback = client.makeFeedback(cReq, false);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.declined;
        assertEquals(res, expected);
        assertEquals(cReq.getStatus(), ClientRequest.Status.inProgress);
    }

}
