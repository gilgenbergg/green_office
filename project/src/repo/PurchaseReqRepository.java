package repo;

import model.PurchaseRequest;
import model.PurchaseRequest.Status;

import java.util.List;

public interface PurchaseReqRepository {

    Integer getValidPurchaseID();

    PurchaseRequest findItemBypReqID(Integer receivedpReqID);

    List<PurchaseRequest> filterpReqsByStatus(Status status);

    boolean add(PurchaseRequest item);

    void remove(PurchaseRequest item);
}
