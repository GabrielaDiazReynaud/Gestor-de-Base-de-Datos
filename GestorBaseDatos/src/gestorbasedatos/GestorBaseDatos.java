/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriela Diaz R
 */
public class GestorBaseDatos {

    /**
     * @param args the command line arguments
     */
       public static Frame frame;
       public static Conexion conexion;
      
    public static void main(String[] args){
     /* String url= "jdbc:firebirdsql:localhost/3050:C:/df/database1.fdb";
      String user= "SYSDBA";
      String password= "Greynaud23";*/
      frame= new Frame();
      frame = new Frame();
      frame.setVisible(true);
      frame.setResizable(false);
      conexion=new Conexion();
      Principal principal= new Principal();
      frame.setPanel(principal);
        
    }
    
}


//CONNECT 'C:\Database\DATAB1'  user 'SYSDBA' password 'masterkey';

//GRANT DELETE, INSERT, SELECT, UPDATE, REFERENCES ON hola to username
