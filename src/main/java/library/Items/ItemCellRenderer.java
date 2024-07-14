package library.Items;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ItemCellRenderer implements ListCellRenderer<Item> {
    private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {
            renderer.setText(value.getTitle() + ": " + value.getQuantity() + " - " + value.getPrice() + "zł");
        }
        return renderer;
    }
}
