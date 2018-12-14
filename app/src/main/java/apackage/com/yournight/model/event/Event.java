package apackage.com.yournight.model.event;

import android.graphics.Bitmap;

import java.io.Serializable;

import apackage.com.yournight.model.event.eventAssist.EventLocation;
import apackage.com.yournight.model.event.eventAssist.EventPlace;
import apackage.com.yournight.model.event.eventAssist.EventStats;
import apackage.com.yournight.model.event.eventAssist.EventVenue;
/**
 * Created by Aleksandr Soloninov on 20.11.2017.
 * Hochschule Worms, inf3032
 */
public class  Event implements Serializable {
    /* description */
    private String id;
    private String name;
    private String coverPicture;
    private String profilePicture;
    private String distance;
    private String description;
    private String startTime;
    private String endTime;

    /* place */
    private EventPlace place;
    private String placeID;
    private String placeName;

    /* location */
    private EventLocation location;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String street;
    private String zip;

    /* stats */
    private EventStats stats;
    private String attending;
    private String declined;
    private String maybe;
    private String noreply;

    /* venue */
    private EventVenue venue;
    private String venueId;
    private String venueName;
    private String venueCategory;
    private String[] venueCategoryList;

    /* Bitmap */
    private Bitmap bitmapCoverPicture;
    private Bitmap bitmapProfilePicture;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public EventPlace getPlace() {
        return place;
    }

    public void setPlace(EventPlace place) {
        this.place = place;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public EventLocation getLocation() {
        return location;
    }

    public void setLocation(EventLocation location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public EventVenue getVenue() {
        return venue;
    }

    public void setVenue(EventVenue venue) {
        this.venue = venue;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCategory() {
        return venueCategory;
    }

    public void setVenueCategory(String venueCategory) {
        this.venueCategory = venueCategory;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getDeclined() {
        return declined;
    }

    public void setDeclined(String declined) {
        this.declined = declined;
    }

    public String getMaybe() {
        return maybe;
    }

    public void setMaybe(String maybe) {
        this.maybe = maybe;
    }

    public String getNoreply() {
        return noreply;
    }

    public void setNoreply(String noreply) {
        this.noreply = noreply;
    }

    public EventStats getStats() {
        return stats;
    }

    public void setStats(EventStats stats) {
        this.stats = stats;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Bitmap getBitmapCoverPicture() {
        return bitmapCoverPicture;
    }

    public void setBitmapCoverPicture(Bitmap bitmapCoverPicture) {
        this.bitmapCoverPicture = bitmapCoverPicture;
    }

    public Bitmap getBitmapProfilePicture() {
        return bitmapProfilePicture;
    }

    public void setBitmapProfilePicture(Bitmap bitmapProfilePicture) {
        this.bitmapProfilePicture = bitmapProfilePicture;
    }

    public String[] getVenueCategoryList() {
        return venueCategoryList;
    }

    public void setVenueCategoryList(String[] venueCategoryList) {
        this.venueCategoryList = venueCategoryList;
    }

    // event to string for easy logging

    public String getEventGeneralInfo(){
        return
                "-- Event General Info --- \n"
                + name + "(ID:" + id + ")\n"
                + "from " + startTime + " to " + endTime +"\n"
                + "Distance: " + distance + "\n"
                + "Description: " + description;
    }

    @Override
    public String toString() {
        String returnString;

        try {
            returnString =
                    "---Event---\n"
                    + getEventGeneralInfo()
                    + place.toString() + "\n"
                    + location.toString() + "\n"
                    + stats.toString() + "\n"
                    + venue.toString();
        } catch (Exception e){
            try {
                returnString = getEventGeneralInfo();
            } catch (Exception e1){
                returnString = "Event.toString() failed";
            }
        }

        return returnString;

    }
}

