package model;

public class Feedback {
    private Integer clientRequestID;
    private Type type;
    private String text;


    public Feedback(Integer clientRequestID, Type type, String text) {
        this.clientRequestID = clientRequestID;
        this.type = type;
        this.text = text;
    }

    public enum Type {
        accepted, declined
    }

    public Type getType() {
        return type;
    }

    public Integer getClientRequestID() {
        return clientRequestID;
    }

    public String getText() {
        return text;
    }

    public void setClientRequestID(Integer clientRequestID) {
        this.clientRequestID = clientRequestID;
    }

    public void setType (Type type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }
}
