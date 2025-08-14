package com.example.newspaperproject.billboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;


    @FXML
    private TableView<CustomerBean> tableView;

    @FXML
    private ComboBox<String> billStatus;

    @FXML
    private Label txtAmount;

    @FXML
    void doFind(ActionEvent event) {
        TableColumn<CustomerBean, String> mobileC=new TableColumn<>("Mobile no.");//name of column
        mobileC.setCellValueFactory(new PropertyValueFactory<>("mobileC"));//from where to pick the data(name of variable in bean file)
        mobileC.setMinWidth(100);

        TableColumn<CustomerBean, String> startC=new TableColumn<>("Start Date");
        startC.setCellValueFactory(new PropertyValueFactory<>("startC"));
        startC.setMinWidth(100);

        TableColumn<CustomerBean, String> endC=new TableColumn<>("End Date");
        endC.setCellValueFactory(new PropertyValueFactory<>("endC"));
        endC.setMinWidth(100);

        TableColumn<CustomerBean, String> daysC=new TableColumn<>("Days");
        daysC.setCellValueFactory(new PropertyValueFactory<>("daysC"));
        daysC.setMinWidth(40);

        TableColumn<CustomerBean, String> amountC=new TableColumn<>("Amount");
        amountC.setCellValueFactory(new PropertyValueFactory<>("amountC"));
        amountC.setMinWidth(60);

        TableColumn<CustomerBean, String> statusC=new TableColumn<>("Status");
        statusC.setCellValueFactory(new PropertyValueFactory<>("statusC"));
        statusC.setMinWidth(100);

        tableView.getColumns().addAll(mobileC,startC,endC,daysC,amountC,statusC);

        tableView.setItems(getArrayOfObjects());

    }

    ObservableList<CustomerBean> getArrayOfObjects()
    {

        ObservableList<CustomerBean> list= FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from bill");
            ResultSet res= stmt.executeQuery();
            float myAmount=0;

            while(res.next())
            {
                String mobile=res.getString("mobile");
                String dos=String.valueOf(res.getDate("dos"));
                String doe=String.valueOf(res.getDate("doe"));

                String days=String.valueOf(res.getInt("days"));
                myAmount+=res.getFloat("bill");
                String bill=String.valueOf(res.getFloat("bill"));
                String status=String.valueOf(res.getInt("status"));


                CustomerBean emp=new CustomerBean(mobile,dos,doe,days,bill,status);
                list.add(emp);
            }
            txtAmount.setText(String.valueOf(myAmount));
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return  list;

    }


    Connection con;
    @FXML
    void initialize() {
        con= MySqlConnection.getMySQLDBConnection();
        if(con==null){
            System.out.println("Connection Error");
            return;
        }
        billStatus.getItems().add("All");
        billStatus.getItems().add("Paid");
        billStatus.getItems().add("Unpaid");

    }

}
