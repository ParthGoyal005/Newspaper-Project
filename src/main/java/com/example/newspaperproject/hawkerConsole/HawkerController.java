package com.example.newspaperproject.hawkerConsole;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class HawkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAllAreas;

    @FXML
    private ComboBox<String> comboHawkerIds;

    @FXML
    private DatePicker doj;

    @FXML
    private ImageView hawkerImage;

    @FXML
    private ImageView logo;

    @FXML
    private TextField textAadhaar;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textContact;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSelectedAreas;

    @FXML
    private Label messageLabel;

    String path;
    Image img;
    Connection con;

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Hawker not found");
        alert.setContentText("Please enter a valid Hawker ID");
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
    void Fetch(ActionEvent event) {
        try {
            PreparedStatement stmt = con.prepareStatement("select * from hawkers where Hawkerid=?");
            stmt.setString(1,comboHawkerIds.getValue());
            ResultSet res= stmt.executeQuery();

            if(res.next()==true)
            {
                String name=res.getString("Name");
                String contact=res.getString("Contact");
                String address=res.getString("Address");
                String adhaar=res.getString("Aadhaar");
                java.sql.Date dojj=res.getDate("dob");
                String picPath=res.getString("Picpath");
                String selareas=res.getString("SelectedAreas");

                String[] sel=selareas.split(",");

                for(String str:sel){
                    comboAllAreas.getItems().add(str);
                }

                textName.setText(name);
                textName.setDisable(true);
                textContact.setText(contact);
                textContact.setDisable(true);
                textAddress.setText(address);
                textAadhaar.setText(adhaar);
                doj.setValue(dojj.toLocalDate());
                hawkerImage.setImage(new Image(new FileInputStream(picPath)));
                path=picPath;
                textSelectedAreas.setText(selareas);

            }
            else {
                System.out.println("Invalid ID");
                showAlert();
            }

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }


    @FXML
    void Browse(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.jpg","*.png") );
        File file= chooser.showOpenDialog(null);
        path=(file.getAbsolutePath());

        try {

            hawkerImage.setImage(new Image(new FileInputStream(file)));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Clear(ActionEvent event) {
        String basePath="C:\\Users\\parth\\Desktop\\Java\\NewspaperProject\\src\\main\\resources\\com\\example\\newspaperproject\\hawkerConsole\\profile-picture.png";
        comboHawkerIds.setValue(null);
        comboAllAreas.setValue(null);

        textAadhaar.setText(null);
        textAadhaar.setDisable(false);

        textName.setText(null);
        textName.setDisable(false);

        textContact.setText(null);
        textContact.setDisable(false);

        textAddress.setText(null);
        textSelectedAreas.setText(null);
        doj.setValue(null);
        try {
            hawkerImage.setImage(new Image(new FileInputStream(new File(basePath))));
        }
        catch (Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        String query="delete from hawkers where hawkerid=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);

            pst.setString(1,comboHawkerIds.getValue());

            int count=pst.executeUpdate();
            if(count==0) {
                System.out.println("Invalid Id");
                showAlert();
            }

            else {
                System.out.println("Record Deleted");
                showMessage("Record deleted successfully");
            }

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
        comboHawkerIds.getItems().remove(comboHawkerIds.getValue());
        Clear(event);
    }

    @FXML
    void Save(ActionEvent event) {
        String query="insert into hawkers values(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);

            String name=textName.getText();
            String contact=textContact.getText();
            String hawkerID;
            if(name.length()>=5) {
                hawkerID = name.substring(0, 5) + contact.substring(contact.length()-5,contact.length());
            }
            else {
                hawkerID=name.substring(0,name.length())+contact.substring(contact.length()-5,contact.length());
            }
            pst.setString(1,hawkerID);
            pst.setString(2,name);
            pst.setString(3,contact);
            pst.setString(4,textAddress.getText());
            pst.setString(5,textAadhaar.getText());


            LocalDate lcl= doj.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(6, dt);

            pst.setString(7,path);
            pst.setString(8,textSelectedAreas.getText());
            pst.executeUpdate();
            comboHawkerIds.getItems().add(hawkerID);
        }
        catch (Exception exp){
            System.out.println(exp);
        }

//        Clear(event);
    }

    @FXML
    void Update(ActionEvent event) {
        String query="update hawkers set Address=?, Dob=?, Picpath=?,SelectedAreas=? where Hawkerid=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,textAddress.getText());

            LocalDate lcl= doj.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(2, dt);

//            if(path==null){
//                ResultSet res=getsinglehawker(ComboHawkerID.getValue());
//                path=res.getString("picpath");
//            }

            pst.setString(3,path);
            pst.setString(4,textSelectedAreas.getText());
            pst.setString(5,comboHawkerIds.getValue());

            int count=pst.executeUpdate();
            if(count==0) {
                System.out.println("Invalid Id");
                showAlert();
            }
            else {
                System.out.println("Record Updated");
                showMessage("Record updated successfully");
            }

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
//        Clear(event);
    }

    @FXML
    void setSelected(ActionEvent event) {
        String selectedArea = comboAllAreas.getValue();
        if (selectedArea == null || selectedArea.trim().isEmpty())
        {
            return;
        }

        String currentText = textSelectedAreas.getText();
        ArrayList<String> selectedAreas = new ArrayList<>();

        // Convert existing text to a list (if any)
        if (!currentText.trim().isEmpty() && currentText!=null) {
            String[] parts = currentText.split(",\\s*");
            for (String part : parts) {
                selectedAreas.add(part.trim());
            }
        }

        // Add only if not already present
        if (!selectedAreas.contains(selectedArea)) {
            selectedAreas.add(selectedArea);
        }

        // Join and update text field
        textSelectedAreas.setText(String.join(", ", selectedAreas));
    }

    @FXML
    void initialize() {
        con= MySqlConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

        ArrayList<String> lstAreas=getAllAreas();
        for(String str: lstAreas){
            comboAllAreas.getItems().add(str);
        }
        ArrayList<String> lstHawkerID=getHawkerID();
        for(String str: lstHawkerID){
            comboHawkerIds.getItems().add(str);
        }

    }

    ArrayList<String> getAllAreas(){
        ArrayList<String> Areas=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select Area from areas");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String area=res.getString("Area");
                Areas.add(area);

            }
            System.out.println(Areas);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return Areas;
    }

    ArrayList<String> getHawkerID(){
        ArrayList<String> HawkerIDs=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select Hawkerid from hawkers");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String hawkerID=res.getString("Hawkerid");
                HawkerIDs.add(hawkerID);

            }
            System.out.println(HawkerIDs);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return HawkerIDs;
    }

}
