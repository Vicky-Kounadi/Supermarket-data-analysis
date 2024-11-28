package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class Controller_4 {
    private Map<String, Integer> TopDay22;
    private Map<String, Integer> TopDay23;
    private ArrayList<LocalDate> Key;
    private ArrayList<Integer> Value;
    private int sum = 0;
    private int sum2 = 0;

    @FXML
    private Button seeNext, seePrev;

    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private void goToMainMenu() throws IOException {
        App.setRoot("scene_1");
    }

    public void firstSet() throws IOException {

        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv", "rw");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<LocalDate, Map<Integer, String>> datesMap = new TreeMap<>();
        Map<Integer, String> transactionsMap;
        String line, str[];

        while ((line = customerBuys.readLine()) != null) {

            str = line.split(",");
            int customerID = Integer.parseInt(str[0]);
            LocalDate dateBought = LocalDate.parse(str[1], dtf);

            if (datesMap.containsKey(dateBought)) {
                if (datesMap.get(dateBought).containsKey(customerID) != true) {
                    datesMap.get(dateBought).put(customerID, "");
                }
            } else {
                transactionsMap = new TreeMap<>();
                transactionsMap.put(customerID, "");
                datesMap.put(dateBought, transactionsMap);
            }
        }
        customerBuys.close();

        File oldFile = new File("weeksTransactions.csv");
        oldFile.delete();
        RandomAccessFile weeks = new RandomAccessFile("weeksTransactions.csv", "rw");
        File oldFile1 = new File("topDaysPerYear.csv");
        oldFile1.delete();
        RandomAccessFile topDays = new RandomAccessFile("topDaysPerYear.csv", "rw");
        Key = new ArrayList<>();
        Value = new ArrayList<>();
        TopDay22 = new LinkedHashMap<>();
        TopDay23 = new LinkedHashMap<>();
        int days = 0;
        Set<Entry<LocalDate, Map<Integer, String>>> entries = datesMap.entrySet();
        for (Entry<LocalDate, Map<Integer, String>> e : entries) {

            if (days == 7) {
                days = 0;
                weeks.writeBytes(System.lineSeparator());
            }

            if (days == 0) {
                weeks.writeBytes(e.getKey() + "-> Sunday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Sunday", e.getValue().size());
            } else if (days == 1) {
                weeks.writeBytes(e.getKey() + "-> Monday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Monday", e.getValue().size());
            } else if (days == 2) {
                weeks.writeBytes(e.getKey() + "-> Tuesday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Tuesday", e.getValue().size());
            } else if (days == 3) {
                weeks.writeBytes(e.getKey() + "-> Wednesday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Wednesday", e.getValue().size());
            } else if (days == 4) {
                weeks.writeBytes(e.getKey() + "-> Thursday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Thursday", e.getValue().size());
            } else if (days == 5) {
                weeks.writeBytes(e.getKey() + "-> Friday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Friday", e.getValue().size());
            } else if (days == 6) {
                weeks.writeBytes(e.getKey() + "-> Saturday" + "  Total Transactions: " + e.getValue().size()
                        + System.lineSeparator());
                fillingTopDaysMaps(e.getKey(), "Saturday", e.getValue().size());
            }
            days++;
            Key.add(e.getKey());
            Value.add(e.getValue().size());
        }

        topDays.writeBytes("Year 2022 Top Sales Day" + System.lineSeparator());
        TopDay22.forEach((key, value) -> {
            try {
                topDays.writeBytes(key + ": " + value + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        topDays.writeBytes(System.lineSeparator() + "Year 2023 Top Sales Day" + System.lineSeparator());
        TopDay23.forEach((key, value) -> {
            try {
                topDays.writeBytes(key + ": " + value + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        weeks.close();
        topDays.close();

        sum = 7;
        sum2 = 0;
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        fillChart();
    }

    private void fillingTopDaysMaps(LocalDate date, String day, int transactions) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        if (LocalDate.parse("01/01/23", dtf).compareTo(date) >= 0) {
            if (TopDay22.containsKey(day)) {
                int transacts = TopDay22.get(day);
                TopDay22.put(day, transactions + transacts);
            } else {
                TopDay22.put(day, transactions);
            }
        } else {
            if (TopDay23.containsKey(day)) {
                int transacts = TopDay23.get(day);
                TopDay23.put(day, transactions + transacts);
            } else {
                TopDay23.put(day, transactions);
            }
        }
    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        if (sum2 > 730) {
            sum2 = 728;
            seeNext.setDisable(true);
        }
        fillChart();
    }

    @FXML
    private void previousChart() {
        seeNext.setDisable(false);
        sum2 -= sum;
        if (sum2 <= 0) {
            sum2 = 0;
            seePrev.setDisable(true);
        }
        fillChart();
    }

    private void fillChart() {
        barChart1.setTitle("Most Transactions Per Week");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = 0 + sum2; i < Key.size(); i++) {
            if (sum1 == 0) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Sunday", Value.get(i)));
            } else if (sum1 == 1) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Monday", Value.get(i)));
            } else if (sum1 == 2) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Tuesday", Value.get(i)));
            } else if (sum1 == 3) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Wednesday", Value.get(i)));
            } else if (sum1 == 4) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Thursday", Value.get(i)));
            } else if (sum1 == 5) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Friday", Value.get(i)));
            } else if (sum1 == 6) {
                series1.getData().add(new XYChart.Data<>(Key.get(i) + " Saturday", Value.get(i)));
            }

            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
