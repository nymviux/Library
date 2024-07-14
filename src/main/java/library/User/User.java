package library.User;

import java.math.BigDecimal;

public class User {
    private static User instance;
    private String first_name;
    private String last_name;
    private BigDecimal balance;

    private User(String first_name, String last_name, BigDecimal balance) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = balance;

    }

    public static User getInstance(String first_name, String last_name, BigDecimal balance) {
        if (instance == null) {
            instance = new User(first_name, last_name, balance);
        }
        return instance;
    }

    public static User getInstance() throws IllegalStateException {
        if (instance == null) {

            throw new IllegalStateException(
                    "Instance not initialized. Call getInstance(String first_name, String last_name, BigDecimal balance) first.");
        }
        return instance;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void subtractMoney(float amount) throws IllegalArgumentException {

        BigDecimal amount_ = new BigDecimal(Float.toString(amount));
        if (balance.compareTo(amount_) < 0) 
            throw new IllegalArgumentException("Insufficient balance.");
            
        balance = balance.subtract(amount_);
    }

}
