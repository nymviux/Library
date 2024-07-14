package library;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import library.Items.Item;
import library.User.User;
import library.Utils.DocumentUtils;

import java.awt.*;

public class UICreator {
    private LibraryManager libraryManager;
    private ActionListeners actionListeners;
    public JTextArea textArea;

    public UICreator(LibraryManager libraryManager, ActionListeners actionListeners) {
        this.libraryManager = libraryManager;
        this.actionListeners = actionListeners;
    }

    public static void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void setGridBagConstraints(GridBagConstraints gbc, int gridx, int gridy, int gridwidth,
            int gridheight, double weightx, double weighty, int fill) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
    }


    private JFrame createMainFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Book shop");
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(800, 650));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

   
    private JPanel createFarLeftPanel() {
        JLabel informationLabel = new JLabel("Item information");
        JPanel panel = new JPanel();
        this.textArea = new JTextArea("Books Information\nAuthor:\nCreation Date: ");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
        panel.setLayout(new BorderLayout());
        panel.add(informationLabel, BorderLayout.NORTH);
        panel.add(scrollPaneTextArea, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane createMiddlePanel(JList<Item> itemList) {
        JScrollPane scrollPanel = new JScrollPane(itemList); 
        return scrollPanel;
    }

    private JPanel createFarRightPanel() {
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel(new GridBagLayout());
        JLabel quantityLabel = new JLabel("Quantity:");

       
        JTextField quantityTextField = new JTextField("1");
        ((PlainDocument) quantityTextField.getDocument()).setDocumentFilter(DocumentUtils.getNumericDocumentFilter());
        JLabel costLabel = new JLabel(String.format("Cost: %s", 0));
        
        libraryManager.getItemList()
                .addListSelectionListener(actionListeners.getShopListSelecActionListener(quantityTextField, costLabel, textArea));
        
        quantityTextField.getDocument().addDocumentListener(actionListeners.getQuantityFieldListenerClass(quantityTextField, costLabel));

        JLabel summaryCost = new JLabel(String.format("Summary cost: %s", 0));
        JButton putToCartButton = new JButton("Add to shopping cart");
        
        
        putToCartButton
                .addActionListener(actionListeners.getAddToCartButtonClickListener(quantityTextField, summaryCost));
        JButton buyButton = new JButton("Checkout");
        JButton saveCartButton = new JButton("Save cart");
        JButton loadCartButton = new JButton("Load cart");
        
        
        saveCartButton.addActionListener(actionListeners.getSaveBasketButtonClickListener());
        loadCartButton.addActionListener(actionListeners.getLoadBasketFromFileButtonClickListener(summaryCost));

        JPanel userInfo = new JPanel(new BorderLayout());
        User user = User.getInstance();
        JLabel userName = new JLabel(user.getFirstName() + " " + user.getLastName());
        double cash = user.getBalance().doubleValue();
        String cashText = String.format("Balance: %s", cash);
        JLabel userCash = new JLabel(cashText);
        
        
        buyButton.addActionListener(actionListeners.getCheckoutButtonClickListener(userCash, summaryCost));

        userInfo.add(userName, BorderLayout.NORTH);
        userInfo.add(userCash, BorderLayout.CENTER);

        JPanel blankPanel = new JPanel();

        setGridBagConstraints(gbc, 0, 0, 1, 1, 0.2, 0.05, GridBagConstraints.BOTH);
        panel.add(quantityLabel, gbc);
        setGridBagConstraints(gbc, 1, 0, 7, 1, 1, 0.05, GridBagConstraints.BOTH);
        panel.add(quantityTextField, gbc);
        setGridBagConstraints(gbc, 0, 1, 8, 1, 1, 0.05, GridBagConstraints.BOTH);
        panel.add(putToCartButton, gbc);

        setGridBagConstraints(gbc, 0, 2, 4, 1, 1, 0.05, GridBagConstraints.BOTH);
        panel.add(costLabel, gbc);
        setGridBagConstraints(gbc, 0, 3, 4, 1, 1, 0.05, GridBagConstraints.BOTH);
        panel.add(summaryCost, gbc);

        setGridBagConstraints(gbc, 0, 4, 8, 1, 1, 0.05, GridBagConstraints.CENTER);
        panel.add(buyButton, gbc);

        setGridBagConstraints(gbc, 0, 5, 8, 2, 1.0, 0.1, GridBagConstraints.SOUTH);
        panel.add(userInfo, gbc);
        setGridBagConstraints(gbc, 0, 6, 8, 10, 1.0, 1, GridBagConstraints.BOTH);
        panel.add(blankPanel, gbc);

        setGridBagConstraints(gbc, 0, 7, 1, 1, 0.1, 0.01, GridBagConstraints.BOTH);
        panel.add(loadCartButton, gbc);
        setGridBagConstraints(gbc, 1, 7, 1, 1, 0.1, 0.01, GridBagConstraints.BOTH);
        panel.add(saveCartButton, gbc);

        return panel;
    }


    public void createUI() {
        GridBagConstraints gbc = new GridBagConstraints();

        JFrame frame = createMainFrame();
        JPanel gridPanel = new JPanel(new GridBagLayout());

        JPanel farLeftPanel = createFarLeftPanel();
        JScrollPane middleLeftPanel = createMiddlePanel(libraryManager.getItemList());
        JScrollPane middleRightPanel = createMiddlePanel(libraryManager.getBasketList());
        JPanel farRightPanel = createFarRightPanel();

        setGridBagConstraints(gbc, 0, 0, 1, 4, 0.25, 1.0, GridBagConstraints.BOTH);
        gridPanel.add(farLeftPanel, gbc);

        setGridBagConstraints(gbc, 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.BOTH);
        gridPanel.add(middleLeftPanel, gbc);

        setGridBagConstraints(gbc, 2, 0, 1, 2, 1.0, 1.0, GridBagConstraints.BOTH);
        gridPanel.add(middleRightPanel, gbc);

        setGridBagConstraints(gbc, 3, 0, 1, 4, 0.1, 1.0, GridBagConstraints.BOTH);
        gridPanel.add(farRightPanel, gbc);

        frame.add(gridPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
