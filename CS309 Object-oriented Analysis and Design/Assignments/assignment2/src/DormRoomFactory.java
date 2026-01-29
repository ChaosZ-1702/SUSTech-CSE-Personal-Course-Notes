public class DormRoomFactory implements RoomConfigFactory {
    public void setupPanels(MonitoringHub hub, String roomName) {
        // 1. Create RealtimePanel
        RealtimePanel realtimePanel = new RealtimePanel("Dorm Display");
        hub.registerObserver(realtimePanel); // ← Factory registers observer!
        // 2. Create AlarmPanel with relaxed threshold
        AlarmPanel alarmPanel = new AlarmPanel("Dorm Alarm", 50.0);
        hub.registerObserver(alarmPanel); // ← This is the interaction point!
    }
}