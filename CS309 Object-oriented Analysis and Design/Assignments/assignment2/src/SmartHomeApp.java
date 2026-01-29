public class SmartHomeApp {
    public static void main(String[] args) {
        // 1. Create hub
        MonitoringHub hub = new MonitoringHub();

        // 2. Setup for dormitory (you can change this to "laboratory")
        String roomType, roomName;
//        roomType = "dormitory"; roomName = "Dormitory A";
        roomType = "laboratory"; roomName = "Laboratory A";
        RoomConfigFactory factory = FactoryConfig.getFactory(roomType);
        factory.setupPanels(hub, roomName);

        // 3. Simulate normal conditions
        System.out.println("--- Normal Conditions ---");
        hub.reportData("temperature", roomName, 23.5);
        hub.reportData("smoke", roomName, 15.0);

        // 4. Simulate smoke alert
        System.out.println("\n--- Smoke Alert ---");
//        hub.reportData("smoke", roomName, 75.0);
        hub.reportData("smoke", roomName, 12.0);
    }
}
