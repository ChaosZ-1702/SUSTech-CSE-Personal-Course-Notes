public class RealtimePanel implements Observer<SensorEvent> {
    String panelName;

    public RealtimePanel(String panelName) {
        this.panelName = panelName;
    }

    @Override
    public void update(SensorEvent event) {
        System.out.println("[" + this.panelName + "] " + event.sensorType + " in " + event.roomName + ": " + event.value);
    }
}
