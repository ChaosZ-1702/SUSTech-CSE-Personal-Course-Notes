public class LabRoomFactory implements RoomConfigFactory {
    public void setupPanels(MonitoringHub hub, String roomName) {
        // 1. Create RealtimePanel
        RealtimePanel realtimePanel = new RealtimePanel("Lab Display");
        hub.registerObserver(realtimePanel); // ← Factory registers observer!
        // 2. Create AlarmPanel with relaxed threshold
        AlarmPanel alarmPanel = new AlarmPanel("Lab Alarm", 20.0);
        hub.registerObserver(alarmPanel); // ← This is the interaction point!
    }
}
