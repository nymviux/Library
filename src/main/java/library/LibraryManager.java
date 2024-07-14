package library;

import library.Items.Item;
import library.Items.ItemCellRenderer;
import library.Items.ItemComparator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class LibraryManager {

    private JList<Item> itemList;
    private JList<Item> basketList;

    
    private void initializeLists(Item[] itemList){

        Arrays.sort(itemList, new ItemComparator());
        DefaultListModel<Item> listModel = new DefaultListModel<>();

        for (Item item : itemList) {
            listModel.addElement(item);
        }

     
        JList<Item> itemJList = new JList<>(listModel);
        this.itemList = itemJList;
        this.itemList.setCellRenderer(new ItemCellRenderer());
        this.basketList = new JList<>(new DefaultListModel<Item>());
        this.basketList.setCellRenderer(new ItemCellRenderer());
    }

    public LibraryManager(Item[] itemList){
        initializeLists(itemList);
    }

    public JList<Item> getItemList(){
        return itemList;
    }

    public JList<Item> getBasketList(){
        return basketList;
    }


    public void saveBasketToFile(){
        try {

            FileOutputStream fileOut = new FileOutputStream("basket.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            DefaultListModel<Item> listModel = (DefaultListModel<Item>) basketList.getModel();
            List<Item> items = new ArrayList<>();

            for (int i = 0; i < listModel.getSize(); i++) {
                items.add(listModel.getElementAt(i));
            }

            out.writeObject(items);
            out.close();
            fileOut.close();
            System.out.println("Basket serialized and saved to basket.ser");
        } catch (IOException e) {
            UICreator.showErrorDialog("There was an error while saving the basket to a file. Please try again.");
        }
    }

    public BigDecimal getBasketValue() {
        JList<Item> basketList = getBasketList();
        
        BigDecimal basketValue = new BigDecimal(0);
        
        DefaultListModel<Item> basketModel = (DefaultListModel<Item>) basketList.getModel();
        
        for (int i = 0; i < basketModel.getSize(); i++) {
            Item var = basketModel.getElementAt(i);
            BigDecimal price =  new BigDecimal(var.getPrice());
            Integer quantity = var.getQuantity();
            BigDecimal itemsCost = price.multiply(new BigDecimal(quantity));
            basketValue = basketValue.add(itemsCost);
        }

        return basketValue;
    }

    public void loadBasketFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream("basket.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            List<Item> items = (List<Item>) in.readObject();

            DefaultListModel<Item> listModel = (DefaultListModel<Item>) basketList.getModel();
            listModel.clear();
            for (Item item : items) {
                listModel.addElement(item);
            }

            in.close();
            fileIn.close();
            System.out.println("Basket deserialized from basket.ser");
        } catch (IOException e) {
            UICreator.showErrorDialog("There was an error while loading the basket from a file. Please try again.");
        } catch (ClassNotFoundException e) {
            UICreator.showErrorDialog("There was an error while loading the basket from a file. Please try again.");
        }
    }
}
