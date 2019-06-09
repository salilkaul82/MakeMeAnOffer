package ng.com.obkm.bottomnavviewwithfragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExampleDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Offers.db";
    private static final int DATABASE_VERSION = 1;

    public static final String OFFERS_TABLE_NAME = "offers";
    public static final String OFFERS_COLUMN_ID = "_id";
    public static final String OFFERS_COLUMN_MERCHANT_NAME = "merchant";
    public static final String PERSON_COLUMN_DISC = "discount";
    public static final String PERSON_COLUMN_COUPON = "coupon";
    public static final String PERSON_COLUMN_OFFER_URL = "offerurl";
    public static final String PERSON_COLUMN_IMAGE_URL = "imageurl";

    public ExampleDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + OFFERS_TABLE_NAME +
                        "(" + OFFERS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        OFFERS_COLUMN_MERCHANT_NAME + " TEXT, " +
                        PERSON_COLUMN_DISC + " TEXT, " +
                        PERSON_COLUMN_COUPON+ " TEXT, " +
                        PERSON_COLUMN_OFFER_URL + " TEXT, " +
                        PERSON_COLUMN_IMAGE_URL + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OFFERS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertOffer(OffersData offersData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(OFFERS_COLUMN_MERCHANT_NAME, offersData.getMerchantName());
        contentValues.put(PERSON_COLUMN_DISC, offersData.getPercentDisc());
        contentValues.put(PERSON_COLUMN_COUPON, offersData.getCouponCode());
        contentValues.put(PERSON_COLUMN_OFFER_URL, offersData.getOfferURL());
        contentValues.put(PERSON_COLUMN_IMAGE_URL, offersData.getImageURL());

        db.insert(OFFERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, OFFERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateOffer(OffersData offersData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OFFERS_COLUMN_MERCHANT_NAME, offersData.getMerchantName());
        contentValues.put(PERSON_COLUMN_DISC, offersData.getPercentDisc());
        contentValues.put(PERSON_COLUMN_COUPON, offersData.getCouponCode());
        contentValues.put(PERSON_COLUMN_OFFER_URL, offersData.getOfferURL());
        contentValues.put(PERSON_COLUMN_IMAGE_URL, offersData.getImageURL());

        db.update(OFFERS_TABLE_NAME, contentValues, OFFERS_COLUMN_ID + " = ? ", new String[] { offersData.getId()} );
        return true;
    }

    public Integer deleteOffer(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(OFFERS_TABLE_NAME,
                OFFERS_COLUMN_ID + " = ? ",
                new String[] { id });
    }

    public Cursor getOffer(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + OFFERS_TABLE_NAME + " WHERE " +
                OFFERS_COLUMN_ID + "=?", new String[]{id});
        return res;
    }

    public Cursor getAllOffers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + OFFERS_TABLE_NAME, null );
        return res;
    }
}
