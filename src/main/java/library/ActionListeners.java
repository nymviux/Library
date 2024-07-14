package library;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.UUID;

import library.Exceptions.NotEnoughItemsException;
import library.Items.Item;
import library.User.User;

import java.text.DecimalFormat;


public class ActionListeners {
    private LibraryManager libraryManager;

    public ActionListeners(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    public ActionListener getAddToCartButtonClickListener(JTextField quantityTextField, JLabel summaryCost) {
        return new AddToCartButtonClickListener(quantityTextField, summaryCost);
    }

    public ListSelectionListener getShopListSelecActionListener(JTextField quantityTextField, JLabel cost, JTextArea textArea) {
        return new ShopListSelectionListenerClass(quantityTextField, cost, textArea);
    }

    public QuantityFieldListenerClass getQuantityFieldListenerClass(JTextField quantityTextField, JLabel cost) {
        return new QuantityFieldListenerClass(quantityTextField, cost);
    }

    public ActionListener getCheckoutButtonClickListener(JLabel userCashLabel, JLabel summaryCostLabel) {
        return new CheckoutButtonClickListener(userCashLabel, summaryCostLabel);
    }

    public ActionListener getSaveBasketButtonClickListener() {
        return new SaveBasketButtonClickListener();
    }

    public ActionListener getLoadBasketFromFileButtonClickListener(JLabel summaryCostLabel) {
        return new loadBasketFromFileButtonClickListener(summaryCostLabel);
    }

    class AddToCartButtonClickListener implements ActionListener {
        private JTextField quantityTextField;
        private JLabel summaryCostLabel;

        public AddToCartButtonClickListener(JTextField quantityTextField, JLabel summaryCost) {
            this.quantityTextField = quantityTextField;
            this.summaryCostLabel = summaryCost;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                manageAddingToCart();
            } catch (NotEnoughItemsException exc) {
                UICreator.showErrorDialog(exc.getMessage());
            }

        }

        private void manageAddingToCart() throws NotEnoughItemsException {
            JList<Item> sourceList = libraryManager.getItemList(); 
            JList<Item> targetList = libraryManager.getBasketList(); 
            int selectedIndex = sourceList.getSelectedIndex();
            if (selectedIndex != -1) { 
                Item selectedItem = sourceList.getSelectedValue(); 
                DefaultListModel<Item> sourceModel = (DefaultListModel<Item>) sourceList.getModel(); 
                DefaultListModel<Item> targetModel = (DefaultListModel<Item>) targetList.getModel(); 
        
                UUID selectedUid = selectedItem.getUid();
                Item foundItem = findItemWithUidInList(selectedUid, targetModel); 
                
                Integer quantity = Integer.parseInt(quantityTextField.getText()); 
                throwIfNotEnoughItems(selectedItem, quantity); 
        
                if (foundItem == null) { 
                    targetModel.addElement(selectedItem.copy(quantity)); 
                } else { 
                    System.out.println("hit2");
                    foundItem.addQuantity(quantity); 
                }
                if (selectedItem.getQuantity() - quantity == 0) { 
                    System.out.println("hit3");
                    sourceModel.remove(selectedIndex);
                }
                selectedItem.subtractQuantity(quantity); 
            }
            refreshLists(sourceList, targetList); 
            refreshTotalCostLabel(); 
        }
        
        private void refreshLists(JList<Item> list1, JList<Item> list2) {
            list1.revalidate(); 
            list2.revalidate();
            list1.repaint(); 
            list2.repaint(); 
        }
        
       
        private void refreshTotalCostLabel() {
            JList<Item> basketList = libraryManager.getBasketList(); 
            DefaultListModel<Item> basketModel = (DefaultListModel<Item>) basketList.getModel(); 
            double totalCost = 0.0;
        
            for (int i = 0; i < basketModel.getSize(); i++) { 
                Item item = basketModel.getElementAt(i); 
                double itemCost = item.getPrice() * item.getQuantity(); 
                totalCost += itemCost; 
            }
        
            DecimalFormat decimalFormat = new DecimalFormat("#.##"); 
            String formattedTotalCost = decimalFormat.format(totalCost); 
        
            
            
            summaryCostLabel.setText(String.format("Summary cost: %s", formattedTotalCost)); 
        }
     
       
        private void throwIfNotEnoughItems(Item selectedItem, Integer quantity) throws NotEnoughItemsException {
            if (selectedItem.getQuantity() - quantity < 0) 
                throw new NotEnoughItemsException(); 
        }
        
        
        private Item findItemWithUidInList(UUID uid, DefaultListModel<Item> list) {
            Item foundItem;
            for (int i = 0; i < list.getSize(); i++) { 
                foundItem = list.getElementAt(i); 
                if (foundItem.getUid().equals(uid)) { 
                    return foundItem; 
                }
            }
            return null; 
        }
    }

   
    class CheckoutButtonClickListener implements ActionListener {
        private JLabel userCashLabel; 
        private JLabel summaryCostLabel; 
    
