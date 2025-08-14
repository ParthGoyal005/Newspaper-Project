package com.example.newspaperproject.customers;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class CustomerController {

    Connection con;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Comboareas;

    @FXML
    private ComboBox<String> CombohawkerId;

    @FXML
    private DatePicker dateofstart;

    @FXML
    private ListView<String> lstSelPapers;

    @FXML
    private ListView<String> lstSelPrices;

    @FXML
    private ListView<String> lstpaper;

    @FXML
    private ListView<String> lstprices;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtemail;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField txtmobileno;

    @FXML
    void 	showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Customer not found");
        alert.setContentText("Please enter a valid Mobile Number");
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

    @FXML
    void doaddlstselprices(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            lstSelPapers.getItems().add(lstpaper.getSelectionModel().getSelectedItem());
            int ind=lstpaper.getSelectionModel().getSelectedIndex();
            lstprices.getSelectionModel().select(ind);
            lstSelPrices.getItems().add(lstprices.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void dochange(ActionEvent event) {
        String query="update customers set emailid=?, address=?,dos=?,area=?,hawkerid=?,papers=?,prices=?  where mobile=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,txtemail.getText());
            pst.setString(2,txtAddress.getText());
            LocalDate lcl= dateofstart.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(3, dt);
            pst.setString(4,Comboareas.getValue());
            pst.setString(5,CombohawkerId.getValue());
            ObservableList<String> items = lstSelPapers.getItems();
            String paper="";
            for(String s:items)
            {
                paper = paper + "," + s;

            }
            pst.setString(6,paper);
            String price="";
            ObservableList<String> itemprice = lstSelPrices.getItems();
            for(String s:itemprice)
            {
                price=price + "," + s;

            }
            pst.setString(7,price);
            pst.setString(8,txtmobileno.getText());


            int count=pst.executeUpdate();
            if(count==0) {
                showAlert();
            }
            else {
                showMessage("Record updated successfully");
            }
        }
        catch(Exception exp) {
            System.out.println(exp);
        }

    }

    @FXML
    void doclear(ActionEvent event) {
        txtmobileno.setText("");
        txtName.setText("");
        txtemail.setText("");
        txtAddress.setText("");
        dateofstart.setValue(null);
        Comboareas.setValue(null);
        CombohawkerId.setValue(null);
        lstSelPrices.getItems().clear();
        lstSelPapers.getItems().clear();
    }

    @FXML
    void doremovecustomer(ActionEvent event) {
        try {
            String query = "delete from customers where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtmobileno.getText());

            int c= pst.executeUpdate();
            if(c==0)
            {
                showAlert();
            }
            else{
                showMessage("Record deleted successfully");
                doclear(event);
            }

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }
//    mobile varchar(100) primary key,cname varchar(100),emailid varchar(100),address varchar(100),dos date,area varchar(100),hawkerid varchar(100),papers varchar(100),prices varchar(100),status int

    @FXML
    void dosavecustomer(ActionEvent event) {
        try {
            String query = "insert into customers values(?,?,?,?,?,?,?,?,?,1)";
            PreparedStatement pst = con.prepareStatement(query);


            pst.setString(1,txtmobileno.getText());

            pst.setString(2,txtName.getText());
            pst.setString(3,txtemail.getText());

            pst.setString(4,txtAddress.getText());
            LocalDate lcl=dateofstart.getValue();
            Date dt= Date.valueOf(lcl);
            pst.setDate(5,dt);

            pst.setString(6,Comboareas.getValue());

            pst.setString(7,CombohawkerId.getValue());

            ObservableList<String> items = lstSelPapers.getItems();
            String paper="";
            for(String s:items)
            {
                paper=paper +s + ",";

            }
            pst.setString(8,paper);
            String price="";
            ObservableList<String> itemprice = lstSelPrices.getItems();
            for(String s:itemprice)
            {
                price=price  + s + ",";

            }
            pst.setString(9,price);
            pst.executeUpdate();
            showMessage("Record Saved successfully");

        }
        catch(Exception exp)
        {

            System.out.println(exp.getMessage());

        }


    }

    @FXML
    void dosearch(ActionEvent event) {
        try {
            String query = "select * from customers where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtmobileno.getText());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                txtName.setText(res.getString("cname"));
                txtemail.setText(res.getString("emailid"));
                txtAddress.setText(res.getString("address"));
                dateofstart.setValue(res.getDate("dos").toLocalDate());

                Comboareas.setValue(res.getString("area"));
                CombohawkerId.setValue(res.getString("hawkerid"));
                String s=res.getString("papers");
                String[] ary=s.split(",");
                for(String m:ary)
                {
                    lstSelPapers.getItems().add(m);
                }
                String spr=res.getString("prices");
                String[] arypr=spr.split(",");
                for(String m:arypr)
                {
                    lstSelPrices.getItems().add(m);
                }

            }
            else{
                showAlert();
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }
    @FXML
    void dofillhawkerid(ActionEvent event) {
        CombohawkerId.getItems().clear();
//        System.out.println("running");
        try {
            String query = "select Hawkerid from hawkers where SelectedAreas like ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,"%" + Comboareas.getValue() +"%");
            ResultSet res=pst.executeQuery();
            while(res.next())
            {
                String id=res.getString("Hawkerid");
                CombohawkerId.getItems().add(id);
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }


    }
    ArrayList<String> aryprices=new ArrayList<String>();
    ArrayList<String>arypaper=new ArrayList<String>();



    @FXML
    void initialize() {

        con= MySqlConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection error");
            return;
        }

        getpapersandprices();
        getareas();

    }
    void  getpapersandprices ()
    {

        try
        {
            PreparedStatement stmt = con.prepareStatement("select Title,Price from paper");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String paper=res.getString("Title");
                Float price=res.getFloat("Price");
                arypaper.add(paper);
                aryprices.add(String.valueOf(price));

            }
            for(int i=0;i<aryprices.size();i++)
            {
                String paper= arypaper.get(i);
                lstpaper.getItems().add(paper);
                String prices=aryprices.get(i);
                lstprices.getItems().add(prices);
            }

            System.out.println(arypaper);
            System.out.println(aryprices);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }


    ArrayList<String> getareas()
    {

        ArrayList<String> pt=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select distinct Area from areas");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String area=res.getString("Area");
                pt.add(area);

            }
            for(String s:pt)
            {
                Comboareas.getItems().add(s);
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return pt;


    }

}
