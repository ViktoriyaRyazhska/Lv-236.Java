package com.reveha.XChangeTry;

import org.knowm.xchange.currency.CurrencyPair;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MarketPollingGUI class is base class to provide graphical user interface.
 * Simple window that allows to set up properties,
 * and calculate results with graphic presentation next.
 *
 * @author Orest Reveha
 */
public class MarketPollingGUI extends JFrame {
    /**
     * Dimension to set combobox size.
     */
    private static final Dimension CURRENCY_COMBOBOX_DIMENSION =
            new Dimension(60, 20);
    /**
     * Dimension to set root panel size.
     */
    private static final Dimension ROOT_PANEL_DIMENSION =
            new Dimension(1350, 450);
    /**
     * Main panel.
     */
    private JPanel rootpanel = new JPanel(new BorderLayout());
    /**
     * Center panel.
     */
    private JPanel centerpanel = new JPanel(new FlowLayout());
    /**
     * Panel that contains currency.
     */
    private JPanel currencyPanel = new JPanel(new FlowLayout());
    /**
     * Combobox for first currency (left).
     */
    private JComboBox currencyOneComboBox;
    /**
     * Combobox for second currency (right).
     */
    private JComboBox currencyTwoComboBox;
    /**
     * Label 1.
     */
    private JLabel currencyOneLabel = new JLabel("Currency 1: ");
    /**
     * Label 2.
     */
    private JLabel currencyTwoLabel = new JLabel("Currency 2: ");
    /**
     * Button that starts calculating.
     */
    private JButton goButton = new JButton("Go");
    /**
     * Option pane.
     */
    private JOptionPane optionPane = new JOptionPane();

    /**
     * Creating graphical user interface.
     */
    public MarketPollingGUI() {
        super("xChange Demo");

        String[] cryptos = new String[]{"BTC"};
        currencyOneComboBox = new JComboBox(cryptos);
        currencyOneComboBox.setSelectedIndex(-1);
        currencyOneComboBox.setPreferredSize(CURRENCY_COMBOBOX_DIMENSION);

        String[] curs = new String[]{"USD", "EUR"};
        currencyTwoComboBox = new JComboBox(curs);
        currencyTwoComboBox.setSelectedIndex(-1);
        currencyTwoComboBox.setPreferredSize(CURRENCY_COMBOBOX_DIMENSION);

        currencyPanel.add(currencyOneLabel);
        currencyPanel.add(currencyOneComboBox);
        currencyPanel.add(currencyTwoLabel);
        currencyPanel.add(currencyTwoComboBox);
        currencyPanel.add(goButton);
        currencyPanel.setVisible(true);

        centerpanel.setVisible(true);
        optionPane.setVisible(false);

        rootpanel.add(currencyPanel, BorderLayout.NORTH);
        rootpanel.setPreferredSize(ROOT_PANEL_DIMENSION);
        rootpanel.setVisible(true);
        rootpanel.add(centerpanel, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootpanel);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);

        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                new Thread(new MarketPollingDemoExchanges(
                        MarketPollingGUI.this)).start();
            }
        });
    }

    /**
     * Creating a crypto-currency pair.
     *
     * @return pair of two selected options - crypto and currency;
     * <code>null</code> in case some option haven't been chosen
     */
    public CurrencyPair getCurrencyPair() {
        String currencyOne = currencyOneComboBox.getSelectedItem().toString();
        String currencyTwo = currencyTwoComboBox.getSelectedItem().toString();
        if (currencyOne != null && currencyTwo != null) {
            return new CurrencyPair(currencyOne, currencyTwo);
        }
        return null;
    }

    /**
     * Provide access for option pane for members out of this class.
     *
     * @return optionPane
     */
    public JOptionPane getOptionPane() {
        return optionPane;
    }

    /**
     * Provide access for center panel for members out of this class.
     *
     * @return centerpanel
     */
    public JPanel getCenterpanel() {
        return centerpanel;
    }
}
