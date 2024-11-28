package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage stage;
    private static Scene scene;
    private static Controller_1 con1;
    private static Controller_2 con2;
    private static Controller_3 con3;
    private static Controller_4 con4;
    private static Controller_5 con5;
    private static Controller_6 con6;
    private static Controller_7 con7;
    private static Controller_8 con8;
    private static Controller_9 con9;
    private static Parent root1,root2,root3,root4,root5,root6,root7,root8,root9;
    private static FXMLLoader fxmlLoader1;
    private static FXMLLoader fxmlLoader2 = new FXMLLoader(App.class.getResource("scene_2.fxml"));
    private static FXMLLoader fxmlLoader3 = new FXMLLoader(App.class.getResource("scene_3.fxml"));
    private static FXMLLoader fxmlLoader4 = new FXMLLoader(App.class.getResource("scene_4.fxml"));
    private static FXMLLoader fxmlLoader5 = new FXMLLoader(App.class.getResource("scene_5.fxml"));
    private static FXMLLoader fxmlLoader6 = new FXMLLoader(App.class.getResource("scene_6.fxml"));
    private static FXMLLoader fxmlLoader7 = new FXMLLoader(App.class.getResource("scene_7.fxml"));
    private static FXMLLoader fxmlLoader8 = new FXMLLoader(App.class.getResource("scene_8.fxml"));
    private static FXMLLoader fxmlLoader9 = new FXMLLoader(App.class.getResource("scene_9.fxml"));

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("scene_1"), 1024, 600);
        stage.setTitle("Mini Market Analysis");
        stage.setMinWidth(900);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        
        if(fxml.equalsIgnoreCase("scene_1")){
            scene.setRoot(root1);
        }
        else if(fxml.equalsIgnoreCase("scene_2")){
            scene.setRoot(root2);
            con2.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_3")){
            scene.setRoot(root3);
            con3.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_4")){
            scene.setRoot(root4);
            con4.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_5")){
            scene.setRoot(root5);
            con5.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_6")){
            scene.setRoot(root6);
            con6.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_7")){
            scene.setRoot(root7);
            con7.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_8")){
            scene.setRoot(root8);
            con8.firstSet();
        }
        else if(fxml.equalsIgnoreCase("scene_9")){
            scene.setRoot(root9);
            con9.firstSet();
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader1 = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        root1 = fxmlLoader1.load();
        root2 = fxmlLoader2.load();
        root3 = fxmlLoader3.load();
        root4 = fxmlLoader4.load();
        root5 = fxmlLoader5.load();
        root6 = fxmlLoader6.load();
        root7 = fxmlLoader7.load();
        root8 = fxmlLoader8.load();
        root9 = fxmlLoader9.load();
        con1 = fxmlLoader1.getController();
        con2 = fxmlLoader2.getController();
        con3 = fxmlLoader3.getController();
        con4 = fxmlLoader4.getController();
        con5 = fxmlLoader5.getController();
        con6 = fxmlLoader6.getController();
        con7 = fxmlLoader7.getController();
        con8 = fxmlLoader8.getController();
        con9 = fxmlLoader9.getController();
        return root1;
    }

    public static void main(String[] args) throws IOException {
        deleteFiles();
        launch();
    }

    public static void arrfConfirm(){
        File file = new File("arrffFile.csv");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("File path: " + file.getAbsolutePath());
        alert.setHeaderText("Arrff file created successfully");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.DECORATED);
        alert.show();
    }

    public static void arrfConfirm2(){
        File file = new File("categoryArrffFile.csv");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("File path: " + file.getAbsolutePath());
        alert.setHeaderText("Category arrff file created successfully");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.DECORATED);
        alert.show();
    }

    public static void arrfConfirm3(){
        File file = new File("arrffFile18-35.csv");
        File file1 = new File("arrffFile36-59.csv");
        File file2 = new File("arrffFile60-80.csv");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("1st File path: " + file.getAbsolutePath() + System.lineSeparator() + "2nd File path: " + file1.getAbsolutePath() + System.lineSeparator() + "3rd File path: " + file2.getAbsolutePath());
        alert.setHeaderText("Category arrff file created successfully");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.DECORATED);
        alert.show();
    }
    
    private static void deleteFiles(){
        File oldFile = new File("monthBuys.csv");
        File oldFile1 = new File("arrffFile.csv");
        File oldFile2 = new File("productsFile.csv");
        File oldFile3 = new File("arrffFile18-35.csv");
        File oldFile4 = new File("arrffFile36-59.csv");
        File oldFile5 = new File("arrffFile60-80.csv");
        File oldFile6 = new File("topDaysPerYear.csv");
        File oldFile7 = new File("customersBuyMost.csv");
        File oldFile8 = new File("categoryArrffFile.csv");
        File oldFile9 = new File("weeksTransactions.csv");
        oldFile.delete();
        oldFile1.delete();
        oldFile2.delete();
        oldFile3.delete();
        oldFile4.delete();
        oldFile5.delete();
        oldFile6.delete();
        oldFile7.delete();
        oldFile8.delete();
        oldFile9.delete();
    }

}