public class FactoryConfig {
    public static RoomConfigFactory getFactory(String roomType) {
        if (roomType.equalsIgnoreCase("dormitory")) {
            return new DormRoomFactory();
        } else if (roomType.equalsIgnoreCase("laboratory")) {
            return new LabRoomFactory();
        } else {
            return new DormRoomFactory(); // Default
        }
    }
}