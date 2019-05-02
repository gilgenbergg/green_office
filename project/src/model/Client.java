package model;

import java.util.List;

public abstract class Client extends User {

    private List<Plant> plants;
    private Integer clientID;

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

    //TODO: public boolean makePurchaseRequest() {}

    //TODO: public boolean makeFeedback() {}
}
