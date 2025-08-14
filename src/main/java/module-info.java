module com.example.newspaperproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.newspaperproject to javafx.fxml;
    exports com.example.newspaperproject;
    exports com.example.newspaperproject.papermaster;
    opens com.example.newspaperproject.papermaster to javafx.fxml;

    exports com.example.newspaperproject.hawkerConsole;
    opens com.example.newspaperproject.hawkerConsole to javafx.fxml;

    exports com.example.newspaperproject.area;
    opens com.example.newspaperproject.area to javafx.fxml;

    exports com.example.newspaperproject.customers;
    opens com.example.newspaperproject.customers to javafx.fxml;

    exports com.example.newspaperproject.billing;
    opens com.example.newspaperproject.billing to javafx.fxml;

    exports com.example.newspaperproject.dashboard;
    opens com.example.newspaperproject.dashboard to javafx.fxml;

    exports com.example.newspaperproject.billboard;
    opens com.example.newspaperproject.billboard to javafx.fxml;

    exports com.example.newspaperproject.billcollector;
    opens com.example.newspaperproject.billcollector to javafx.fxml;

    exports com.example.newspaperproject.customerboard;
    opens com.example.newspaperproject.customerboard to javafx.fxml;

}