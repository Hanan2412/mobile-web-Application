package ModelView;

/**
 * the interface WebAppJSInterface is implementing
 */
public interface WebApp {
    void setCurrTransaction(Transaction transaction);
    void newTransaction(String date, String product, float price, int quantity);
    int setupTransactionTable();
    String getTransactionFromTable(int index);
    String getTransactionDate(int index);
    String getTransactionProduct(int index);
    float getTransactionPrice(int index);
    int getTransactionQuantity(int index);
    int checkTransactionTableSize();
    String readBudget();
    void deleteRowFromDB(int rowId);
}
