import java.util.ArrayList;

public class MonitoringHub implements Subject<SensorEvent> {
    ArrayList<Observer<SensorEvent>> registeredObservers = new ArrayList<>();

    @Override
    public void registerObserver(Observer<SensorEvent> observer) {
        if (!this.registeredObservers.contains(observer)) this.registeredObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer<SensorEvent> observer) {
        while (this.registeredObservers.contains(observer)) this.registeredObservers.remove(observer);
    }

    @Override
    public void notifyObservers(SensorEvent event) {
        for (Observer<SensorEvent> observer : registeredObservers) observer.update(event);
    }

    public void reportData(String sensorType, String roomName, double value) {
        this.notifyObservers(new SensorEvent(sensorType, roomName, value));
    }
}
