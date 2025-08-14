package com.example.newspaperproject.billcollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import com.example.newspaperproject.billboard.CustomerBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillCollectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<BillBean> tableView;

    @FXML
    private Label txtAmount;

    @FXML
    private TextField txtMobile;

    @FXML
    void fillTable(ActionEvent event) {
        TableColumn<BillBean, String> startC=new TableColumn<>("Start Date");
        startC.setCellValueFactory(new PropertyValueFactory<>("startC"));
        startC.setMinWidth(100);

        TableColumn<BillBean, String> endC=new TableColumn<>("End Date");
        endC.setCellValueFactory(new PropertyValueFactory<>("endC"));
        endC.setMinWidth(100);

        TableColumn<BillBean, String> daysC=new TableColumn<>("Days");
        daysC.setCellValueFactory(new PropertyValueFactory<>("daysC"));
        daysC.setMinWidth(40);

        TableColumn<BillBean, String> amountC=new TableColumn<>("Amount");
        amountC.setCellValueFactory(new PropertyValueFactory<>("amountC"));
        amountC.setMinWidth(60);

        tableView.getColumns().addAll(startC,endC,daysC,amountC);

        tableView.setItems(getArrayOfObjects());
    }

    ObservableList<BillBean> getArrayOfObjects()
    {

        ObservableList<BillBean> list= FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from bill where mobile=? and status = 1");
            stmt.setString(1,txtMobile.getText());
            ResultSet res= stmt.executeQuery();
            float myAmount=0;

            while(res.next())
            {

                String dos=String.valueOf(res.getDate("dos"));
                String doe=String.valueOf(res.getDate("doe"));

                String days=String.valueOf(res.getInt("days"));
                myAmount+=res.getFloat("bill");
                String bill=String.valueOf(res.getFloat("bill"));


                BillBean emp=new BillBean(dos,doe,days,bill);
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
    }

}
