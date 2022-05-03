package app.flipn.airqualitymqttconsumer.model;

import java.util.StringJoiner;

public class GenericMessage {

    private int id;

    private String uuid;

    private String payload;

    private int qos;

    private String description;

    private long timestamp;

    public GenericMessage(int id, String uuid, String payload, int qos, String description, long timestamp) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.payload = payload;
        this.qos = qos;
        this.description = description;
        this.timestamp = timestamp;
    }

    public GenericMessage() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GenericMessage.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("uuid='" + uuid + "'")
                .add("payload='" + payload + "'")
                .add("qos=" + qos)
                .add("description='" + description + "'")
                .add("timestamp=" + timestamp)
                .toString();
    }
}