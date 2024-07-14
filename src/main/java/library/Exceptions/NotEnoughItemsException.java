package library.Exceptions;


public class NotEnoughItemsException extends Exception{
    public NotEnoughItemsException(){
        super("There is not enough items in shop");
    }
}