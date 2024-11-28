package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller_3 {

    private ArrayList<Integer> bestCustomers;
    private ArrayList<Integer> totalItemsBought;
    private int sum = 10;
    private int sum2 = 0;

    @FXML
    private TextField custNum;

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
        Map<Integer, Map<LocalDate, String>> customers = new TreeMap<>();
        Map<LocalDate, String> dateBuys;
        String line, str[], product;

        // Μap<key custID, value Map<key date, value basket>>
        while ((line = customerBuys.readLine()) != null) {

            str = line.split(",");
            int customerID = Integer.parseInt(str[0]);
            LocalDate dateBought = LocalDate.parse(str[1], dtf);
            product = str[2];

            if (customers.containsKey(customerID)) {
                if (customers.get(customerID).containsKey(dateBought)) {
                    String basket = customers.get(customerID).get(dateBought);
                    basket = basket + "," + product;
                    customers.get(customerID).put(dateBought, basket);
                } else {
                    customers.get(customerID).put(dateBought, product);
                }
            } else {
                dateBuys = new TreeMap<>();
                dateBuys.put(dateBought, product);
                customers.put(customerID, dateBuys);
            }
        }

        File oldFile = new File("customersBuyMost.csv");
        oldFile.delete();
        RandomAccessFile customersBuyMost = new RandomAccessFile("customersBuyMost.csv", "rw");
        bestCustomers = new ArrayList<>();
        totalItemsBought = new ArrayList<>();
        customers.forEach((customerID, map) -> {
            bestCustomers.add(customerID);
            int totalItems = 0;
            Set<Entry<LocalDate, String>> entries = map.entrySet();
            for (Entry<LocalDate, String> e : entries) {
                String tmp[] = e.getValue().split(",");
                totalItems = totalItems + tmp.length;
            }
            totalItemsBought.add(totalItems);
        });

        for (int i = 0; i < totalItemsBought.size(); i++) {
            for (int j = totalItemsBought.size() - 1; j > i; j--) {
                if (totalItemsBought.get(i) > totalItemsBought.get(j)) {
                    int tmp1 = totalItemsBought.get(i);
                    int tmp2 = bestCustomers.get(i);
                    totalItemsBought.set(i, totalItemsBought.get(j));
                    bestCustomers.set(i, bestCustomers.get(j));
                    totalItemsBought.set(j, tmp1);
                    bestCustomers.set(j, tmp2);
                }
            }
        }

        int sum1 = 0;
        for (int i = bestCustomers.size() - 1; i >= 0; i--) {
            sum1++;
            customersBuyMost.writeBytes("BestCustomer " + sum1 + " -> ID: " + bestCustomers.get(i)
                    + "  TotalProductsBought: " + totalItemsBought.get(i) + System.lineSeparator());
        }

        customersBuyMost.close();
        customerBuys.close();

        sum = 10;
        sum2 = 0;
        barChart1.setTitle("Top customers with descending order");
        fillChart();
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        custNum.setText("");

    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        if (sum2 > 3898) {
            sum2 = 3890;
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

    @FXML
    private void changeChartSize() {
        try {
            int chartSize = Integer.parseInt(custNum.getText());
            if (chartSize > 0 && chartSize < 101) {
                sum = chartSize;
            } else {
                sum = 10;
            }
        } catch (NumberFormatException e) {
            sum = 10;
        }
        fillChart();
    }

    private void fillChart() {
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for (int i = bestCustomers.size() - 1 - sum2; i >= 0; i--) {
            series1.getData().add(new XYChart.Data<>("ID: " + bestCustomers.get(i), totalItemsBought.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
