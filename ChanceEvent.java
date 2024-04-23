
public class ChanceEvent {
    String name;
    String description;
    Integer gain;
    int location;

    public ChanceEvent(String name, String description, int gain, int newPosition) {
        this.name = name;
        this.description = description;
        this.gain = gain;
        location = newPosition;
    }
}