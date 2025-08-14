package com.example.newspaperproject.papermaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class PaperMasterController {

    Connection con;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboPaperTitle;

    @FXML
    private TextField textLanguage;

    @FXML
    private TextField textPrice;

    @FXML
    private Label messageLabel;

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

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Paper not found");
        alert.setContentText("Please enter a valid newspaper title");
        alert.showAndWait();
    }

    @FXML
    void Delete(ActionEvent event) {
        String query="delete from paper where Title=?";
        try{
            PreparedStatement pst= con.prepareStatement(query);

            pst.setString(1,String.valueOf(comboPaperTitle.getValue()));

            int count=pst.executeUpdate();
            if(count==0) {
                System.out.println("Inavlid Newspaper");
                showAlert();
            }
            else {
                System.out.println("Record Deleted");
                showMessage("Record Deleted Successfully");
            }

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
        New(event);
    }

    @FXML
    void New(ActionEvent event) {
        textLanguage.setText(null);
        comboPaperTitle.setValue(null);
        textPrice.setText(null);
    }

    @FXML
    void Save(ActionEvent event) {
        String query="insert into paper values(?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,String.valueOf(comboPaperTitle.getValue()));
            pst.setString(2,textLanguage.getText());
            pst.setFloat(3,Float.parseFloat(textPrice.getText()));
            pst.executeUpdate();
            showMessage("Record Inserted Successfully");
            comboPaperTitle.getItems().add(comboPaperTitle.getValue().toString());
        }
        catch (Exception exp){
            System.out.println(exp);
        }

//        New(event);
    }

    @FXML
    void Update(ActionEvent event) {
        String query="update paper set price=? where Title=? and PaperLanguage=?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setFloat(1,Float.parseFloat(textPrice.getText()));
            pst.setString(2,String.valueOf(comboPaperTitle.getValue()));
            pst.setString(3,textLanguage.getText());

            int count=pst.executeUpdate();
            if(count==0) {
                System.out.println("Inavlid Newspaper");
                showAlert();
            }
            else {
                System.out.println("Record Updated");
                showMessage("Record Updated Successfully");
            }
        }
        catch (Exception exp){
            System.out.println(exp);
        }
        New(event);
    }

    @FXML
    void Find(ActionEvent event) {
        String query="select * from paper where Title=?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,String.valueOf(comboPaperTitle.getValue()));
            ResultSet set=pst.executeQuery();
            if(set.next()==true){
                String language=set.getString("PaperLanguage");
                float price=set.getFloat("Price");
                textLanguage.setText(language);
                textPrice.setText( String.valueOf( price));
            }
            else{
                showAlert();
            }
        }
        catch (Exception exp){
            System.out.println(exp);
        }
    }


    @FXML
    void initialize() {
        con= MySqlConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

        ArrayList<String> papersLst= getAllpapers();
        for(String  s : papersLst){
            comboPaperTitle.getItems().add(s);
        }

    }


    ArrayList<String> getAllpapers()
    {

        ArrayList<String> papers=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select Title from paper");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String paper=res.getString("Title");
                papers.add(paper);

            }
            System.out.println(papers);
        }
        catch(Exception exp) {
            exp.printStackTrace();
        }
        return papers;


    }

}


