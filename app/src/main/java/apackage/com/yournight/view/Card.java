package apackage.com.yournight.view;

import java.util.Date;

/**
 * >>Die Klasse wird nicht mehr gebraucht<<
 *
 */

public class Card {
    private String imgURL;
    private String eventName;
    private int member;
    private String location;

    public Card(String imgURL, String eventName, String location, int member) {
        this.imgURL = imgURL;
        this.eventName = eventName;
        this.location = location;
        this.member = member;

    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
