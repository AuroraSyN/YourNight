package apackage.com.yournight.model.cache;



public class DiskLruImageCacheObject {

    /**
     * is the Database object that can be called
     * from every class.
     */
    public DiskLruImageCache diskLruImageCache  = null;

    /**
     * Default constructor of the DatabaseObject class.
     */
    private DiskLruImageCacheObject() {
    }

    /**
     * Instance of the DatabaseObject.
     *
     */
    public static DiskLruImageCacheObject instance = null;

    /**
     *  Get the DatabaseObject.
     * @return the DatabaseObject
     */
    public static synchronized DiskLruImageCacheObject getObject() {
        if (instance == null) {
            instance = new DiskLruImageCacheObject();
        }
        return instance;
    }
}
