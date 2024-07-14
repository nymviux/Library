package library.Items;

import java.io.Serializable; 
import java.math.BigDecimal;
import java.util.UUID;

import library.Exceptions.NegativeNumberException;



public abstract class Item implements ItemInterface, Serializable {
    
    private transient UUID uid; 
    private BigDecimal price; 
    private Integer quantity; 
    private String title;

    

    public Item(double price, Integer quantity, String title) {
       
        this.uid = UUID.randomUUID();  
        this.price = new BigDecimal(price);
        this.quantity = quantity;
        this.title = title;
    }


    protected Item(UUID uid, double price, Integer quantity, String title) {
        this.uid = uid;
        this.price = new BigDecimal(price);
        this.quantity = quantity;
        this.title = title;
    }

    
    final public UUID getUid(){
        return uid;
    }

    final public String getTitle() {
        return title;
    }

    final public double getPrice() {
        return price.doubleValue();
    }

    final public Integer getQuantity() {
        return quantity;
    }

    final public void addQuantity(Integer quantity_) {
        quantity = quantity + quantity_;
    }

    final public void subtractQuantity(Integer quantity_) {
        quantity = quantity - quantity_;
    }

    final public void setPrice(float value) throws NegativeNumberException {
        BigDecimal convertedValue = new BigDecimal(value); 
        if (value < 0) {
            throw new NegativeNumberException();
        }
        this.price = convertedValue;
    }

    final public void setQuantity(Integer value) {
        this.quantity = value;
    }

    
    public abstract String getDescription(); 

    public abstract Item copy(Integer quantity); 
}
