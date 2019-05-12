package repo;

import model.ClientRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientReqRepoImpl implements ClientReqsRepository {

    private static final ClientReqRepoImpl CREQ_REPO = new ClientReqRepoImpl();
    private ClientReqRepoImpl() {}
    public static ClientReqRepoImpl getInstance() {
        return CREQ_REPO;
    }

    private List<ClientRequest> data = testBase();
    private IDgenerator generator = new IDgenerator();

    @Override
    public ClientRequest findItemBycReqID(Integer receivedcReqID) {
        ClientRequest found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getcReqID().equals(receivedcReqID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    @Override
    public List<ClientRequest> filtercReqsByType(ClientRequest.Type type) {
        List<ClientRequest> filtered = new ArrayList<>();
        for (ClientRequest item:
                data) {
            if (item.getType() == type) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    @Override
    public List<ClientRequest> filtercReqsByStatus(ClientRequest.Status status) {
        List<ClientRequest> filtered = new ArrayList<>();
        for (ClientRequest item:
             data) {
            if (item.getStatus() == status) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public Integer getValidClientReqID() {
        Integer validID;
        List<Integer> allIDs = new ArrayList<>();
        for (ClientRequest item:
                data) {
            allIDs.add(item.getcReqID());
        }
        validID = generator.generateNewID(allIDs);
        allIDs.add(validID);
        allIDs.sort(Comparator.naturalOrder());
        return validID;
    }

    @Override
    public boolean add(ClientRequest item) {
        return data.add(item);
    }

    @Override
    public void remove(ClientRequest item) {
        data.remove(item);
    }

    private List<ClientRequest> testBase() {
      List<ClientRequest> data = new ArrayList<>();
        Integer cReqID;
        Integer plantID;
        Integer clientID;
        Integer landscaperID;
        Integer adminID;
        ClientRequest.Status status;
        ClientRequest.Type type;
        ClientRequest cReq1 = new ClientRequest(
                cReqID = 1,
                plantID = 1,
                clientID = 1,
                landscaperID = 1,
                adminID = 1,
                status = ClientRequest.Status.inProgress,
                type = ClientRequest.Type.firstOne
        );
        data.add(cReq1);
        ClientRequest cReq2 = new ClientRequest(
                cReqID = 2,
                plantID = 2,
                clientID = 1,
                landscaperID = 2,
                adminID = 1,
                status = ClientRequest.Status.inPurchase,
                type = ClientRequest.Type.firstOne
        );
        data.add(cReq2);
        ClientRequest cReq3 = new ClientRequest(
                cReqID = 3,
                plantID = 3,
                clientID = 2,
                landscaperID = 1,
                adminID = 1,
                status = ClientRequest.Status.done,
                type = ClientRequest.Type.firstOne
        );
        data.add(cReq3);
        ClientRequest cReq4 = new ClientRequest(
                cReqID = 4,
                plantID = 4,
                clientID = 3,
                landscaperID = 2,
                adminID = 1,
                status = ClientRequest.Status.inProgress,
                type = ClientRequest.Type.firstOne
        );
        data.add(cReq4);
        ClientRequest cReq5 = new ClientRequest(
                cReqID = 5,
                plantID = 5,
                clientID = 3,
                landscaperID = 1,
                adminID = 1,
                status = ClientRequest.Status.inProgress,
                type = ClientRequest.Type.firstOne
        );
        data.add(cReq5);
      return data;
    }
}