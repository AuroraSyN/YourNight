package apackage.com.yournight.model.event.eventAssist;

import java.io.Serializable;

/**
 * Created by Aleksandr Soloninov on 03.12.2017.
 * Hochschule Worms, inf3032
 */

public class EventStats implements Serializable {

    private String attending;
    private String declined;
    private String maybe;
    private String noreply;

    public String getAttending() {
        return attending;
    }

    public String getDeclined() {
        return declined;
    }

    public String getMaybe() {
        return maybe;
    }

    public String getNoreply() {
        return noreply;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public void setDeclined(String declined) {
        this.declined = declined;
    }

    public void setMaybe(String maybe) {
        this.maybe = maybe;
    }

    public void setNoreply(String noreply) {
        this.noreply = noreply;
    }

    // to string

    @Override
    public String toString() {
        return "--- Event Stats ---\n"
                + "Attending - Declined - Maybe - Noreply\n"
                + attending + " - " + declined + " - " + maybe + " - " + noreply;
    }
}
