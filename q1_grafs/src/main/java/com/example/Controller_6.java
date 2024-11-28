package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class Controller_6 {

    private Map<String, Integer> months22 = new LinkedHashMap<>();
    private Map<String, Integer> months23 = new LinkedHashMap<>();
    private ArrayList<String> month;
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

        month = new ArrayList<>();
        Value = new ArrayList<>();

        datesMap.forEach((key, value) -> {
            if (LocalDate.parse("31/01/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("January", 22, value.size());
            } else if (LocalDate.parse("28/02/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("February", 22, value.size());
            } else if (LocalDate.parse("31/03/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("March", 22, value.size());
            } else if (LocalDate.parse("30/04/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("April", 22, value.size());
            } else if (LocalDate.parse("31/05/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("May", 22, value.size());
            } else if (LocalDate.parse("30/06/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("June", 22, value.size());
            } else if (LocalDate.parse("31/07/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("July", 22, value.size());
            } else if (LocalDate.parse("31/08/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("August", 22, value.size());
            } else if (LocalDate.parse("30/09/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("September", 22, value.size());
            } else if (LocalDate.parse("31/10/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("October", 22, value.size());
            } else if (LocalDate.parse("30/11/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("November", 22, value.size());
            } else if (LocalDate.parse("31/12/22", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("December", 22, value.size());
            } else if (LocalDate.parse("31/01/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("January", 23, value.size());
            } else if (LocalDate.parse("28/02/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("February", 23, value.size());
            } else if (LocalDate.parse("31/03/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("March", 23, value.size());
            } else if (LocalDate.parse("30/04/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("April", 23, value.size());
            } else if (LocalDate.parse("31/05/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("May", 23, value.size());
            } else if (LocalDate.parse("30/06/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("June", 23, value.size());
            } else if (LocalDate.parse("31/07/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("July", 23, value.size());
            } else if (LocalDate.parse("31/08/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("August", 23, value.size());
            } else if (LocalDate.parse("30/09/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("September", 23, value.size());
            } else if (LocalDate.parse("31/10/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("October", 23, value.size());
            } else if (LocalDate.parse("30/11/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("November", 23, value.size());
            } else if (LocalDate.parse("31/12/23", dtf).compareTo(key) >= 0) {
                fillingMonthsMaps("December", 23, value.size());
            }
        });

        File oldFile = new File("monthBuys.csv");
        oldFile.delete();
        RandomAccessFile monthBuys = new RandomAccessFile("monthBuys.csv", "rw");
        monthBuys.writeBytes("Year 2022 Top Sales On Each Month" + System.lineSeparator());
        months22.forEach((key, value) -> {
            month.add(key);
            Value.add(value);
            try {
                monthBuys.writeBytes(key + ": " + value + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        monthBuys.writeBytes(System.lineSeparator() + "Year 2023 Top Sales On Each Month" + System.lineSeparator());
        months23.forEach((key, value) -> {
            month.add(key);
            Value.add(value);
            try {
                monthBuys.writeBytes(key + ": " + value + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sum = 12;
        sum2 = 0;
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        monthBuys.close();
        fillChart();
    }

    private void fillingMonthsMaps(String month, int year, int value) {
        if (year == 22) {
            if (months22.containsKey(month)) {
                int transacts = months22.get(month);
                months22.put(month, value + transacts);
            } else {
                months22.put(month, value);
            }
        } else {
            if (months23.containsKey(month)) {
                int transacts = months23.get(month);
                months23.put(month, value + transacts);
            } else {
                months23.put(month, value);
            }
        }
    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        if (sum2 >= 12) {
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
        if (sum2 == 0) {
            barChart1.setTitle("2022 Transactions");
        } else {
            barChart1.setTitle("2023 Transactions");
        }
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = 0 + sum2; i < month.size(); i++) {
            series1.getData().add(new XYChart.Data<>(month.get(i), Value.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
