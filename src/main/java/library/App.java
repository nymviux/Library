package library;

import library.Items.Item;
import library.User.User;
import java.math.BigDecimal;


public class App {

    public static void main(String[] args) {
       
        Item[] itemsList = ItemFixtureCreator.getItemList();

        
        User.getInstance("Adam", "Malysz", new BigDecimal(100));

        LibraryManager libraryManager = new LibraryManager(itemsList);
        ActionListeners actionListeners = new ActionListeners(libraryManager);
        UICreator uiCreator = new UICreator(libraryManager, actionListeners);

        uiCreator.createUI();

    }

}
