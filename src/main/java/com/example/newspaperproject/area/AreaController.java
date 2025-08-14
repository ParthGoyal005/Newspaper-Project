package com.example.newspaperproject.area;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.example.newspaperproject.MySqlConnection;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class AreaController {
    Connection con;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textArea;

    @FXML
    private Label messageLabel;

    @FXML
    void addArea(ActionEvent event) {
        String query= "insert into areas values(?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,textArea.getText());
            pst.executeUpdate();
            messageLabel.setText("Area added successfully");
            messageLabel.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.millis(2000));
            pause.setOnFinished(eventt -> {
                messageLabel.setVisible(false);
                messageLabel.setText("");
            });
            pause.play();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    }

}
