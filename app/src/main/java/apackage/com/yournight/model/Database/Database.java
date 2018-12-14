package apackage.com.yournight.model.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {

    /**
     * DATABASE_VERSION contains the current the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * SQL QUERY to create the table app_table.
     */
    private String createQuery = "CREATE TABLE " + Table.TableInfo.TABLE_APP_DATA
            + " (" + Table.TableInfo.EVENT_ID + " TEXT,"
            + Table.TableInfo.EVENT_NAME + " TEXT,"
            + Table.TableInfo.EVENT_PLACE + " TEXT,"
            + Table.TableInfo.EVENT_DATE + " TEXT,"
            + Table.TableInfo.EVENT_STARTIME + " TEXT,"
            + Table.TableInfo.EVENT_MEMBER_AMOUNT + " TEXT,"
            + Table.TableInfo.EVENT_PICTURE_PATH + " TEXT);";
    
    /**
     * Database constructor calls the constructor from his super class.
     * @param context contains the activity of the UI thread
     */
    public Database(final Context context) {
        super(context, Table.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a table into the database.
     * @param sqLiteDatabase contains the SQLiteDatabase object
     */
    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createQuery);
    }

    /**
     * Method not necessary.
     * @param sqLiteDatabase not necessary
     * @param i not necessary
     * @param i1 not necessary
     */
    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase,
                          final int i, final int i1) {
    }

    public  boolean checkIsIDAlreadyExits(final Database database, String TableName, String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = database.getWritableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /**
     * Deletes a item from the table app_data.
     * Item can be delete with an Package Name
     * @param database contains the database object
     */
    public void deleteAppData(final Database database) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String sql = "drop table " + Table.TableInfo.TABLE_APP_DATA;
        try {
            sqLiteDatabase.execSQL(sql);

        } catch (SQLException e) {

        }
    }
}

