public class SensorEvent {
    String sensorType, roomName;
    double value;

    public SensorEvent(String sensorType, String roomName, double value) {
        this.sensorType = sensorType;
        this.roomName = roomName;
        this.value = value;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getRoomName() {
        return roomName;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return "sensorType: " + sensorType + ", roomName: " + roomName + ", value: " + value;
    }

}
