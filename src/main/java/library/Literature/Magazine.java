package library.Literature;

import java.util.UUID;

public class Magazine extends Literature {
    
    private String issn;

    public Magazine(String title, String author, int releaseDate, double price, Integer quantity, String issn) {
        super(title, author, releaseDate, price, quantity);
        this.issn = issn;
    }

    private Magazine(UUID uid, String title, String author, int releaseDate, double price, Integer quantity, String issn) {
        super(uid, title, author, releaseDate, price, quantity);
        this.issn = issn;
    }

    public String getIssn() {
        return issn;
    }

    public String getDescription(){
        return "Magazine Information\nAuthor: " + getAuthor() + "\nISSN: " + getIssn() + "\nCreation Date: " + getReleaseDate();
    }

    @Override
    public Magazine copy(Integer quantity){
        Magazine copy = new Magazine(getUid(), getTitle(), getAuthor(), getReleaseDate(), getPrice(), quantity, getIssn());
        return copy;
    }
}
