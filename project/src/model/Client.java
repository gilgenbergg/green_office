package model;

import repo.ClientReqRepoImpl;
import repo.PlantRepoImpl;
import repo.UserRepoImpl;

import java.util.List;

public class Client extends User {

    private List<Plant> clientPlants;
    private ClientReqRepoImpl clientsReqsBase = ClientReqRepoImpl.getInstance();
    private PlantRepoImpl plantsBase = PlantRepoImpl.getInstance();
    private UserRepoImpl users = UserRepoImpl.getInstance();

    public Client(List<Plant> plants, User user) {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
        this.clientPlants = plantsBase.filterPlantsByUserID(user.getUID());
    }

    @Override
    public Role getRole() {
        return Role.client;
    }

    public Client getClient() {
        return this;
    }

    public List<Plant> clientPlants (Integer clientID) {
        this.clientPlants = plantsBase.filterPlantsByUserID(clientID);
        return clientPlants;
    }

    // if a first one case, then plantID should be null else client need to pass a plantID
    public ClientRequest createClientReq(ClientRequest.Type type, Integer plantID) {
        ClientRequest cReq = null;
        if (type.equals(ClientRequest.Type.firstOne)) {
            Integer cReqID = clientsReqsBase.getValidClientReqID();
            plantID = plantsBase.getValidPlantID();
            cReq = new ClientRequest(cReqID, plantID, getClient().getUID(), null, null,
                    ClientRequest.Status.newOne, type);
        }
        else if (type.equals(ClientRequest.Type.planned)) {
            Integer cReqID = clientsReqsBase.getValidClientReqID();
            Plant plant = plantsBase.findItemByPlantID(plantID);
            cReq = new ClientRequest(cReqID, plantID, getClient().getUID(), null, null,
                    ClientRequest.Status.newOne, type);
        }
        return cReq;
    }

    //opinion true if client accepts landscaper`s work or false if not (will come somehow outside)
    public Feedback makeFeedback(ClientRequest clientRequest, boolean opinion) {
        Feedback feedback = new Feedback(clientRequest.getcReqID(), Feedback.Type.none, "");
        if (clientRequest.getStatus().equals(ClientRequest.Status.done)) {
            if (opinion) {
            feedback.setType(Feedback.Type.accepted);
            feedback.setText("Everything is great! Thanks.");
        }
            else {
                feedback.setText("I am not satisfied. Needs some more work to do here!");
                feedback.setType(Feedback.Type.declined);
                clientRequest.setStatus(ClientRequest.Status.inProgress);
            }
        }
        else {
            feedback.setText("Work is not finished, can`t be accepted!");
            feedback.setType(Feedback.Type.none);
            clientRequest.setStatus(ClientRequest.Status.inProgress);
        }
    return feedback;
    }
}
