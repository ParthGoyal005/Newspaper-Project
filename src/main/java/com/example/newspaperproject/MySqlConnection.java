package com.example.newspaperproject;
import java.sql.*;
public class MySqlConnection
{
    public static Connection getMySQLDBConnection()
    {
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/NewspaperProject","root","Pgs_7958");
        }
        catch(Exception exp)
        {
            System.out.println(exp.toString());
        }
        return con;

    }
}
