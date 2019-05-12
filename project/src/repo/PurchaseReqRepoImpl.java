package repo;

import model.PurchaseRequest;
import model.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PurchaseReqRepoImpl implements PurchaseReqRepository {

    private static final PurchaseReqRepoImpl PREQ_REPO = new PurchaseReqRepoImpl();
    private PurchaseReqRepoImpl() {}
    public static PurchaseReqRepoImpl getInstance() {
        return PREQ_REPO;
    }

    private List<PurchaseRequest> data = testBase();
    private IDgenerator generator = new IDgenerator();

    public Integer getValidPurchaseID() {
        Integer validID;
        List<Integer> allIDs = new ArrayList<>();
        for (PurchaseRequest item:
                data) {
            allIDs.add(item.getpReqID());
        }
        validID = generator.generateNewID(allIDs);
        allIDs.add(validID);
        allIDs.sort(Comparator.naturalOrder());
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
        List<Resource> alreadyBought;
        PurchaseRequest.Status status;
        PurchaseRequest pReq1 = new PurchaseRequest(
                pReqID = 1,
                cReqID = 1,
                plantID = 1,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress,
                alreadyBought = null
        );
        data.add(pReq1);
        PurchaseRequest pReq2 = new PurchaseRequest(
                pReqID = 2,
                cReqID = 2,
                plantID = 1,
                landscaperID = 1,
                adminID = 1,
                status = PurchaseRequest.Status.approved,
                alreadyBought = null
        );
        data.add(pReq2);
        PurchaseRequest pReq3 = new PurchaseRequest(
                pReqID = 3,
                cReqID = 3,
                plantID = 3,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inCheck,
                alreadyBought = null
        );
        data.add(pReq3);
        PurchaseRequest pReq4 = new PurchaseRequest(
                pReqID = 4,
                cReqID = 4,
                plantID = 3,
                landscaperID = 1,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress,
                alreadyBought = null
        );
        data.add(pReq4);
        PurchaseRequest pReq5 = new PurchaseRequest(
                pReqID = 5,
                cReqID = 5,
                plantID = 1,
                landscaperID = 2,
                adminID = 1,
                status = PurchaseRequest.Status.inProgress,
                alreadyBought = null
        );
        data.add(pReq5);
        return data;
    }
}
