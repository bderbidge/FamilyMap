package Models;

/**
 * Created by brandonderbidge on 6/17/17.
 */

public class PersonOrEvent {

    public PersonOrEvent(String ID, String text, String type) {
        this.ID = ID;
        Text = text;
        Type = type;
    }

    private String ID;
    private String Text;
    private String Type;

    public String getID() {
        return ID;
    }

    public String getText() {
        return Text;
    }

    public String getType() {
        return Type;
    }
}
