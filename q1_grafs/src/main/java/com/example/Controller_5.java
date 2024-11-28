package com.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class Controller_5 {

    private ArrayList<LocalDate> Key;
    private ArrayList<Integer> Value;
    private int sum = 0;
    private int sum2 = 0;
    private int flag = 0;

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

        Key = new ArrayList<>();
        Value = new ArrayList<>();
        datesMap.forEach((key, value) -> {
            Key.add(key);
            Value.add(value.size());
        });

        sum = 31;
        flag = 0;
        sum2 = 0;
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        fillChart();
    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        flag++;
        if (flag == 0 || flag == 2 || flag == 4 || flag == 6 || flag == 7 || flag == 9 || flag == 11) {
            sum = 31;
        } else if (flag == 1) {
            sum = 28;
        } else if (flag == 3 || flag == 5 || flag == 8 || flag == 10) {
            sum = 30;
        }
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
        flag--;
        if (flag == 0 || flag == 2 || flag == 4 || flag == 6 || flag == 7 || flag == 9 || flag == 11) {
            sum = 31;
        } else if (flag == 1) {
            sum = 28;
        } else if (flag == 3 || flag == 5 || flag == 8 || flag == 10) {
            sum = 30;
        }
        if (sum2 <= 0) {
            sum2 = 0;
            seePrev.setDisable(true);
        }
        fillChart();
    }

    private void fillChart() {
        barChart1.setTitle("Most Transactions Per Month");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = 0 + sum2; i < Key.size(); i++) {
            series1.getData().add(new XYChart.Data<>(Key.get(i) + "", Value.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
