package com.reveha.XChangeTry;

/**
 * Main Class in program used to start the process.
 * Displays bid-ask charts for two crypto currency exchanges:
 * BitStamp and LiteCoin for currency pairs BTC-USD and BTC-EUR.
 *
 * @author Orest Reveha
 * @version 1.0
 */
public final class XChangeDemo {
    /**
     * Runs this program.
     * Includes creating of GUI
     *
     * @param args for input parameters of type String
     */
    public static void main(final String[] args) {
        new MarketPollingGUI();
    }

    /**
     * Private constructor for XChangeDemo class.
     */
    public XChangeDemo() {

    }

}

