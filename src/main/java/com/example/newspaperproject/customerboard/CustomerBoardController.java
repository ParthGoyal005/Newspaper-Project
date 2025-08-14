package com.example.newspaperproject.customerboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import com.example.newspaperproject.billcollector.BillBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboHawker;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<CustomerBean> tableView;

    @FXML
    void fillTable(ActionEvent event) {

        TableColumn<CustomerBean, String> mobileC=new TableColumn<>("Mobile no.");
        mobileC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobileC.setMinWidth(100);

        TableColumn<CustomerBean, String> cnameC=new TableColumn<>("Name");
        cnameC.setCellValueFactory(new PropertyValueFactory<>("cname"));
        cnameC.setMinWidth(100);

        TableColumn<CustomerBean, String> emailC=new TableColumn<>("Email Id");
        emailC.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailC.setMinWidth(100);

        TableColumn<CustomerBean, String> addressC=new TableColumn<>("Address");
        addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressC.setMinWidth(100);

        TableColumn<CustomerBean, String> startC=new TableColumn<>("Start Date");
        startC.setCellValueFactory(new PropertyValueFactory<>("dos"));
        startC.setMinWidth(100);

        TableColumn<CustomerBean, String> areaC=new TableColumn<>("Area");
        areaC.setCellValueFactory(new PropertyValueFactory<>("area"));
        areaC.setMinWidth(100);

        TableColumn<CustomerBean, String> hawkerC=new TableColumn<>("Hawker");
        hawkerC.setCellValueFactory(new PropertyValueFactory<>("hawker"));
        hawkerC.setMinWidth(100);

        TableColumn<CustomerBean, String> paperC=new TableColumn<>("Papers");
        paperC.setCellValueFactory(new PropertyValueFactory<>("paper"));
        paperC.setMinWidth(100);

        TableColumn<CustomerBean, String> priceC=new TableColumn<>("Prices");
        priceC.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceC.setMinWidth(100);

        TableColumn<CustomerBean, String> statusC=new TableColumn<>("Status");
        statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusC.setMinWidth(100);

        tableView.getColumns().addAll(mobileC,cnameC,emailC,addressC,startC,areaC,hawkerC,paperC,priceC,statusC);

        tableView.setItems(getFilteredCustomers());
    }

    ObservableList<CustomerBean> getFilteredCustomers() {
        ObservableList<CustomerBean> list = FXCollections.observableArrayList();

        try {
            String area = comboArea.getValue();
            String paper = comboPaper.getValue();
            String hawker = comboHawker.getValue();

            PreparedStatement pst = null;

            // CASE 1: All none
            if (area.equals("none") && paper.equals("none") && hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers");
            }
            // CASE 2: Only area
            else if (!area.equals("none") && paper.equals("none") && hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE area = ?");
                pst.setString(1, area);
            }
            // CASE 3: Only paper
            else if (area.equals("none") && !paper.equals("none") && hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE paper LIKE ?");
                pst.setString(1, "%" + paper + "%");
            }
            // CASE 4: Only hawker
            else if (area.equals("none") && paper.equals("none") && !hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE hawkerid = ?");
                pst.setString(1, hawker);
            }
            // CASE 5: Area + paper
            else if (!area.equals("none") && !paper.equals("none") && hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND paper LIKE ?");
                pst.setString(1, area);
                pst.setString(2, "%" + paper + "%");
            }
            // CASE 6: Area + hawker
            else if (!area.equals("none") && paper.equals("none") && !hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND hawkerid = ?");
                pst.setString(1, area);
                pst.setString(2, hawker);
            }
            // CASE 7: Paper + hawker
            else if (area.equals("none") && !paper.equals("none") && !hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE paper LIKE ? AND hawkerid = ?");
                pst.setString(1, "%" + paper + "%");
                pst.setString(2, hawker);
            }
            // CASE 8: All three selected
            else if (!area.equals("none") && !paper.equals("none") && !hawker.equals("none")) {
                pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND papers LIKE ? AND hawkerid = ?");
                pst.setString(1, area);
                pst.setString(2, "%" + paper + "%");
                pst.setString(3, hawker);
            }

            // Execute the query and build the list
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                String mobile = res.getString("mobile");
                String name = res.getString("cname");
                String email = res.getString("emailid");
                String address = res.getString("address");
                Date dos = res.getDate("dos");
                String areaVal = res.getString("area");
                String harkerid = res.getString("hawkerid");
                String paperVal = res.getString("papers");
                String temp = res.getString("prices");
                String[] parts=temp.split(",");
                float prices=0;
                for(String part : parts)
                {
                    prices+= Float.parseFloat(part);
                }

                int status = res.getInt("status");

                CustomerBean cbn = new CustomerBean(
                        mobile, name, email, address,dos.toString(),areaVal,
                        harkerid, paperVal, String.valueOf(prices), String.valueOf(status)
                );
                list.add(cbn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    Connection con;
    @FXML
    void initialize() {
        con= MySqlConnection.getMySQLDBConnection();
        if(con==null){
            System.out.println("Connection Error");
            return;
        }

        ArrayList<String> listt = getAreas();
        comboArea.getItems().addAll(listt);

        ArrayList<String> listt2 = getPaper();
        comboPaper.getItems().addAll(listt2);

        ArrayList<String> listt3 = getHawker();
        comboHawker.getItems().addAll(listt3);
    }

    ArrayList<String> getAreas()
    {
        ArrayList<String> list=new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("select Area from areas");
            ResultSet array = pst.executeQuery();
            while(array.next())
            {
                list.add(array.getString("Area"));
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
        return list;
    }

    ArrayList<String> getPaper()
    {
        ArrayList<String> list=new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("select Title from paper");
            ResultSet array = pst.executeQuery();
            while(array.next())
            {
                list.add(array.getString("Title"));
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
        return list;
    }

    ArrayList<String> getHawker()
    {
        ArrayList<String> list=new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("select Hawkerid from hawkers");
            ResultSet array = pst.executeQuery();
            while(array.next())
            {
                list.add(array.getString("Hawkerid"));
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
        return list;
    }

}
