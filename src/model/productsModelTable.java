package model;

/**
 * @author Fabian-Bob Ioan George
 * basic class with constructor, setters and getters
 * nothing fancy stores Strings from the db or from the user
 */

public class productsModelTable {
    String id, product_name, quantity;

    public productsModelTable(String id, String product_name, String quantity) {
        this.id = id;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
