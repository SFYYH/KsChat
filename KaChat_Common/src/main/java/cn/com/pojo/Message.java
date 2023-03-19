package cn.com.pojo;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;
    private String fromID;
    private String toID;

    private Object data;

    public Message(MessageType messageType, String fromID, String toID, Object data) {
        this.messageType = messageType;
        this.fromID = fromID;
        this.toID = toID;
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        toID = toID;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
