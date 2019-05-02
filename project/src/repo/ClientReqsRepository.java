package repo;

import model.ClientRequest;
import model.ClientRequest.Status;
import model.ClientRequest.Type;

import java.util.List;

public interface ClientReqsRepository {

    ClientRequest findItemBycReqID(Integer receivedcReqID);

    List<ClientRequest> filtercReqsByType(Type type);

    List<ClientRequest> filtercReqsByStatus(Status status);

    boolean add(ClientRequest item);

    void remove(ClientRequest item);
}
