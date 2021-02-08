package ModelView;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.appdevv2.R;
import com.example.appdevv2.View;

import java.util.ArrayList;

import Model.Database;

/**
 * @author Victoria Avrahamchik
 * @author Hanan Dorfman
 * the javascript interface for the webview
 */

public class WebAppJSInterface implements WebApp{

    //private Database database;
    Context mContext;
    String currTable;
    Database database;
    Transaction currTransaction;
    private View view;
    ArrayList<Transaction> transactions;

    /** Instantiate the interface and set the context */
   public WebAppJSInterface(Context c) {
        this.transactions=null;
        this.mContext = c;
        this.database = new Database(c);
    }

    /**
     * constructor
     * @param database the database created in mainActivity
     * @param view  the view created in mainActivity
     */
    public WebAppJSInterface(Database database, View view){
       this.database = database;
       this.view = view;
    }

    /**
     * sets the current transaction
     * @param transaction the data that was entered
     */
    public void setCurrTransaction(Transaction transaction){
        this.currTransaction = transaction;
    }

    /**
     * sets a new transaction and adds it to the database
     * @param date entry date
     * @param product the product name
     * @param price the product price
     * @param quantity the product quantity
     */
    @JavascriptInterface
    public void newTransaction(String date, String product, float price, int quantity){
        this.currTransaction = new Transaction(date, product, price, quantity);
        addTransactionToDB();
        Toast.makeText(view.getWebView().getContext(),view.getWebView().getContext().getResources().getString(R.string.tra),Toast.LENGTH_SHORT).show();

    }

    /**
     * adds new transaction to the database
     */
    private void addTransactionToDB(){
        database.insertTransaction(currTable, currTransaction);
    }


    /**
     * returns the number of transactions in the database
     * @return returns the number of transactions
     */
    @JavascriptInterface
    public int setupTransactionTable(){
        ArrayList<Transaction> transactions = database.getTransactionTable(database.getTableName());
        if(transactions!=null) {
            int count = transactions.size();
            //return Arrays.toString(stringTransactions);
            this.transactions = transactions;
            return count;
        }
        else{
            Toast.makeText(view.getWebView().getContext(),view.getWebView().getContext().getResources().getString(R.string.e_tra),Toast.LENGTH_SHORT).show();

            //return null;
            this.transactions = null;
            return 0;
        }
    }

    /**
     * return the transaction corresponding with the index
     * @param index the index of the transaction to return
     * @return the desired transaction as a string
     */
    public String getTransactionFromTable(int index){
        return transactions.get(index).toString();
    }

    /**
     * gets the desired transaction date
     * @param index the index of the transaction
     * @return the date of the transaction
     */
    @JavascriptInterface
    public String getTransactionDate(int index){
        return this.transactions.get(index).getDate();
    }

    /**
     * gets the desired transaction name
     * @param index the index of the transaction
     * @return the name of the transaction
     */
    @JavascriptInterface
    public String getTransactionProduct(int index){
        return this.transactions.get(index).getProduct();
    }

    /**
     * gets the desired transaction price
     * @param index the index of the transaction
     * @return the price of the transaction
     */
    @JavascriptInterface
    public float getTransactionPrice(int index){
        return this.transactions.get(index).getPrice();
    }

    /**
     * gets the desired transaction quantity
     * @param index the index of the transaction
     * @return the quantity of the transaction
     */
    @JavascriptInterface
    public int getTransactionQuantity(int index){
        return this.transactions.get(index).getQuantity();
    }
    /**
     * @return the number of all the existing transactions
     */
    @JavascriptInterface
    public int checkTransactionTableSize(){
        return this.transactions.size();
    }

    /**
     * deletes the transaction the user picked
     * @param rowId the row to delete
     */
    @JavascriptInterface
    public void deleteRowFromDB(int rowId){
        transactions.remove(rowId);
        database.deleteRow(rowId);
        for(int i=0;i<transactions.size();i++)
            database.insertTransaction(currTable,transactions.get(i));
    }

    /**
     * reads the budget and updates it according to the transactions in the database
     * @return the calculated remaining budget
     */
    @JavascriptInterface
    public String readBudget()
    {
        float tmpBudget=0;
        float spent = 0;
        ArrayList<Transaction> transactions = database.getTransactionTable(database.getTableName());
        for(int i = 0;i<transactions.size();i++)
        {
            if(transactions.get(i).getProduct().equals("budget"))
                tmpBudget = transactions.get(i).getPrice();
            else
                spent = spent + transactions.get(i).getPrice()*transactions.get(i).getQuantity();
        }
        float finalAnswer;
        finalAnswer = tmpBudget-spent;
        return String.valueOf(finalAnswer);
    }
}
