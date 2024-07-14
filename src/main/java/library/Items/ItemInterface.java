package library.Items;

import library.Exceptions.NegativeNumberException;

interface ItemInterface { 
    double getPrice();
    Integer getQuantity();
    void setPrice(float value) throws NegativeNumberException;
    void setQuantity(Integer value);
    String getDescription();
    Item copy(Integer quantity);
}