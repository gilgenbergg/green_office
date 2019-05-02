package repo;

import model.PurchaseRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PurchaseReqRepoImpl implements PurchaseReqRepository {

    private List<PurchaseRequest> data = testBase();

    public Integer getValidPurchaseID() {
        Integer validID;
        List<Integer> allIDs = new ArrayList<>();
        for (PurchaseRequest item:
                data) {
            allIDs.add(item.getpReqID());
        }
        if (allIDs.size() == 0) {
            validID = 1;
        }
        else {
            Integer prev = 0;
            for (Integer id:
                    allIDs) {
                if (id < allIDs.size()) {
                    if (id != prev + 1) {
                        validID = prev + 1;
                        prev = validID;
                        allIDs.add(validID);
                        allIDs.sort(Comparator.naturalOrder());
                    }
                    else if (id == prev + 1) {
                        prev = id;
                    }
                }
            }
            validID = prev + 1;
            allIDs.add(validID);
            allIDs.sort(Comparator.naturalOrder());
        }
        return validID;
    }

    @Override
    public PurchaseRequest findItemBypReqID(Integer receivedpReqID) {
        PurchaseRequest found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getpReqID().equals(receivedpReqID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    @Override
    public List<PurchaseRequest> filterpReqsByStatus(PurchaseRequest.Status status) {
        List<PurchaseRequest> filtered = new ArrayList<>();
        for (PurchaseRequest item:
                data) {
            if (item.getStatus() == status) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    @Override
    public boolean add(PurchaseRequest item) {
        return data.add(item);
    }

    @Override
    public void remove(PurchaseRequest item) {
        data.remove(item);
    }

    private List<PurchaseRequest> testBase() {
        List<PurchaseRequest> data = new ArrayList<>();
        Integer pReqID;
        Integer cReqID;
        Integer plantID;
        Integer landscaperID;
        Integer adminID;
        PurchaseRequest.Status status;
        PurchaseRequest pReq1 = new PurchaseRequest(
                pReqID = 1,
                cReqID = 1,
                plantID = 1,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress
        );
        data.add(pReq1);
        PurchaseRequest pReq2 = new PurchaseRequest(
                pReqID = 2,
                cReqID = 2,
                plantID = 1,
                landscaperID = 1,
                adminID = 1,
                status = PurchaseRequest.Status.approved
        );
        data.add(pReq2);
        PurchaseRequest pReq3 = new PurchaseRequest(
                pReqID = 3,
                cReqID = 3,
                plantID = 3,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inCheck
        );
        data.add(pReq3);
        PurchaseRequest pReq4 = new PurchaseRequest(
                pReqID = 4,
                cReqID = 4,
                plantID = 3,
                landscaperID = 1,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress
        );
        data.add(pReq4);
        PurchaseRequest pReq5 = new PurchaseRequest(
                pReqID = 5,
                cReqID = 5,
                plantID = 1,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress
        );
        data.add(pReq5);
        return data;
    }
}
