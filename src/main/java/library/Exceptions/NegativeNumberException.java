package library.Exceptions;

public class NegativeNumberException extends Exception {
    public NegativeNumberException(){
        super("The number is lower than 0.");
    }
}