        CheckoutButtonClickListener(JLabel userCashLabel, JLabel summaryCostLabel) {
            this.userCashLabel = userCashLabel;
            this.summaryCostLabel = summaryCostLabel;
        }
    
        private void manageCheckout() throws IllegalArgumentException{
            BigDecimal basketValue = libraryManager.getBasketValue(); 
            BigDecimal userCash = new BigDecimal(User.getInstance().getBalance().doubleValue()); 
    
            
            userCash = userCash.subtract(basketValue); 
            User.getInstance().subtractMoney(basketValue.floatValue()); 
            userCashLabel.setText(String.format("Balance: %.2f", userCash)); 
            DefaultListModel<Item> basketModel = (DefaultListModel<Item>) libraryManager.getBasketList().getModel(); 
            basketModel.clear(); 
            summaryCostLabel.setText("Summary cost: 0"); 
        }
    

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                manageCheckout(); 
            } catch (IllegalArgumentException exc) { 
                UICreator.showErrorDialog(exc.getMessage());
            }
        }
    }


    class ShopListSelectionListenerClass implements ListSelectionListener {
        private JTextField quantityTextField; 
        private JLabel costLabel; 
        private JTextArea textArea; 
    
        ShopListSelectionListenerClass(JTextField quantityTextField, JLabel costLabel, JTextArea textArea) {
            this.quantityTextField = quantityTextField;
            this.costLabel = costLabel;
            this.textArea = textArea;
        }
    
        private void manageCostLabel(Item selectedItem){
            String quantity_str = quantityTextField.getText(); 
            if (quantity_str.isEmpty())
                costLabel.setText("Cost: 0"); 
            else if (selectedItem != null) {
                int quantity = Integer.parseInt(quantity_str); 
                double price = selectedItem.getPrice(); 
                double cost = quantity * price; 
                costLabel.setText(String.format("Cost: %.2f", cost)); 
            }
        }
    
        private void manageTextArea(Item selectedItem){
            if (selectedItem != null) {
                textArea.setText(selectedItem.getDescription()); 
            }
        }
    
        @Override
        public void valueChanged(ListSelectionEvent e) {
        
            if (!e.getValueIsAdjusting()) {
                JList<Item> list = libraryManager.getItemList(); 
                Item selectedValue = list.getSelectedValue(); 
                manageCostLabel(selectedValue); 
                manageTextArea(selectedValue); 
            }
        }
    }


class QuantityFieldListenerClass implements DocumentListener {
    private JTextField quantityTextField; 
    private JLabel costLabel; 

    QuantityFieldListenerClass(JTextField quantityTextField, JLabel costLabel) {
        this.quantityTextField = quantityTextField;
        this.costLabel = costLabel;
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        updateCostLabel(); 
    }

    @Override
    public void insertUpdate(DocumentEvent arg0) {
        updateCostLabel(); 
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        updateCostLabel(); 
    }

    
    private void updateCostLabel() {
        JList<Item> list = libraryManager.getItemList(); 
        Item selectedValue = list.getSelectedValue();
        String quantity_str = quantityTextField.getText(); 
        if (quantity_str.isEmpty())
            costLabel.setText("Cost: 0"); 
        else if (selectedValue != null) {
            int quantity = Integer.parseInt(quantity_str); 
            double price = selectedValue.getPrice(); 
            double cost = quantity * price; 
            costLabel.setText(String.format("Cost: %.2f", cost)); 
        }
    }
}

    
    class SaveBasketButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryManager.saveBasketToFile();
        }
    }

    class loadBasketFromFileButtonClickListener implements ActionListener {
        private JLabel summaryCostLabel; 
        loadBasketFromFileButtonClickListener(JLabel summaryCostLabel) {
            this.summaryCostLabel = summaryCostLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryManager.loadBasketFromFile(); 
            BigDecimal basketValue = libraryManager.getBasketValue();
            summaryCostLabel.setText(String.format("Summary cost: %.2f", basketValue)); 
        }
    }

}
