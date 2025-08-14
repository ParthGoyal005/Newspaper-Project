package com.example.newspaperproject.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.newspaperproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void openArea(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("area/AreaView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Area");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openBillBoard(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billboard/BillboardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("BillBoard");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openBillCollector(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billcollector/BillCollectorView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 442, 400);
            stage.setTitle("Bill Collector");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openBilling(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billing/BillingView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 626, 428);
            stage.setTitle("Hawker Console");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openCustomerBoard(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customerboard/CustomerBoardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Customer Bard");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openCustomerConsole(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customers/CustomerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 665, 494);
            stage.setTitle("Hawker Console");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openHawkerConsole(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hawkerConsole/HawkerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 483);
            stage.setTitle("Hawker Console");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void openPapermaster(MouseEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("papermaster/PaperMasterView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("PaperMaster");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }



    @FXML
    void initialize() {

    }

}
