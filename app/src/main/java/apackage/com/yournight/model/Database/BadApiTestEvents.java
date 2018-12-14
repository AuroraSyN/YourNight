package apackage.com.yournight.model.Database;

import apackage.com.yournight.model.event.Event;
import apackage.com.yournight.model.event.eventAssist.EventLocation;
import apackage.com.yournight.model.event.eventAssist.EventPlace;
import apackage.com.yournight.model.event.eventAssist.EventStats;
import apackage.com.yournight.model.event.eventAssist.EventVenue;
import apackage.com.yournight.model.network.EventNetwork;

/**
 * Created by Aleksandr Soloninov on 28.03.2017.
 * Hochschule Worms, inf3032
 */

public class BadApiTestEvents {

    private Event[] downEvents;

    public Event[] getDownEvents(){
        return this.downEvents;
    }

    public BadApiTestEvents(){
        downEvents = new Event[29];
        for (int i = 0; i < 29; i++) {
            downEvents[i] = new Event();
            downEvents[i].setName("TestEvent # " + i*3+1);
            downEvents[i].setStartTime("00:0"+i +":0"+ i*7);
            downEvents[i].setId("" + i*i*42);

            // testEventPlace
            EventPlace testEventPlace = new EventPlace();
            testEventPlace.setId("" + i+42);
            testEventPlace.setName("testEventPlace # " +i*252);
            downEvents[i].setPlace(testEventPlace);

            // testEventLocation
            EventLocation testEventLocation = new EventLocation();
            testEventLocation.setCity("Worms # " +i*76352);
            testEventLocation.setCountry("testCountry # " +i*64873);
            testEventLocation.setLatitude("49.6" + (int)(Math.random()*100000));
            testEventLocation.setLongitude("8.3" + (int)(Math.random()*100000));
            testEventLocation.setStreet("testStreet # " +i*21657);
            testEventLocation.setZip(""+ i*i*i*i+i );
            downEvents[i].setLocation(testEventLocation);

            // testEventVenue
            EventVenue testEventVenue = new EventVenue();
            testEventVenue.setCategory("testCategory # " +i*2658);
            String testCategoryList[] = {"List1", "List2"};
            testEventVenue.setCategoryList(testCategoryList);
            testEventVenue.setId(""+i*733);
            testEventVenue.setName("testVenue # "+i*12);
            downEvents[i].setVenue(testEventVenue);

            // testEventStats
            EventStats testEventStats = new EventStats();
            testEventStats.setAttending("" +i*75);
            testEventStats.setDeclined("" +i*99);
            testEventStats.setMaybe("" +i*89);
            testEventStats.setNoreply(""+i*54);
            downEvents[i].setStats(testEventStats);
        }
        EventNetwork.events = new Event[downEvents.length];
    }
}
