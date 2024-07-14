package library.Literature;

import java.util.UUID;

import library.Items.Item;

public class Literature extends Item {
    private String author;
    private int releaseDate;

    public Literature(String title, String author, int releaseDate, double price, Integer quantity){
        super(price, quantity, title);
        this.author = author;
        this.releaseDate = releaseDate;
    }

    protected Literature(UUID uid, String title, String author, int releaseDate, double price, Integer quantity){
        super(uid, price, quantity, title);
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public String getAuthor(){
        return author;
    }

    public int getReleaseDate(){
        return releaseDate;
    }
    
    public String getDescription(){
        return "Literature Information\nAuthor: " + getAuthor() + "\nCreation Date: " + getReleaseDate();
    }

    @Override
    public Literature copy(Integer quantity){
        Literature copy = new Literature(getUid(), getTitle(), getAuthor(), getReleaseDate(), getPrice(), quantity);
        return copy;
    }
}
