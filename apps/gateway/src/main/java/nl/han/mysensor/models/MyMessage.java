package nl.han.mysensor.models;

import com.google.gson.annotations.SerializedName;
import nl.han.mysensor.models.myenums.MyCommand;
import nl.han.mysensor.models.myenums.MyDataTypes;
import nl.han.mysensor.models.myenums.MyInternal;
import nl.han.mysensor.models.myenums.MyPresentationType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Abstract MySensor message
 *
 * @author Thomas
 * @since 0.1
 */
@Entity
public abstract class MyMessage {

    @Id
    @SerializedName("_id")
    private ObjectId id;

    private Long nodeId;
    private Long childSensorId;
    private MyCommand command;
    private boolean ack;
    private String payload;


    public MyMessage(Long nodeId, Long childSensorId, MyCommand command, boolean ack, String payload) {
        this.nodeId = nodeId;
        this.childSensorId = childSensorId;
        this.command = command;
        this.ack = ack;
        this.payload = payload;
    }

    public MyMessage() {
    }

    private MyMessage(Builder builder) {
        this.nodeId = builder.nodeId;
        this.childSensorId = builder.childSensorId;
        this.command = builder.command;
        this.ack = builder.ack;
        this.payload = builder.payload;
    }

    public static Builder newMyMessage() {
        return new Builder();
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public Long getChildSensorId() {
        return childSensorId;
    }

    public MyCommand getCommand() {
        return command;
    }

    public boolean isAck() {
        return ack;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "id=" + id +
                ", nodeId=" + nodeId +
                ", childSensorId=" + childSensorId +
                ", command=" + command +
                ", ack=" + ack +
                ", payload='" + payload + '\'' +
                '}';
    }

    public static final class Builder {
        private Long nodeId;
        private Long childSensorId;
        private MyCommand command;
        private boolean ack;
        private String payload = "";

        private MyPresentationType presentationType;
        private MyInternal internal;
        private MyDataTypes req;
        private MyDataTypes set;

        private Builder() {
        }

        public MyMessage build() {
            if (this.command == MyCommand.PRESENTATION) {
                return new MyPresentationMessage(this.nodeId, this.childSensorId, this.command, this.ack, this.payload, this.presentationType);
            } else if (this.command == MyCommand.INTERNAL) {
                return new MyInternalMessage(this.nodeId, this.childSensorId, this.command, this.ack, this.payload, this.internal);
            } else if (this.command == MyCommand.REQ) {
                return new MyReqMessage(this.nodeId, this.childSensorId, this.command, this.ack, this.payload, this.req);
            } else if (this.command == MyCommand.SET) {
                return new MySetMessage(this.nodeId, this.childSensorId, this.command, this.ack, this.payload, this.set);
            }
            throw new IllegalStateException("Could not create MyMessage object!");
        }

        public Builder nodeId(Long nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public Builder childSensorId(Long childSensorId) {
            this.childSensorId = childSensorId;
            return this;
        }

        public Builder command(MyCommand command) {
            this.command = command;
            return this;
        }

        public Builder ack(boolean ack) {
            this.ack = ack;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder presentationType(MyPresentationType presentationType) {
            this.presentationType = presentationType;
            return this;
        }

        public Builder internal(MyInternal internal) {
            this.internal = internal;
            return this;
        }

        public Builder setDataType(MyDataTypes dataTypes) {
            this.set = dataTypes;
            return this;
        }

        public Builder reqDataType(MyDataTypes dataTypes) {
            this.req = dataTypes;
            return this;
        }
    }
}
