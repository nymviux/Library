package library.Literature;

import java.util.UUID;

public class Book extends Literature {
    private String isbn;

    public Book(String title, String author, int releaseDate, double price, Integer quantity, String isbn) {
        super(title, author, releaseDate, price, quantity); 
         this.isbn = isbn;
    }
    
    private Book(UUID uid, String title, String author, int releaseDate, double price, Integer quantity, String isbn) {
        super(uid, title, author, releaseDate, price, quantity);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription(){ 
        return "Books Information\nAuthor: " + getAuthor() + "\nISBN: " + getIsbn() + "\nCreation Date: " + getReleaseDate();
    }

    @Override
    public Book copy(Integer quantity){
        Book copy = new Book(getUid(), getTitle(), getAuthor(), getReleaseDate(), getPrice(), quantity, getIsbn());
        return copy;
    }
}
