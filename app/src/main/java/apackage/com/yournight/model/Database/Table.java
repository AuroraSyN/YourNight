package apackage.com.yournight.model.Database;

import android.provider.BaseColumns;


public class Table {

    static abstract class TableInfo implements BaseColumns {

        static final String DATABASE_NAME = "yournight_Db";
        static final String TABLE_APP_DATA = "event_table";

        static final String EVENT_ID = "event_id";
        static final String EVENT_NAME = "event_Name";
        static final String EVENT_PLACE = "event_place";
        static final String EVENT_DATE = "event_date";
        static final String EVENT_STARTIME = "event_startime";
        static final String EVENT_MEMBER_AMOUNT = "event_member_amount";
        static final String EVENT_PICTURE_PATH = "event_picuture_path";


    }
}

