package com.example.newspaperproject.billing;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class BillingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker DatepickerDU;

    @FXML
    private TextField TxtBillamount;

    @FXML
    private Label lblchange;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtdos;

    @FXML
    private TextField txtlessdays;

    @FXML
    private TextField txtmobile;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField txttotalprice;


    @FXML
    private DatePicker DPdos;

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Customer not found");
        alert.setContentText("Please enter a valid Mobile no.");
        alert.showAndWait();
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(event -> {
            messageLabel.setVisible(false);
            messageLabel.setText("");
        });
        pause.play();
    }

    void doupdate() {
        String query = "update customers set dos =? where mobile =? ";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            LocalDate doe = DatepickerDU.getValue();
            Date dt = Date.valueOf(doe.plusDays(1));

            pst.setDate(1, dt);
            pst.setString(2, txtmobile.getText());
            pst.executeUpdate();
            System.out.println("Date updated in customer table");
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
//            showerrmsg(exp.getMessage());

        }
    }

    @FXML
    void DoSaveBill(ActionEvent event) {
        try {
            saveInDB(0);
            doupdate();
            showMessage("Record saved successfully");


        } catch (Exception e) {
            e.printStackTrace();
            lblchange.setText("Error ");
        }
    }


    @FXML
    void doGenerateBill(ActionEvent event) {
        try {
            java.time.LocalDate dos = DPdos.getValue();
            java.time.LocalDate doe = DatepickerDU.getValue();

            int totalDays = (int) java.time.temporal.ChronoUnit.DAYS.between(dos, doe);
            int lessDays = Integer.parseInt(txtlessdays.getText());
            int billableDays = totalDays - lessDays;

            float dailyPrice = Float.parseFloat(txttotalprice.getText());
            float bill = billableDays * dailyPrice;

            TxtBillamount.setText(String.valueOf(bill));

            saveInDB(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void saveInDB(int status){
        String mobile = txtmobile.getText();
        LocalDate dos = DPdos.getValue();
        LocalDate doe = DatepickerDU.getValue();
        String billText = TxtBillamount.getText();
        String daysText = txtlessdays.getText();

        java.sql.Date sqlDos = java.sql.Date.valueOf(dos);
        java.sql.Date sqlDoe = java.sql.Date.valueOf(doe);

        int days = 0;
        try {
            days = Integer.parseInt(daysText);
        } catch (NumberFormatException e) {
            txtlessdays.setText("0");
            days = 0;
        }
        System.out.println("chlra1");
        float billAmount = Float.parseFloat(billText);

        String query = "INSERT INTO bill (mobile, dos, doe, days, bill, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, mobile);
            pst.setDate(2, sqlDos);
            pst.setDate(3, sqlDoe);
            pst.setInt(4, days);
            pst.setFloat(5, billAmount);
            pst.setInt(6,status);
            System.out.println("chlra2");
            pst.executeUpdate();
        }
        catch(Exception e){
            e.getMessage();
        }
    }


    @FXML
    void dofind(ActionEvent event) {
        String mobile = txtmobile.getText();

        try {
            String query = "SELECT cname, dos, prices FROM customers WHERE mobile = ? AND status = 1";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, mobile);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("cname"));
                DPdos.setValue(rs.getDate("dos").toLocalDate());

                // Sum prices
                String prices = rs.getString("prices");
                System.out.println(prices);
                float total = 0;
                if (prices != null && !prices.isEmpty()) {
                    for (String p : prices.split(",")) {
                        total += Float.parseFloat(p);
                    }
                }
                txttotalprice.setText(String.valueOf(total));
            }
            else{
                showAlert();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Connection con;
    @FXML
    void initialize() {
        con= MySqlConnection.getMySQLDBConnection();
        if(con==null){
            System.out.println("Connection Error");
            return;
        }
        //txtlessdays.setText("0");
        txtlessdays.setEditable(true);
        txtlessdays.setText("0");

    }

}
