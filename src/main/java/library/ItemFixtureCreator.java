package library;

import java.util.ArrayList;

import library.Items.Item;
import library.Literature.Book;
import library.Literature.Literature;
import library.Literature.Magazine;

public class ItemFixtureCreator {
    public static Item[] getItemList() {
        ArrayList<Item> itemArray = new ArrayList<>();
        
        itemArray.add(new Literature("Literature Title 1", "Author 1", 2000, 10.99, 5));
        itemArray.add(new Literature("Literature Title 2", "Author 2", 2005, 15.99, 3));

        
        itemArray.add(new Book("Book Title 1", "Author 3", 1998, 20.50, 2, "ISBN1"));
        itemArray.add(new Book("Book Title 2", "Author 4", 2010, 12.75, 4, "ISBN2"));
        itemArray.add(new Book("Book Title 3", "Author 5", 2007, 18.00, 6, "ISBN3"));

       
        itemArray.add(new Magazine("Magazine Title 1", "Editor 1", 2021, 5.99, 10, "ISSN1"));
        itemArray.add(new Magazine("Magazine Title 2", "Editor 2", 2019, 7.50, 8, "ISSN2"));
        itemArray.add(new Magazine("Magazine Title 3", "Editor 3", 2017, 4.25, 12, "ISSN3"));
        Item[] peopleList = new Item[itemArray.size()];
        peopleList = itemArray.toArray(peopleList);

        return peopleList;
    }
}
