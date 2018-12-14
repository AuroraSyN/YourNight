package apackage.com.yournight.model.event;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import apackage.com.yournight.R;
import apackage.com.yournight.model.Database.Database;
import apackage.com.yournight.model.cache.DiskLruImageCache;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.view.MainActivity;

import static apackage.com.yournight.model.network.EventNetwork.events;
import static apackage.com.yournight.model.network.EventNetwork.eventsList;

/**
 * Created by Aleksandr Soloninov on 06.12.2017.
 * Hochschule Worms, inf3032
 */


//TODO get only 10 events
//TODO some problem with checkIsIDAlreadyExits
//TODO Race condition because events load quicker then event covers, fix in onPostExecute from Event Parser Task
public class EventParser extends android.os.AsyncTask {

    private Activity activity;

    private final static String APPFINDER_CACHE = "EventsCoverPictureCache";

    //TODO allocate right space size
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

    private int pictureThreadCounter = 0;
    private int pictureThreadEnd = 0;

    public EventParser(Activity activity) {
        this.activity = activity;

//        Database database = new Database(activity);
//        database.deleteAppData(database);
//        database.close();
//        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT event_id, event_Name FROM event_table", null);
//        sqLiteDatabase.beginTransaction();
//
//        while (cursor.moveToNext()) {
//           Log.e("EventParser", "FROM DB EVENT ID: " + cursor.getString(0));
//           Log.e("EventParser", "FROM DB EVENT NAME: " + cursor.getString(1));
//        }
//
//        sqLiteDatabase.setTransactionSuccessful();
//        sqLiteDatabase.endTransaction();
//        sqLiteDatabase.close();
//        cursor.close();

        //pictureThreadEnd = eventsList.size();


        DiskLruImageCacheObject.getObject().diskLruImageCache = new DiskLruImageCache(activity, APPFINDER_CACHE,
                DISK_CACHE_SIZE,
                Bitmap.CompressFormat.JPEG, 50);

    }

    private static void closeSqlTransaction(final SQLiteDatabase
                                                    sqLiteDatabase) {
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        int index = 0;

        Database database = new Database(activity);
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        
        String sql = "insert into event_table (event_id, event_Name, event_startime) values (?, ?, ?);";
        SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
        
        for (Event event : eventsList){

            if(!database.checkIsIDAlreadyExits(database,"event_table", "event_id", event.getId())){
                stmt.bindString(1, event.getId());
                stmt.bindString(2, event.getName());
                stmt.bindString(3, event.getStartTime());

                stmt.executeInsert();
                stmt.clearBindings();

                DownloadTask downloadTask = new DownloadTask(event.getId());
                downloadTask.execute(event.getCoverPicture());

                pictureThreadEnd++;
            }

            //!!! only for testEvents
            events[index] = new Event();
            //--------------------------

            //description
            events[index].setId(event.getId());
            events[index].setName(event.getName());
            events[index].setCoverPicture(event.getCoverPicture());
            events[index].setProfilePicture(event.getProfilePicture());
            events[index].setDescription(event.getDescription());
            events[index].setDistance(event.getDistance());
            events[index].setStartTime(event.getStartTime());
            events[index].setEndTime(event.getEndTime());
            /* place */
            events[index].setPlace(event.getPlace());
            events[index].setPlaceID(event.getPlace().getId());
            events[index].setPlaceName(event.getPlace().getName());
            /* location */
            events[index].setLocation(event.getPlace().getLocation());
            events[index].setCity(event.getLocation().getCity());
            events[index].setCountry(event.getLocation().getCountry());
            events[index].setLatitude(event.getLocation().getLatitude());
            events[index].setLongitude(event.getLocation().getLongitude());
            events[index].setStreet(event.getLocation().getStreet());
            events[index].setZip(event.getLocation().getZip());
            /* stats */
            events[index].setStats(event.getStats());
            events[index].setAttending(event.getStats().getAttending());
            events[index].setDeclined(event.getStats().getDeclined());
            events[index].setMaybe(event.getStats().getMaybe());
            events[index].setNoreply(event.getStats().getNoreply());
            /* venue */
            events[index].setVenue(event.getVenue());
            events[index].setVenueId(event.getVenue().getId());
            events[index].setVenueName(event.getVenue().getName());
            events[index].setVenueCategory(event.getVenue().getCategory());

            /* control */
            if (events[index].getId() == null) {
                events[index].setId("null");
            }
            if (events[index].getName() == null) {
                events[index].setName("null");
            }
            if (events[index].getCoverPicture() == null) {
                events[index].setCoverPicture("null");
            }
            if (events[index].getProfilePicture() == null) {
                events[index].setProfilePicture("null");
            }
            if (events[index].getDescription() == null) {
                events[index].setDescription("null");
            }
            if (events[index].getDistance() == null) {
                events[index].setDistance("null");
            }
            if (events[index].getStartTime() == null) {
                events[index].setStartTime("null");
            }
            if (events[index].getEndTime() == null) {
                events[index].setEndTime("null");
            }
            //place
            if (events[index].getPlaceID() == null) {
                events[index].setPlaceID("null");
            }
            if (events[index].getPlaceName() == null) {
                events[index].setPlaceName("null");
            }
            //location
            if (events[index].getCity() == null) {
                events[index].setCity("null");
            }
            if (events[index].getCountry() == null) {
                events[index].setCountry("null");
            }
            if (events[index].getLatitude() == null) {
                events[index].setLatitude("null");
            }
            if (events[index].getLongitude() == null) {
                events[index].setLongitude("null");
            }
            if (events[index].getStreet() == null) {
                events[index].setStreet("null");
            }
            if (events[index].getZip() == null) {
                events[index].setZip("null");
            }
            if (events[index].getAttending() == null) {
                events[index].setAttending("null");
            }
            if (events[index].getDeclined() == null) {
                events[index].setDeclined("null");
            }
            if (events[index].getMaybe() == null) {
                events[index].setMaybe("null");
            }
            if (events[index].getNoreply() == null) {
                events[index].setNoreply("null");
            }
            if (events[index].getVenueId() == null) {
                events[index].setVenueId("null");
            }
            if (events[index].getVenueName() == null) {
                events[index].setVenueName("null");
            }
            if (events[index].getVenueCategory() == null) {
                events[index].setVenueCategory("null");
            }

            /* correct time */
            // ausgemacht wegen Facebook bzw Server down ist (TestPhase)
            /*
            events[index].getStartTime().split("T");
            String[] eventDate = events[index].getStartTime().split("T");
            String[] buffer = eventDate[1].split("\\+");
            String[] eventDateBuffer = eventDate[0].split("\\-");
            String eventTime = buffer[0];
            eventTime = eventTime.substring(1, eventTime.length() - 3);
            events[index].setStartTime(eventDateBuffer[2] + "." + eventDateBuffer[1] + "." + eventDateBuffer[0] + " ab " + eventTime + " Uhr");
            */

            if (events[index].getEndTime() != "null") {
                String[] eventDate1 = events[index].getEndTime().split("T");
                String[] buffer1 = eventDate1[1].split("\\+");
                String[] eventDateBuffer1 = eventDate1[0].split("\\-");
                String eventTime1 = buffer1[0];
                eventTime1 = eventTime1.substring(1, eventTime1.length() - 3);
                events[index].setEndTime(eventDateBuffer1[2] + "." + eventDateBuffer1[1] + "." + eventDateBuffer1[0] + " bis " + eventTime1 + " Uhr");
            }

//            Log.e("LOG | ---","----");
//            Log.e("LOG | id", events[index].getId());
//            Log.e("LOG | name", events[index].getName());
//            Log.e("LOG | CoverPicture", events[index].getCoverPicture());
//            Log.e("LOG | ProfilePicture", events[index].getProfilePicture());
//            Log.e("LOG | Description", events[index].getDescription());
//            Log.e("LOG | Distance", events[index].getDistance());
//            Log.e("LOG | startTime", events[index].getStartTime());
//            Log.e("LOG | endTime", events[index].getEndTime());
//            Log.e("LOG | placeID", events[index].getPlaceID());
//            Log.e("LOG | placeName", events[index].getPlaceName());
//            Log.e("LOG | city ", events[index].getCity());
//            Log.e("LOG | country ", events[index].getCountry());
//            Log.e("LOG | latitude ", events[index].getLatitude());
//            Log.e("LOG | longitude", events[index].getLongitude());
//            Log.e("LOG | street", events[index].getStreet());
//            Log.e("LOG | zip", events[index].getZip());
//            Log.e("LOG | Attending", events[index].getAttending());
//            Log.e("LOG | Declined", events[index].getDeclined());
//            Log.e("LOG | Maybe", events[index].getMaybe());
//            Log.e("LOG | Noreply", events[index].getNoreply());
//            Log.e("LOG | venueId", events[index].getVenueId());
//            Log.e("LOG | venueName", events[index].getVenueName());
//            Log.e("LOG | venueCategory", events[index].getVenueCategory());
            index++;
        }
        closeSqlTransaction(sqLiteDatabase);
        Log.e("INDEX", String.valueOf(index));
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        checkIfDownloadTaskFinished();

    }
    private Handler delayhandler;
    private Runnable delayrunnable;

