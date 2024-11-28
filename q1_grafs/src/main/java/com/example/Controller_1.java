package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javafx.fxml.FXML;

public class Controller_1 {

    @FXML
    private void crateArrfFile() throws IOException {
        // break down the file and count times
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

        // put in key the product, in value the times
        ArrayList<String> Key = new ArrayList<>();
        ArrayList<Integer> Value = new ArrayList<>();

        products.forEach((key, value) -> {
            Key.add(key);
            Value.add(value);
        });

        // sort based on value
        for (int i = 0; i < Value.size(); i++) {
            for (int j = Value.size() - 1; j > i; j--) {
                if (Value.get(i) > Value.get(j)) {
                    int tmp1 = Value.get(i);
                    String tmp2 = Key.get(i);
                    Value.set(i, Value.get(j));
                    Key.set(i, Key.get(j));
                    Value.set(j, tmp1);
                    Key.set(j, tmp2);
                }
            }
        }

        File oldFile = new File("arrffFile.csv");
        oldFile.delete();
        RandomAccessFile arrffFile = new RandomAccessFile("arrffFile.csv", "rw");
        arrffFile.writeBytes(Key.get(0));
        for (int i = 1; i < Key.size() - 1; i++) {
            arrffFile.writeBytes("," + Key.get(i));
        }
        arrffFile.writeBytes("," + Key.get(Key.size() - 1) + System.lineSeparator());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<Integer, Map<LocalDate, String>> customers = new TreeMap<>();
        Map<LocalDate, String> dateBuys;
        customerBuys.seek(0);

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

        customers.forEach((customerID, map) -> {

            Set<Entry<LocalDate, String>> entries = map.entrySet();
            for (Entry<LocalDate, String> e : entries) {

                String tmp[] = e.getValue().split(",");
                Map<String, String> arrf = new LinkedHashMap<>();
                for (int k = 0; k < Key.size() - 1; k++) {
                    arrf.put(Key.get(k), ",");
                }
                arrf.put(Key.get(Key.size() - 1), "" + System.lineSeparator());

                for (String item : tmp) {
                    if (arrf.get(item).contains("t") != true) {
                        arrf.put(item, "t" + arrf.get(item));
                    }
                }
                arrf.forEach((k, v) -> {
                    try {
                        arrffFile.writeBytes(v);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        });
        arrffFile.close();
        customerBuys.close();
        App.arrfConfirm();
    }

    @FXML
    private void showTopProducts() throws IOException {
        App.setRoot("scene_2");
    }

    @FXML
    private void showTopCustomers() throws IOException {
        App.setRoot("scene_3");
    }

    @FXML
    private void showTopProductsPerWeek() throws IOException {
        App.setRoot("scene_4");
    }

    @FXML
    private void showTopProductsPerMonth() throws IOException {
        App.setRoot("scene_5");
    }

    @FXML
    private void showTopProductsPerYear() throws IOException {
        App.setRoot("scene_6");
    }

    @FXML
    private void showTopProductsOnXmas() throws IOException {
        App.setRoot("scene_7");
    }

    @FXML
    private void showTopProductsOnEaster() throws IOException {
        App.setRoot("scene_8");
    }

    @FXML
    private void showTopProductsOnSummer() throws IOException {
        App.setRoot("scene_9");
    }

    @FXML
    private void crateCategoryArrfFile() throws IOException {
        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv", "rw");
        RandomAccessFile productCategories = new RandomAccessFile("categories.csv", "rw");
        Map<String, Integer> products = new HashMap<>();
        String line, str[], product, productType;
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

        Map<String, String> productsTypes = new HashMap<>();
        while ((line = productCategories.readLine()) != null) {
            str = line.split(",");
            product = str[0];
            productType = str[1];
            if (productsTypes.containsKey(productType)) {
                String items = productsTypes.get(productType);
                productsTypes.put(productType, items + "," + str[0]);
            } else {
                productsTypes.put(productType, str[0]);
            }
        }

        ArrayList<String> Key = new ArrayList<>();
        ArrayList<String> Value = new ArrayList<>();

        productsTypes.forEach((key, value) -> {
            Key.add(key);
            Value.add(value);
        });

        File oldFile = new File("categoryArrffFile.csv");
        oldFile.delete();
        RandomAccessFile arrffFile2 = new RandomAccessFile("categoryArrffFile.csv", "rw");
        arrffFile2.writeBytes(Key.get(0));
        for (int i = 1; i < Key.size() - 1; i++) {
            arrffFile2.writeBytes("," + Key.get(i));
        }
        arrffFile2.writeBytes("," + Key.get(Key.size() - 1) + System.lineSeparator());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<Integer, Map<LocalDate, String>> customers = new TreeMap<>();
        Map<LocalDate, String> dateBuys;
        customerBuys.seek(0);

        while ((line = customerBuys.readLine()) != null) {

            str = line.split(",");
            int customerID = Integer.parseInt(str[0]);
            LocalDate dateBought = LocalDate.parse(str[1], dtf);
            product = str[2];

            Set<Entry<String, String>> entries = productsTypes.entrySet();
            for (Entry<String, String> e : entries) {
                if (e.getValue().contains(product)) {
                    product = e.getKey();
                }
            }

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

        customers.forEach((customerID, map) -> {

            Set<Entry<LocalDate, String>> entries = map.entrySet();
            for (Entry<LocalDate, String> e : entries) {

                String tmp[] = e.getValue().split(",");
                Map<String, String> arrf = new LinkedHashMap<>();
                for (int k = 0; k < Key.size() - 1; k++) {
                    arrf.put(Key.get(k), ",");
                }
                arrf.put(Key.get(Key.size() - 1), "" + System.lineSeparator());

                for (String item : tmp) {
                    if (arrf.get(item).contains("t") != true) {
                        arrf.put(item, "t" + arrf.get(item));
                    }
                }
                arrf.forEach((k, v) -> {
                    try {
                        arrffFile2.writeBytes(v);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        });

        productCategories.close();
        arrffFile2.close();
        customerBuys.close();
        App.arrfConfirm2();
    }

    @FXML
    private void crateAgeCategoryArrffFiles() throws IOException {
        RandomAccessFile customerBuys = new RandomAccessFile("customerBuys.csv", "rw");
        RandomAccessFile customerAges = new RandomAccessFile("customerAge.csv", "rw");
        RandomAccessFile productCategories = new RandomAccessFile("categories.csv", "rw");

        Map<String, Integer> products = new HashMap<>();
        String line, str[], product, productType;
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

        Map<String, String> productsTypes = new HashMap<>();
        while ((line = productCategories.readLine()) != null) {
            str = line.split(",");
            product = str[0];
            productType = str[1];
            if (productsTypes.containsKey(productType)) {
                String items = productsTypes.get(productType);
                productsTypes.put(productType, items + "," + str[0]);
            } else {
                productsTypes.put(productType, str[0]);
            }
        }

        ArrayList<String> Key = new ArrayList<>();
        ArrayList<String> Value = new ArrayList<>();

        productsTypes.forEach((key, value) -> {
            Key.add(key);
            Value.add(value);
        });

        File oldFile = new File("arrffFile18-35.csv");
        oldFile.delete();
        File oldFile1 = new File("arrffFile36-59.csv");
        oldFile1.delete();
        File oldFile2 = new File("arrffFile60-80.csv");
        oldFile2.delete();
        RandomAccessFile arrffFile18 = new RandomAccessFile("arrffFile18-35.csv", "rw");
        RandomAccessFile arrffFile36 = new RandomAccessFile("arrffFile36-59.csv", "rw");
        RandomAccessFile arrffFile60 = new RandomAccessFile("arrffFile60-80.csv", "rw");
        arrffFile18.writeBytes(Key.get(0));
        arrffFile36.writeBytes(Key.get(0));
        arrffFile60.writeBytes(Key.get(0));
        for (int i = 1; i < Key.size() - 1; i++) {
            arrffFile18.writeBytes("," + Key.get(i));
            arrffFile36.writeBytes("," + Key.get(i));
            arrffFile60.writeBytes("," + Key.get(i));
        }
        arrffFile18.writeBytes("," + Key.get(Key.size() - 1) + System.lineSeparator());
        arrffFile36.writeBytes("," + Key.get(Key.size() - 1) + System.lineSeparator());
        arrffFile60.writeBytes("," + Key.get(Key.size() - 1) + System.lineSeparator());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        Map<Integer, Map<LocalDate, String>> customers = new TreeMap<>();
        Map<LocalDate, String> dateBuys;
        customerBuys.seek(0);

        while ((line = customerBuys.readLine()) != null) {

            str = line.split(",");
            int customerID = Integer.parseInt(str[0]);
            LocalDate dateBought = LocalDate.parse(str[1], dtf);
            product = str[2];

            Set<Entry<String, String>> entries = productsTypes.entrySet();
            for (Entry<String, String> e : entries) {
                if (e.getValue().contains(product)) {
                    product = e.getKey();
                }
            }

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

        Map<Integer, Integer> customersAgeMap = new TreeMap<>();
        while ((line = customerAges.readLine()) != null) {
            str = line.split(",");
            int customerID = Integer.parseInt(str[0]);
            int customerAge = Integer.parseInt(str[1]);
            customersAgeMap.put(customerID, customerAge);
        }

        customers.forEach((customerID, map) -> {

            Set<Entry<LocalDate, String>> entries = map.entrySet();
            for (Entry<LocalDate, String> e : entries) {

                String tmp[] = e.getValue().split(",");
                Map<String, String> arrf = new LinkedHashMap<>();
                for (int k = 0; k < Key.size() - 1; k++) {
                    arrf.put(Key.get(k), ",");
                }
                arrf.put(Key.get(Key.size() - 1), "" + System.lineSeparator());

                for (String item : tmp) {
                    if (arrf.get(item).contains("t") != true) {
                        arrf.put(item, "t" + arrf.get(item));
                    }
                }

                Set<Entry<String, String>> entries1 = arrf.entrySet();
                for (Entry<String, String> e1 : entries1) {
                    try {
                        if (customersAgeMap.get(customerID) <= 35) {
                            arrffFile18.writeBytes(e1.getValue());
                        } else if (customersAgeMap.get(customerID) <= 59) {
                            arrffFile36.writeBytes(e1.getValue());
                        } else {
                            arrffFile60.writeBytes(e1.getValue());
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });

        productCategories.close();
        customerAges.close();
        customerBuys.close();
        arrffFile18.close();
        arrffFile36.close();
        arrffFile60.close();
        App.arrfConfirm3();

    }

}
