/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.firebirdsql.gds.impl.GDSType;
import org.firebirdsql.management.FBManager;
import org.firebirdsql.management.FBManagerMBean;

/**
 *
 * @author Gabriela Diaz R
 */
public class Conexion {

    Connection con;
    int columnas = 0;
    String url = "";

    public Conexion() {

    }
    /*  
     */

    public void Conectar(String url) {
        this.url = url;
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        String user = "SYSDBA";
        String password = "Greynaud23";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "CONECTADO", "EXITO", JOptionPane.INFORMATION_MESSAGE);

    }

    public void doo() {
        Statement st;
        try {
            st = con.createStatement();
            st.execute("create table cities( id int);");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String DateTimeConverter(Timestamp ts) {
        Date date = new Date();
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return formattedDate;

    }

    public void CrearConexion(String nombre) {
        FBManager manager = new FBManager();
        try {

            manager.start();

            manager.createDatabase("C:/df/" + nombre + ".fdb", "SYSDBA", "Greynaud23");
            manager.stop();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CREAR CONEXION", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "CONEXION CREADA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void BorrarConexion(String url) {
        FBManager manager = new FBManager();
        try {

            manager.start();

            manager.dropDatabase(url, "SYSDBA", "Greynaud23");

            manager.stop();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR CONEXION", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "CONEXION ELIMINADA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
    }

    public ArrayList<String> ListasTablas() {
        Statement st;
        ArrayList<String> tables = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$RELATION_NAME from RDB$RELATIONS where RDB$RELATION_TYPE = 0 and RDB$SYSTEM_FLAG = 0;");

            while (rs.next()) {
                tables.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tables;
    }

    public ArrayList<String> TablesColumnandType(String name) {
        columnas = 0;
        Statement st;
        ArrayList<String> tables = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(" select rdb$field_name FROM RDB$RELATION_FIELDS where rdb$relation_name='" + name + "';");
            while (rs.next()) {

                tables.add(rs.getNString(1));

                columnas += 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tables;
    }

    public ArrayList<String> TablesData(String name) {
        Statement st;
        ArrayList<String> tables = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from " + name);
            while (rs.next()) {

                for (int x = 1; x <= columnas; x++) {
                    int type = rs.getMetaData().getColumnType(x);
                    System.out.println(type);
                    if (type == 4) {
                        tables.add(String.valueOf(rs.getInt(x)));
                    } else if (type == 6) {
                        tables.add(String.valueOf(rs.getFloat(x)));
                    } else if (type == 91) {
                        tables.add(DateTimeConverter(rs.getTimestamp(x)));
                    } else if (type == 12) {
                        tables.add(rs.getNString(x));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tables;
    }

    public void BorrarTabla(String name, String url) {
        Statement st;

        try {

            con.close();
            Conectar(url);
            st = con.createStatement();
            st.executeUpdate("DROP TABLE " + name);

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String crearTablaquery(String name, ArrayList<NodoTabla> columns) {
        String query = "CREATE TABLE " + name + " (";
        for (int x = 0; x < columns.size(); x++) {
            query += " " + columns.get(x).nombrecol + " " + columns.get(x).tipoDato.toUpperCase();

            if (columns.get(x).tipoDato.equals("Varchar")) {
                query += "(" + columns.get(x).size + ")";
            }
            if (!columns.get(x).notnul.equals("")) {
                query += " " + columns.get(x).notnul;
            }
            if (!columns.get(x).pk.equals("")) {
                query += " " + columns.get(x).pk;
            }
            if (x != columns.size() - 1) {
                query += " ,";
            }
        }
        query += " );";

        return query;

    }

    public ArrayList<String> types(String name) {
        ArrayList<String> type = new ArrayList<String>();
        try {

            Statement st;
            st = con.createStatement();
            String query = "SELECT * FROM " + name;
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsm = rs.getMetaData();
            for (int x = 1; x <= columnas; x++) {
                type.add(rsm.getColumnTypeName(x));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return type;
    }

    public void createTable(String query) {
        try {
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CREAR TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "TABLA CREADA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void insertInfoTable(String data, String name) {
        try {
            String Query = "INSERT INTO " + name + " VALUES( " + data + " );";
            Statement st;
            st = con.createStatement();
            st.execute(Query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            //  JOptionPane.showMessageDialog(null, "ERROR AL INSERTAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getprimarykey(String name) {
        String result="";
        try {
            String Query = "select ix.rdb$index_name as index_name, sg.rdb$field_name as field_name, rc.rdb$relation_name as table_name from rdb$indices ix left join rdb$index_segments sg on ix.rdb$index_name = sg.rdb$index_name left join rdb$relation_constraints rc on rc.rdb$index_name = ix.rdb$index_name where rc.rdb$constraint_type = 'PRIMARY KEY' AND   rc.rdb$relation_name= '"+name+"';";
           
            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(Query);
             while (rs.next()) {
             rs.getNString(1);
             result = rs.getNString(2);
             rs.getNString(3);
             }
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
         return result;
    }
    
    
    public void modificarInfoTable(String name, String pk,String data){
        try {
            String query="UPDATE "+name+" SET "+data+"WHERE "+pk+" ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void BorrarInfoTable(String name,String data){
        try {
            String query="DELETE FROM "+name+"WHERE "+data+" ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
