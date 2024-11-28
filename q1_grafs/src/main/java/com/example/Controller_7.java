package com.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller_7 {

    private ArrayList<String> xmas22k;
    private ArrayList<Integer> xmas22v;
    private ArrayList<String> xmas23k;
    private ArrayList<Integer> xmas23v;
    private int sum = 10;
    private int sum2 = 0;
    private int flag = 0;

    @FXML
    private TextField prodNum;

    @FXML
    private Button seeNext, seePrev, nextSum, prevSum;

    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private void goToMainMenu() throws IOException {
        App.setRoot("scene_1");
    }

    public void firstSet() throws IOException {

        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv", "rw");
        String line, str[], product;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<LocalDate, Map<String, Integer>> dates = new TreeMap<>();
        Map<String, Integer> itemsBought;

        while ((line = customerBuys.readLine()) != null) {
            str = line.split(",");
            LocalDate dateBought = LocalDate.parse(str[1], dtf);
            product = str[2];

            if (dates.containsKey(dateBought)) {
                if (dates.get(dateBought).containsKey(product)) {
                    int sumProduct = dates.get(dateBought).get(product);
                    sumProduct = sumProduct + 1;
                    dates.get(dateBought).put(product, sumProduct);
                } else {
                    dates.get(dateBought).put(product, 1);
                }
            } else {
                itemsBought = new HashMap<>();
                itemsBought.put(product, 1);
                dates.put(dateBought, itemsBought);
            }
        }
        customerBuys.close();

        Map<String, Integer> xmas22 = new HashMap<>();
        Map<String, Integer> xmas23 = new HashMap<>();
        ArrayList<Map<String, Integer>> periods = new ArrayList<>();
        periods.add(xmas22);
        periods.add(xmas23);

        dates.forEach((date, map) -> {

            if (LocalDate.parse("15/12/22", dtf).compareTo(date) <= 0
                    && LocalDate.parse("15/01/23", dtf).compareTo(date) >= 0) {
                map.forEach((key, value) -> {
                    Map<String, Integer> xmas = periods.get(0);
                    if (xmas.containsKey(key)) {
                        int timesBought = xmas.get(key);
                        xmas.put(key, timesBought + value);
                    } else {
                        xmas.put(key, value);
                    }
                    periods.set(0, xmas);
                });
            } else if (LocalDate.parse("15/12/23", dtf).compareTo(date) <= 0
                    && LocalDate.parse("31/12/23", dtf).compareTo(date) >= 0) {
                map.forEach((key, value) -> {
                    Map<String, Integer> xmas = periods.get(1);
                    if (xmas.containsKey(key)) {
                        int timesBought = xmas.get(key);
                        xmas.put(key, timesBought + value);
                    } else {
                        xmas.put(key, value);
                    }
                    periods.set(1, xmas);
                });
            }
        });

        xmas22k = new ArrayList<>();
        xmas22v = new ArrayList<>();
        xmas23k = new ArrayList<>();
        xmas23v = new ArrayList<>();

        periods.get(0).forEach((key, value) -> {
            String decodedString = new String(key.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            xmas22k.add(decodedString);
            xmas22v.add(value);
        });

        for (int i = 0; i < xmas22v.size(); i++) {
            for (int j = xmas22v.size() - 1; j > i; j--) {
                if (xmas22v.get(i) > xmas22v.get(j)) {
                    int tmp1 = xmas22v.get(i);
                    String tmp2 = xmas22k.get(i);
                    xmas22v.set(i, xmas22v.get(j));
                    xmas22k.set(i, xmas22k.get(j));
                    xmas22v.set(j, tmp1);
                    xmas22k.set(j, tmp2);
                }
            }
        }

        periods.get(1).forEach((key, value) -> {
            String decodedString = new String(key.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            xmas23k.add(decodedString);
            xmas23v.add(value);
        });

        for (int i = 0; i < xmas23v.size(); i++) {
            for (int j = xmas23v.size() - 1; j > i; j--) {
                if (xmas23v.get(i) > xmas23v.get(j)) {
                    int tmp1 = xmas23v.get(i);
                    String tmp2 = xmas23k.get(i);
                    xmas23v.set(i, xmas23v.get(j));
                    xmas23k.set(i, xmas23k.get(j));
                    xmas23v.set(j, tmp1);
                    xmas23k.set(j, tmp2);
                }
            }
        }

        sum = 10;
        sum2 = 0;
        flag = 0;
        prevSum.setDisable(true);
        nextSum.setDisable(false);
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        prodNum.setText("");
        fillChart();
    }

    @FXML
    private void showSum22() {
        flag = 0;
        fillChart();
        prevSum.setDisable(true);
        nextSum.setDisable(false);
    }

    @FXML
    private void showSum23() {
        flag = 1;
        fillChart1();
        nextSum.setDisable(true);
        prevSum.setDisable(false);
    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        if (sum2 > 166) {
            sum2 = 160;
            seeNext.setDisable(true);
        }
        if (flag == 0) {
            fillChart();
        } else {
            fillChart1();
        }
    }

    @FXML
    private void previousChart() {
        seeNext.setDisable(false);
        sum2 -= sum;
        if (sum2 <= 0) {
            sum2 = 0;
            seePrev.setDisable(true);
        }
        if (flag == 0) {
            fillChart();
        } else {
            fillChart1();
        }
    }

    @FXML
    private void changeChartSize() {
        try {
            int chartSize = Integer.parseInt(prodNum.getText());
            if (chartSize > 0 && chartSize <= 166) {
                sum = chartSize;
            } else {
                sum = 10;
            }
        } catch (NumberFormatException e) {
            sum = 10;
        }
        if (flag == 0) {
            fillChart();
        } else {
            fillChart1();
        }
    }

    private void fillChart() {
        barChart1.setTitle("Top products on xmas 2022 with descending order");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = xmas22k.size() - 1 - sum2; i >= 0; i--) {
            series1.getData().add(new XYChart.Data<>(xmas22k.get(i), xmas22v.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }

    private void fillChart1() {
        barChart1.setTitle("Top products on xmas 2023 with descending order");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = xmas23k.size() - 1 - sum2; i >= 0; i--) {
            series1.getData().add(new XYChart.Data<>(xmas23k.get(i), xmas23v.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
