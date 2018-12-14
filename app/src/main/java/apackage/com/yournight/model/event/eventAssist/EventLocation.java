package apackage.com.yournight.model.event.eventAssist;

import java.io.Serializable;

/**
 * Created by Aleksandr Soloninov on 23.11.2017.
 * Hochschule Worms, inf3032
 */

public class EventLocation implements Serializable {

    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String street;
    private String zip;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    // to string

    @Override
    public String toString() {
        return "--- Event Location ---\n"
                + street + " | " + zip + " " + city + " | " + country + "\n"
                + "GPS:[" + latitude + "/" + longitude + "]";
    }
}