    //TODO set a timeout 20 sec
    private void checkIfDownloadTaskFinished() {

        delayhandler= new Handler();
        delayrunnable = new Runnable() {
            @Override
            public void run() {

                if(pictureThreadCounter == pictureThreadEnd){
                    Intent mainIntent = new Intent(activity,MainActivity.class);
                    activity.startActivity(mainIntent);
                    activity.finish();
                } else {
                    delayhandler.postDelayed(this, 100);
                }
            }
        };
        delayhandler.postDelayed(delayrunnable, 100);
    }

    public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
        private String eventID;
        public DownloadTask(String eventID) {
            this.eventID = eventID;
        }

        @Override
        protected Bitmap doInBackground(String... imageUrl) {
            return downloadPic(imageUrl[0]);
        }
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            Log.e("Eventparser", "onPostExecute called");
//            Log.e("Eventparser", "onPostExecute " + pictureThreadCounter);
//            pictureThreadCounter++;
//            DiskLruImageCacheObject.getObject().diskLruImageCache.put(eventID, bitmap);
//        }

        //TODO set download timeout 
        private Bitmap downloadPic(String url) {

            Bitmap bitmap = null;
            try {

                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setInstanceFollowRedirects(true);
                InputStream input = conn.getInputStream();


                // Log.e("EventParser", "download bitmap  " +  imageUrl);

                // Download Image from URL
                // InputStream input = new java.net.URL(imageUrl).openStream();


                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                DiskLruImageCacheObject.getObject().diskLruImageCache.put(eventID, bitmap);

            } catch (java.net.SocketTimeoutException e) {

                Bitmap defaultBitMap = BitmapFactory.decodeResource(activity.getResources(),
                        R.drawable.clubwallpapertest);

                DiskLruImageCacheObject.getObject().diskLruImageCache.put(eventID, defaultBitMap);

                // Log.e("EventParser", "SocketTimeoutException  " +  e.toString());
            } catch (Exception e) {

                Log.e("EventParser", "Exception  " + e.toString());

                e.printStackTrace();
            }


            //Log.e("Eventparser", "onPostExecute " + pictureThreadCounter);
            pictureThreadCounter++;


            return bitmap;
        }
    }
}
