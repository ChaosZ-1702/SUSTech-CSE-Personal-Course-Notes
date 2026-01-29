public interface RoomConfigFactory {
    /**
     * Create and register display panels for this room type
     * IMPORTANT: Panels are automatically registered with hub
     * @param hub The monitoring hub to observe
     * @param roomName The name of the room
     */
    void setupPanels(MonitoringHub hub, String roomName);
}