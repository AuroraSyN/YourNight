package apackage.com.yournight.model.event.eventAssist;

import java.io.Serializable;

/**
 * Created by Aleksandr Soloninov on 23.11.2017.
 * Hochschule Worms, inf3032
 */

public class EventPlace implements Serializable {

    private String id;
    private String name;
    private EventLocation location;


    public EventLocation getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // to string

    @Override
    public String toString() {
        return "--- Event Place ---\n"
                + name + " (ID:" + id + ")";
    }
}
