package ModelView;

public class Transaction {

    private String date;
    private String product;
    private int quantity;
    private float price;

    public Transaction(String date, String product, float price, int quantity) {

        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  "" + date + '\'' +
                ";" + product + '\'' +
                ";" + quantity +
                ";" + price;
    }
}
