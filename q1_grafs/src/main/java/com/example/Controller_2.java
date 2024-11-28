package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller_2 {

    private ArrayList<String> Key;
    private ArrayList<String> keyfile;
    private ArrayList<Integer> Value;
    private int sum = 10;
    private int sum2 = 0;

    @FXML
    private TextField prodNum;

    @FXML
    private Button seeNext, seePrev;

    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private void goToMainMenu() throws IOException {
        App.setRoot("scene_1");
    }

    // break down the file and count times
    // σε TreeMap με key τις εμφανίσεις, όπου οι εμφανίσεις ήταν ίσες θα χάναμε
    // προϊόντα
    public void firstSet() throws IOException {
        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv", "rw");
        Map<String, Integer> products = new HashMap<>();
        String line, str[], product;
        while ((line = customerBuys.readLine()) != null) {
            str = line.split(",");
            product = str[2];
            if (products.containsKey(product)) {
                int timesBought = products.get(product);
                products.put(product, timesBought + 1);
            } else {
                products.put(product, 1);
            }
        }

        // create the file and delete old
        File oldFile = new File("productsFile.csv");
        oldFile.delete();

        // keyfile and Value is for RANDOMACCESSFILE
        // put in Key the product, in Value the times for DIAGRAM
        // the string needs to be decoded to UTF-8 for the Key (so it can be read by
        // FXML)
        RandomAccessFile productsFile = new RandomAccessFile("productsFile.csv", "rw");
        Key = new ArrayList<>();
        keyfile = new ArrayList<>();
        Value = new ArrayList<>();
        products.forEach((key, value) -> {
            keyfile.add(key);
            String decodedString = new String(key.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            Key.add(decodedString);
            Value.add(value);
        });

        // sort arraylists based on value
        for (int i = 0; i < Value.size(); i++) {
            for (int j = Value.size() - 1; j > i; j--) {
                if (Value.get(i) > Value.get(j)) {
                    int tmp1 = Value.get(i);
                    String tmp2 = Key.get(i);
                    String tmp3 = keyfile.get(i);
                    Value.set(i, Value.get(j));
                    Key.set(i, Key.get(j));
                    keyfile.set(i, keyfile.get(j));
                    Value.set(j, tmp1);
                    Key.set(j, tmp2);
                    keyfile.set(j, tmp3);
                }
            }
        }

        // fill RAF with products and value from arraylists
        for (int i = 0; i < Value.size(); i++) {
            productsFile.writeBytes(keyfile.get(i) + "," + Value.get(i) + System.lineSeparator());
        }

        productsFile.close();
        customerBuys.close();

        sum = 10;
        sum2 = 0;
        barChart1.setTitle("Top products with descending order");
        fillChart();
        seeNext.setDisable(false);
        seePrev.setDisable(true);
        prodNum.setText("");
    }

    @FXML
    private void nextChart() {
        seePrev.setDisable(false);
        sum2 += sum;
        if (sum2 > 166) {
            sum2 = 160;
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
            int chartSize = Integer.parseInt(prodNum.getText());
            if (chartSize > 0 && chartSize <= 166) {
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
        for (int i = Key.size() - 1 - sum2; i >= 0; i--) {
            series1.getData().add(new XYChart.Data<>(Key.get(i), Value.get(i)));
            if (sum1 == sum - 1) {
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }

}