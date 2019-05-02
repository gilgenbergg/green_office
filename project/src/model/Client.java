package model;

import repo.ClientReqRepoImpl;
import repo.PlantRepoImpl;

import java.util.List;

public abstract class Client extends User {

    private List<Plant> plants;
    private Integer clientID;
    private ClientReqRepoImpl clientsReqsBase;
    private PlantRepoImpl plantsBase;

    public Client(Integer clientID, List<Plant> plants, User user) {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole());
        this.clientID = clientID;
        this.plants = plants;
    }

    @Override
    public Role getRole() {
        return Role.client;
    }

    public User getUser() {
        return this;
    }

    public List<Plant> clientPlants (Integer clientID) {
        List<Plant> clientPlants = plantsBase.filterPlantsByUserID(clientID);
        return clientPlants;
    }

    // if a first one case, then plantID should be null else client need to pass a plantID
    public ClientRequest createClientReq(ClientRequest.Type type, Integer plantID) {
        ClientRequest cReq = null;
        if (type.equals(ClientRequest.Type.firstOne)) {
            Integer cReqID = clientsReqsBase.getValidClientReqID();
            plantID = plantsBase.getValidPlantID();
            cReq = new ClientRequest(cReqID, plantID, clientID, null, null,
                    ClientRequest.Status.newOne, type);
        }
        else if (type.equals(ClientRequest.Type.planned)) {
            Integer cReqID = clientsReqsBase.getValidClientReqID();
            Plant plant = plantsBase.findItemByPlantID(plantID);
            cReq = new ClientRequest(cReqID, plantID, clientID, null, null,
                    ClientRequest.Status.newOne, type);
        }
        return cReq;
    }

    //opinion true if client accepts landscaper`s work or false if not (will come somehow outside)
    public void makeFeedback(ClientRequest clientRequest, boolean opinion) {
        Feedback feedback = null;
        if (clientRequest.getStatus().equals(ClientRequest.Status.done)) {
            if (opinion) {
            feedback.setType(Feedback.Type.accepted);
            feedback.setText("Everything is great! Thanks.");
        }
            else {
                feedback.setText("I am not satisfied. Needs some more work to do here!");
                feedback.setType(Feedback.Type.declined);
                clientRequest.setStatus(ClientRequest.Status.inProgress);
                clientRequest.setStatus(ClientRequest.Status.newOne);
                clientRequest.setType(ClientRequest.Type.planned);
            }
        }
    }
}
