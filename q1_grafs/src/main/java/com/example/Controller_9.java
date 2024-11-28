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

public class Controller_9 {
    
    private ArrayList<String> sum22k;
    private ArrayList<Integer> sum22v;
    private ArrayList<String> sum23k;
    private ArrayList<Integer> sum23v;
    private int sum = 10;
    private int sum2 = 0;
    private int flag = 0;

    @FXML
    private TextField prodNum;

    @FXML
    private Button seeNext,seePrev,nextSum,prevSum;

    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private void goToMainMenu() throws IOException {
        App.setRoot("scene_1");
    }

    public void firstSet() throws IOException{
        
        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv","rw");
        String line,str[],product;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<LocalDate,Map<String,Integer>> dates = new TreeMap<>();
        Map<String,Integer> itemsBought;

        while((line = customerBuys.readLine()) != null){
            str = line.split(",");
            LocalDate dateBought = LocalDate.parse(str[1], dtf);
            product = str[2];

            if(dates.containsKey(dateBought)){
                if(dates.get(dateBought).containsKey(product)){
                    int sumProduct = dates.get(dateBought).get(product);
                    sumProduct = sumProduct + 1;
                    dates.get(dateBought).put(product, sumProduct);
                }
                else{
                    dates.get(dateBought).put(product, 1);
                }
            }
            else{
                itemsBought = new HashMap<>();
                itemsBought.put(product, 1);
                dates.put(dateBought, itemsBought);
            }
        }
        customerBuys.close();

        Map<String,Integer> summer22 = new HashMap<>();
        Map<String,Integer> summer23 = new HashMap<>();
        ArrayList<Map<String,Integer>> periods = new ArrayList<>();
        periods.add(summer22);
        periods.add(summer23);

        dates.forEach((date, map) -> {
            if(LocalDate.parse("15/06/22", dtf).compareTo(date) <= 0 && LocalDate.parse("01/09/22", dtf).compareTo(date) >=0){
                map.forEach((key, value) -> {
                    Map<String,Integer> summer = periods.get(0);
                    if(summer.containsKey(key)){
                        int timesBought = summer.get(key);
                        summer.put(key, timesBought+value);
                    }
                    else{
                        summer.put(key, value);
                    }
                    periods.set(0,summer);
                });   
            }
            else if(LocalDate.parse("15/06/23", dtf).compareTo(date) <= 0 && LocalDate.parse("01/09/23", dtf).compareTo(date) >=0){
                map.forEach((key, value) -> {
                    Map<String,Integer> summer = periods.get(1);
                    if(summer.containsKey(key)){
                        int timesBought = summer.get(key);
                        summer.put(key, timesBought+value);
                    }
                    else{
                        summer.put(key, value);
                    }
                    periods.set(1,summer);
                });   
            }
        });

        sum22k = new ArrayList<>();
        sum22v = new ArrayList<>();
        sum23k = new ArrayList<>();
        sum23v = new ArrayList<>();

        periods.get(0).forEach((key, value) -> {
            String decodedString = new String(key.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            sum22k.add(decodedString);
            sum22v.add(value);
        });

        for (int i = 0; i < sum22v.size(); i++) {
            for (int j = sum22v.size() - 1; j > i; j--) {
                if (sum22v.get(i) > sum22v.get(j)) {
                    int tmp1 = sum22v.get(i);
                    String tmp2 = sum22k.get(i);
                    sum22v.set(i,sum22v.get(j));
                    sum22k.set(i,sum22k.get(j));
                    sum22v.set(j,tmp1);
                    sum22k.set(j,tmp2);
                }
            }
        }

        periods.get(1).forEach((key, value) -> {
            String decodedString = new String(key.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            sum23k.add(decodedString);
            sum23v.add(value);
        });

        for (int i = 0; i < sum23v.size(); i++) {
            for (int j = sum23v.size() - 1; j > i; j--) {
                if (sum23v.get(i) > sum23v.get(j)) {
                    int tmp1 = sum23v.get(i);
                    String tmp2 = sum23k.get(i);
                    sum23v.set(i,sum23v.get(j));
                    sum23k.set(i,sum23k.get(j));
                    sum23v.set(j,tmp1);
                    sum23k.set(j,tmp2);
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
    private void showSum22(){
        flag = 0;
        fillChart();
        prevSum.setDisable(true);
        nextSum.setDisable(false);
    }

    @FXML
    private void showSum23(){
        flag = 1;
        fillChart1();
        nextSum.setDisable(true);
        prevSum.setDisable(false);
    }

    @FXML
    private void nextChart(){
        seePrev.setDisable(false);
        sum2+=sum;
        if(sum2 > 166){
            sum2 = 160;
            seeNext.setDisable(true);
        }
        if(flag == 0){
            fillChart();
        }
        else{
            fillChart1();
        }
    }

    @FXML
    private void previousChart(){
        seeNext.setDisable(false);
        sum2-=sum;
        if(sum2 <= 0){
            sum2 = 0;
            seePrev.setDisable(true);
        }
        if(flag == 0){
            fillChart();
        }
        else{
            fillChart1();
        }
    }

    @FXML
    private void changeChartSize(){
        try {
            int chartSize = Integer.parseInt(prodNum.getText());
            if(chartSize > 0 && chartSize <= 166){
                sum = chartSize;
            }
            else{
                sum = 10;
            }
        } catch (NumberFormatException e) {
            sum = 10;
        }
        if(flag == 0){
            fillChart();
        }
        else{
            fillChart1();
        }
    }

    private void fillChart(){
        barChart1.setTitle("Top products at summer 2022 with descending order");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for(int i=sum22k.size()-1-sum2; i>=0; i--){
            series1.getData().add(new XYChart.Data<>(sum22k.get(i), sum22v.get(i)));
            if(sum1 == sum-1){
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }

    private void fillChart1(){
        barChart1.setTitle("Top products at summer 2023 with descending order");
        barChart1.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1 = new XYChart.Series<>();
        int sum1 = 0;
        for(int i=sum23k.size()-1-sum2; i>=0; i--){
            series1.getData().add(new XYChart.Data<>(sum23k.get(i), sum23v.get(i)));
            if(sum1 == sum-1){
                break;
            }
            sum1++;
        }
        barChart1.getData().add(series1);
    }
}
