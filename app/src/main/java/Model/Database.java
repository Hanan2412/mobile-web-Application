package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import ModelView.Transaction;

import java.util.ArrayList;

/**
 * @author Victoria Avrahamchik
 * @author Hanan Dorfman
 * the database class
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "APP_DB";
    private static final String TRANSACTIONS = "TRANSACTIONS";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_PRODUCT = "Product";//COLUMN_DATE,COLUMN_PRODUCT,COLUMN_PRICE,COLUMN_QUANTITY
    private static final String COLUMN_PRICE = "Price";
    private static final String COLUMN_QUANTITY = "Quantity";

    //private Cursor cursor;

    SQLiteDatabase db;

    /**
     * the database constructor
     * @param context MainActivity context
     */
    public Database(Context context)
    {
        super(context,DATABASE_NAME,null,1);
        db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    /**
     * creates the database and its table
     * @param db the given database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TRANSACTIONS + " ("+ BaseColumns._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_DATE + " TEXT, "
                    + COLUMN_PRODUCT + " TEXT, "
                    + COLUMN_PRICE + " FLOAT, "
                    + COLUMN_QUANTITY + " INT );"
            );
            this.db=db;
        }catch (Exception e){
            db.execSQL("DROP TABLE IF EXISTS "+ TRANSACTIONS);
            onCreate(db);
            System.out.println("Table "+ TRANSACTIONS +" already exists.");
        }
    }

    /**
     * recreates the database as new
     * @param db database
     * @param oldVersion the oldVersion
     * @param newVersion the newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TRANSACTIONS);
        onCreate(db);

    }

    /**
     * adds new transaction to the table
     * @param tableName the table name
     * @param transaction the new transaction
     */
    public void insertTransaction(String tableName, Transaction transaction){


        ContentValues cv = new ContentValues();

        if(transaction.getProduct().equals("budget"))
        {
            ArrayList<Transaction>transactions = getTransactionTable(TRANSACTIONS);
            boolean b = false;

            for(int i=0;i<transactions.size();i++)
            {
                if(transactions.get(i).getProduct().equals("budget"))
                {
                    b = true;

                    cv.put(COLUMN_PRODUCT,transaction.getProduct());
                    cv.put(COLUMN_PRICE,transaction.getPrice());
                    cv.put(COLUMN_QUANTITY,transaction.getQuantity());
                    cv.put(COLUMN_DATE,transaction.getDate());
                    db.update(TRANSACTIONS,cv,"Product='budget'",null);
                    break;
                }
                else b=false;
            }
            if(!b)
            {
                cv.put(COLUMN_PRODUCT,transaction.getProduct());
                cv.put(COLUMN_PRICE,transaction.getPrice());
                cv.put(COLUMN_QUANTITY,transaction.getQuantity());
                cv.put(COLUMN_DATE,transaction.getDate());
                db.insert(TRANSACTIONS, null, cv);
            }

        }
        else {
            cv.put(COLUMN_DATE, transaction.getDate());
            //db.insert(TRANSACTIONS,COLUMN_DATE,cv);
            cv.put(COLUMN_PRODUCT, transaction.getProduct());
            //db.insert(TRANSACTIONS,COLUMN_PRODUCT,cv);
            cv.put(COLUMN_PRICE, transaction.getPrice());
            //db.insert(TRANSACTIONS,COLUMN_PRICE,cv);
            cv.put(COLUMN_QUANTITY, transaction.getQuantity());

            long result = db.insert(TRANSACTIONS, null, cv);


        }
    }

    /**
     * gets all the existing transactions to ArrayList
     * @param tableName the table name
     * @return all the transactions as ArrayList<Transaction>
     */

    public ArrayList<Transaction> getTransactionTable(String tableName) {

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+ TRANSACTIONS,null);
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                {
                    System.out.println(cursor.getColumnNames());
                    Transaction transaction = new Transaction(
                            cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),//date
                            cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT)),//product
                            cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE)),//price
                            cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))//quantity
                    );
                    System.out.println(
                            cursor.getString(cursor.getColumnIndex(COLUMN_DATE)) + ", "//date
                            + cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT)) + ", "//product
                            + cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE)) + ", "//price
                            + cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));//quantity
                    transactions.add(transaction);
                }
                cursor.close();
                return transactions;

            }catch(Exception e) {

                return null;
        }

    }

    /**
     *
     * @return table Name
     */
    public String getTableName(){
        return this.TRANSACTIONS;
    }

    /**
     * deletes the table
     * @param tableName the table name
     */
    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+ tableName);
    }

    /**
     * deletes a row from the database
     * @param id
     */
    public void deleteRow(int id){

        onUpgrade(db,1,1);
    }


}
