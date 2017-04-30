package com.reveha.XChangeTry;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.Series;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class performs all calculating and sends data to GUI.
 * Main purpose is to make operations with data
 * to prepare graphical output to user.
 * Comparing pairs BTC-USD and BTC-EUR that will
 * allows user to chose best scenario.
 *
 * @author Orest Reveha
 */
public class MarketPollingDemoExchanges implements Runnable {
    /**
     * Instance of MarketPollingGUI class.
     */
    private MarketPollingGUI gui;
    /**
     * Threshold for asks.
     */
    private static final int ASKS_THRESHOLD = 10000;
    /**
     * Threshold for bids.
     */
    private static final int BIDS_THRESHOLD = 10;
    /**
     * Bitstamp istancee.
     */
    private static Exchange bitstamp;
    /**
     * Livecoin instance.
     */
    private static Exchange liveCoin;

    /**
     * Creating new MarketPollingDemoExchanges class.
     *
     * @param inputGui is to decide what GUI will be used
     */
    public MarketPollingDemoExchanges(final MarketPollingGUI inputGui) {
        gui = inputGui;
    }

    /**
     * Runs main calculating process that consists from several stages.
     * <ul>
     * <li>Getting exchange APIs for both Bitstamp and Bitkonan</li>
     * <li>Getting marked data</li>
     * <li>Creating orders from checked options in GUI</li>
     * <li>Getting Bids and Asks</li>
     * <li>Creating chart</li>
     * <li>Sending output to GUI</li>
     * </ul>
     */
    public void run() {
        // Use the factory to get the version 1 Bitstamp and Bitkonan
        // exchange API using default settings
        initBitstampAndLivecoin();
        // Interested in the public market data feed (no authentication)
        MarketDataService bitstampDataService = bitstamp.getMarketDataService();
        MarketDataService liveCoinDataService = liveCoin.getMarketDataService();
        // Get the current orderbooks
        OrderBook orderBookBitStamp = null;
        OrderBook orderBookLiveCoin = null;
        try {
            orderBookBitStamp = bitstampDataService.
                    getOrderBook(gui.getCurrencyPair());
            orderBookLiveCoin = liveCoinDataService.
                    getOrderBook(gui.getCurrencyPair());
        } catch (IOException e) {
            gui.getOptionPane().showMessageDialog(gui, "Invalid currency pair");
        }

        System.out.println("received data.");

        System.out.println("plotting...");


        // get BIDS
        ArrayList<BigDecimal> xBidData = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> yBidData = new ArrayList<BigDecimal>();
        getBids(orderBookBitStamp, xBidData, yBidData, BIDS_THRESHOLD);

        // Create Chart, add bids
        XYChart chart = QuickChart.getChart(
                "BitStamp Order Book",
                "USD", "BTC",
                "BitStamp bids",
                xBidData, yBidData);

        // get ASKS
        ArrayList<BigDecimal> xAskData = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> yAskData = new ArrayList<BigDecimal>();
        getAsks(orderBookBitStamp, xAskData, yAskData, ASKS_THRESHOLD);

        // add Asks Series to chart
        Series series = chart.addSeries("BitStamp asks", xAskData, yAskData);


        ArrayList<BigDecimal> xBidDataTwo = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> yBidDataTwo = new ArrayList<BigDecimal>();
        getBids(orderBookLiveCoin, xBidDataTwo, yBidDataTwo, BIDS_THRESHOLD);

        XYChart chart2 = QuickChart.getChart("LiveCoin Order Book",
                "USD", "BTC", "LiveCoin bids",
                xBidDataTwo, yBidDataTwo);

        //other asks Asks
        ArrayList<BigDecimal> xAskdataTwo = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> yAskDataTwo = new ArrayList<BigDecimal>();

        getAsks(orderBookLiveCoin, xAskdataTwo, yAskDataTwo, ASKS_THRESHOLD);

        series = chart2.addSeries("LiveCoin asks", xAskdataTwo, yAskDataTwo);

        //adding charts to list for wrapper
        List<XYChart> charts = new ArrayList<XYChart>();
        charts.add(chart);
        charts.add(chart2);

        XChartPanel chartPanel = new XChartPanel(chart);
        XChartPanel chartPanel2 = new XChartPanel(chart2);
        chartPanel.setVisible(true);
        chartPanel2.setVisible(true);
        gui.getCenterpanel().removeAll();
        gui.getCenterpanel().add(chartPanel).setVisible(true);
        gui.getCenterpanel().add(chartPanel2).setVisible(true);
        gui.getCenterpanel().updateUI();

    }

    /**
     * Use the factory to get the version 1 Bitstamp and Bitkonan
     * exchange API using default settings.
     */
    private void initBitstampAndLivecoin() {
        bitstamp = ExchangeFactory.INSTANCE.
                createExchange(BitstampExchange.class.getName());
        liveCoin = ExchangeFactory.INSTANCE.
                createExchange(LivecoinExchange.class.getName());
    }

    /**
     * Creating bids according to input data.
     *
     * @param orderBook Orders to use
     * @param xData     x Data
     * @param yData     y Data
     * @param threshold threshold to compare with
     */
    private void getBids(final OrderBook orderBook,
                         final ArrayList<BigDecimal> xData,
                         final ArrayList<BigDecimal> yData,
                         final int threshold) {
        BigDecimal accumulatedUnits = new BigDecimal("0");
        for (LimitOrder limitOrder : orderBook.getBids()) {
            if (limitOrder.getLimitPrice().intValue() > threshold) {
                xData.add(limitOrder.getLimitPrice());
                accumulatedUnits = accumulatedUnits.add(
                        limitOrder.getTradableAmount());
                yData.add(accumulatedUnits);
            }
        }
        Collections.reverse(xData);
        Collections.reverse(yData);
    }

    /**
     * Creating asks according to input data.
     *
     * @param orderBook Orders to use
     * @param xData     x Data
     * @param yData     y Data
     * @param threshold threshold to compare with
     */
    private void getAsks(final OrderBook orderBook,
                         final ArrayList<BigDecimal> xData,
                         final ArrayList<BigDecimal> yData,
                         final int threshold) {
        BigDecimal accumulatedUnits = new BigDecimal("0");
        for (LimitOrder limitOrder : orderBook.getAsks()) {
            if (limitOrder.getLimitPrice().intValue() < threshold) {
                xData.add(limitOrder.getLimitPrice());
                accumulatedUnits = accumulatedUnits.
                        add(limitOrder.getTradableAmount());
                yData.add(accumulatedUnits);
            }
        }
    }
}
