public class AlarmPanel implements Observer<SensorEvent> {
    String panelName;
    double smokeThreshold;

    public AlarmPanel(String panelName, double smokeThreshold) {
        this.panelName = panelName;
        this.smokeThreshold = smokeThreshold;
    }

    @Override
    public void update(SensorEvent event) {
        if (event.sensorType.equalsIgnoreCase("smoke") && event.value > this.smokeThreshold)
            System.out.println("🚨ALARM [" + this.panelName + "]! High " + event.sensorType + " detected: " + event.value);
    }
}
