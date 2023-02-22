/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorbasedatos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.firebirdsql.management.FBUser;
import org.firebirdsql.management.FBUserManager;
import org.firebirdsql.management.User;
import org.firebirdsql.management.UserManager;

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
           
        }

       

    }
        public void ConectarInicio(String url) {
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
        public void desconectar() {
        try {
            this.url ="";
            con.close();
            
            JOptionPane.showMessageDialog(null, "DESCONECTADO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

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
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
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
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
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
              JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
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
              JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
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

    public void AgregarColumnaTabla(String name, NodoTabla columns) {
        try {
            Date d= new Date();
            
            con.close();
            Conectar(url);
            String query = "ALTER TABLE " + name + "ADD " + columns.nombrecol + " " + columns.tipoDato.toUpperCase();
            if (columns.tipoDato.equals("Varchar")) {
                query += "(" + columns.size + ") DEFAULT 'null'";
            } else if (columns.tipoDato.equals("Date")) {
                query += " DEFAULT "+d.getDate();
            } else {
                query += " DEFAULT 0";
            }
            query += " ;";

            Statement st;
            st = con.createStatement();

            st.execute(query);
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void cambiarColumnaNombre(String tname, String cname, String name2) {
        try {
            con.close();
            Conectar(url);
            String query = "ALTER TABLE " + tname + "ALTER " + cname + " TO " + name2 + " ;";

            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void BorrarColumna(String tname, String cname) {
        try {
            con.close();
            Conectar(url);
            String query = "ALTER TABLE " + tname + " DROP " + cname + " ;";

            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

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
              JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
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
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
            //  JOptionPane.showMessageDialog(null, "ERROR AL INSERTAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getprimarykey(String name) {
        String result = "";
        try {
            String Query = "select ix.rdb$index_name as index_name, sg.rdb$field_name as field_name, rc.rdb$relation_name as table_name from rdb$indices ix left join rdb$index_segments sg on ix.rdb$index_name = sg.rdb$index_name left join rdb$relation_constraints rc on rc.rdb$index_name = ix.rdb$index_name where rc.rdb$constraint_type = 'PRIMARY KEY' AND   rc.rdb$relation_name= '" + name + "';";

            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(Query);
            while (rs.next()) {
                rs.getNString(1);
                result = rs.getNString(2);
                rs.getNString(3);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public void modificarInfoTable(String name, String pk, String data) {
        try {
            String query = "UPDATE " + name + " SET " + data + "WHERE " + pk + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void BorrarInfoTable(String name, String data) {
        try {
            String query = "DELETE FROM " + name + "WHERE " + data + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN COMPONENTE TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String TableDDL(String name) {
        ArrayList<String> type = types(name);
        ArrayList<String> nameC = TablesColumnandType(name);
        String query = "CREATE TABLE " + name.trim() + "(\u2002 \n";

        for (int x = 0; x < type.size(); x++) {
            if (type.get(x).equals("INTEGER")) {
                query += "\u2002" + nameC.get(x).trim() + "\u2002 INT ";
            } else {
                query += "\u2002" + nameC.get(x).trim() + "\u2002" + type.get(x);
            }

            if (x != type.size() - 1) {
                query += " ,\n";
            }
        }
        query += "\n);";
        return query;
    }

    public ArrayList<String> ListarIndices() {
        Statement st;
        ArrayList<String> indices = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT RDB$INDEX_NAME FROM RDB$INDICES WHERE RDB$SYSTEM_FLAG = 0;");

            while (rs.next()) {
                indices.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return indices;
    }

    public void CrearIndice(String query) {
        try {
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ModificarIndice(String indexn, String estado) {
        try {
            String query = "ALTER INDEX " + indexn + " " + estado + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void BorrarIndice(String indexn) {
        try {
            String query = "DROP INDEX " + indexn + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getColumnaIndice(String tabla, String indexn) {
        String Columna = "";
        try {
            String query = "SELECT RDB$INDEX_SEGMENTS.RDB$FIELD_NAME AS field_name, (RDB$INDEX_SEGMENTS.RDB$FIELD_POSITION + 1) AS field_position FROM RDB$INDEX_SEGMENTS LEFT JOIN RDB$INDICES ON RDB$INDICES.RDB$INDEX_NAME = RDB$INDEX_SEGMENTS.RDB$INDEX_NAME LEFT JOIN RDB$RELATION_CONSTRAINTS ON RDB$RELATION_CONSTRAINTS.RDB$INDEX_NAME = RDB$INDEX_SEGMENTS.RDB$INDEX_NAME WHERE UPPER(RDB$INDICES.RDB$RELATION_NAME)='" + tabla + "'   AND UPPER(RDB$INDICES.RDB$INDEX_NAME)='" + indexn + "' AND RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_TYPE IS NULL ORDER BY RDB$INDEX_SEGMENTS.RDB$FIELD_POSITION;";
            Statement st;

            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Columna = rs.getNString(1);
                rs.getInt(2);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return Columna;
    }

    public ArrayList<String> InformacionIndice(String indicename) {
        ArrayList<String> info = new ArrayList<String>();
        try {

            String query = "select RDB$RELATION_NAME ,RDB$INDEX_INACTIVE, RDB$INDEX_TYPE,RDB$UNIQUE_FLAG from RDB$INDICES where RDB$SYSTEM_FLAG = 0 and RDB$INDEX_NAME='" + indicename + "';";
            Statement st;

            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                info.add(rs.getNString(1));
                rs.getInt(2);
                if (rs.wasNull()) {
                    info.add("-1");
                } else {
                    info.add(String.valueOf(rs.getInt(2)));
                }
                rs.getInt(3);
                if (rs.wasNull()) {
                    info.add("-1");
                } else {
                    info.add(String.valueOf(rs.getInt(3)));
                }
                rs.getInt(4);
                if (rs.wasNull()) {
                    info.add("-1");
                } else {
                    info.add(String.valueOf(rs.getInt(4)));
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN INDICES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return info;
    }

    public ArrayList<String> ListarProcedimientos() {
        Statement st;
        ArrayList<String> procedimientos = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$PROCEDURE_NAME from RDB$PROCEDURES where RDB$SYSTEM_FLAG = 0;");

            while (rs.next()) {
                procedimientos.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN PROCEDIMIENTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return procedimientos;
    }

    public void CrearProcedimiento(String nombreP, String parametrod, String returns, String declare, String sentence) {
        try {
            String query = " CREATE PROCEDURE " + nombreP;
            if (!parametrod.equals("")) {
                query += " ( " + parametrod + " )";
            }
            if (!returns.equals("")) {
                query += " RETURNS ( " + returns + " )";

            }

            query += " AS";

            if (!declare.equals("")) {
                query += " " + declare;
            }

            query += " BEGIN " + sentence;

            if (!returns.equals("")) {
                query += " SUSPEND ;";
            }
            query += " END ;";
            Statement st;
            st = con.createStatement();

            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN PROCEDIMIENTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ModificarProcedimiento(String nombreP, String parametrod, String returns, String declare, String sentence) {
        try {
            String query = " ALTER PROCEDURE " + nombreP;
            if (!parametrod.equals("")) {
                query += " ( " + parametrod + " )";
            }
            if (!returns.equals("")) {
                query += " RETURNS ( " + returns + " )";

            }

            query += " AS";

            if (!declare.equals("")) {
                query += " " + declare;
            }

            query += " BEGIN " + sentence;

            if (!returns.equals("")) {
                query += " SUSPEND ;";
            }
            query += " END ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN PROCEDIMIENTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String Procedimientosddl(String nameP) {
        String source = "";
        try {
            String query = " select  RDB$PROCEDURE_SOURCE  from RDB$PROCEDURES where RDB$SYSTEM_FLAG = 0 AND RDB$PROCEDURE_NAME='" + nameP.trim() + "' ;";
            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                InputStream in = rs.getBlob(1).getBinaryStream();
                int n = 0;
                try {
                    while ((n = in.read(buf)) >= 0) {

                        baos.write(buf, 0, n);

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }

                byte[] bytes = baos.toByteArray();

                String blobString = new String(bytes);
                source = blobString;

            }

        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN PROCEDIMIENTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return source;
    }

    public void BorrarProcedimiento(String namep) {
        try {
            String name = namep.trim();
            String query = "DROP PROCEDURE " + name + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "ERROR EN PROCEDIMIENTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> ListarFunciones() {
        Statement st;
        ArrayList<String> funciones = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT RDB$FUNCTION_NAME FROM RDB$FUNCTIONS WHERE RDB$SYSTEM_FLAG=0;");

            while (rs.next()) {
                funciones.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return funciones;
    }

    public void CrearFunciones(String nombreF, String parametrod, String returns, String declare, String sentence) {
        try {
            String query = " CREATE FUNCTION " + nombreF;
            if (!parametrod.equals("")) {
                query += " ( " + parametrod + " )";
            }
            if (!returns.equals("")) {
                query += " RETURNS " + returns + "";

            }

            query += " AS";

            if (!declare.equals("")) {
                query += " " + declare;
            }

            query += " BEGIN " + sentence;

            query += " END ;";
            Statement st;
            st = con.createStatement();

            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ModificarFunciones(String nombreF, String parametrod, String returns, String declare, String sentence) {
        try {
            String query = " ALTER FUNCTION " + nombreF;
            if (!parametrod.equals("")) {
                query += " ( " + parametrod + " )";
            }
            if (!returns.equals("")) {
                query += " RETURNS " + returns + "";

            }

            query += " AS";

            if (!declare.equals("")) {
                query += " " + declare;
            }

            query += " BEGIN " + sentence;

            query += " END ;";
            Statement st;
            st = con.createStatement();

            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void BorrarFuncion(String namep) {
        try {
            String name = namep.trim();
            String query = "DROP FUNCTION " + name + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String Funcionesddl(String nameP) {
        String source = "";
        try {
            String query = " select  RDB$FUNCTION_SOURCE  from RDB$FUNCTIONS where RDB$SYSTEM_FLAG = 0 AND RDB$FUNCTION_NAME='" + nameP.trim() + "' ;";
            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                InputStream in = rs.getBlob(1).getBinaryStream();
                int n = 0;
                try {
                    while ((n = in.read(buf)) >= 0) {

                        baos.write(buf, 0, n);

                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    in.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR EN FUNCIONES", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                byte[] bytes = baos.toByteArray();

                String blobString = new String(bytes);
                source = blobString;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return source;
    }

    public ArrayList<String> ListarTriggers() {
        Statement st;
        ArrayList<String> funciones = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$TRIGGER_NAME from RDB$TRIGGERS where RDB$SYSTEM_FLAG = 0;");

            while (rs.next()) {
                funciones.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return funciones;
    }

    public void CrearModificarTrigger(String query) {

        try {
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> Triggersddl(String namet) {
        ArrayList<String> data = new ArrayList<String>();
        try {
            String name = namet.trim();
            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$RELATION_NAME, RDB$TRIGGER_TYPE,RDB$TRIGGER_SOURCE,RDB$TRIGGER_INACTIVE from RDB$TRIGGERS where RDB$SYSTEM_FLAG = 0 AND RDB$TRIGGER_NAME= '" + name + "' ;");
            while (rs.next()) {
                data.add(rs.getString(1));
                int type = rs.getInt(2);
                if (type == 1) {
                    data.add(" BEFORE INSERT");
                } else if (type == 2) {
                    data.add(" AFTER INSERT");
                } else if (type == 3) {
                    data.add(" BEFORE UPDATE");
                } else if (type == 4) {
                    data.add(" AFTER UPDATE");
                } else if (type == 5) {
                    data.add(" BEFORE DELETE");
                } else if (type == 6) {
                    data.add(" AFTER DELETE");
                } else if (type == 17) {
                    data.add(" BEFORE INSERT OR UPDATE");
                } else if (type == 18) {
                    data.add(" AFTER INSERT OR UPDATE");
                } else if (type == 25) {
                    data.add(" BEFORE INSERT OR DELETE");
                } else if (type == 26) {
                    data.add(" AFTER INSERT OR DELETE");
                } else if (type == 27) {
                    data.add(" BEFORE UPDATE OR DELETE");
                } else if (type == 28) {
                    data.add(" AFTER UPDATE OR DELETE");
                } else if (type == 113) {
                    data.add(" BEFORE INSERT OR UPDATE OR DELETE");
                } else if (type == 114) {
                    data.add(" AFTER INSERT OR UPDATE OR DELETE");
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                InputStream in = rs.getBlob(3).getBinaryStream();
                int n = 0;
                try {
                    while ((n = in.read(buf)) >= 0) {

                        baos.write(buf, 0, n);

                    }
                } catch (IOException ex) {
                  
                }
                try {
                    in.close();
                } catch (IOException ex) {
                     JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                byte[] bytes = baos.toByteArray();

                String blobString = new String(bytes);
                data.add(blobString);

                int estado = rs.getInt(4);
                if (estado == 0) {
                    data.add(" ACTIVE");
                } else if (estado == 1) {
                    data.add(" INACTIVE");
                }

            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    public void BorrarTrigger(String namep) {
        try {
            String name = namep.trim();
            String query = "DROP TRIGGER " + name + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ModificarEstadoTrigger(String namep, String estado) {
        try {
            String name = namep.trim();
            String query = "ALTER TRIGGER " + name + " " + estado + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN TRIGGER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> ListarViews() {
        Statement st;
        ArrayList<String> views = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$RELATION_NAME from RDB$RELATIONS where RDB$RELATION_TYPE = 1 and RDB$SYSTEM_FLAG = 0;");

            while (rs.next()) {
                views.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return views;
    }

    public void CrearModificarView(String query) {

        try {
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String Viewsddl(String nameP) {
        String source = "";
        try {
            String query = " select RDB$VIEW_SOURCE from RDB$RELATIONS where RDB$RELATION_TYPE = 1 and RDB$SYSTEM_FLAG = 0 and RDB$RELATION_NAME = '" + nameP + " ' ;";
            Statement st;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                InputStream in = rs.getBlob(1).getBinaryStream();
                int n = 0;
                try {
                    while ((n = in.read(buf)) >= 0) {

                        baos.write(buf, 0, n);

                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    in.close();
                } catch (IOException ex) {
                     JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                byte[] bytes = baos.toByteArray();

                String blobString = new String(bytes);
                source = blobString;

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return source;
    }

    public void BorrarView(String namep) {
        try {
            String name = namep.trim();
            String query = "DROP VIEW " + name + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN VIEW", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void crearChecks(String name, String table, String condicion) {
        try {
            con.close();
            Conectar(url);
            String query = "ALTER TABLE " + table + " ADD CONSTRAINT " + name + " CHECK ( " + condicion + " ) ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN CHECKS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> ListarChecks() {
        Statement st;
        ArrayList<String> checks = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$CONSTRAINT_NAME from rdb$relation_constraints WHERE RDB$CONSTRAINT_TYPE='CHECK';");

            while (rs.next()) {
                checks.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "ERROR EN CHECKS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return checks;
    }

    public String getTablaChecks(String constraint) {
        Statement st;
        String table = "";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select RDB$RELATION_NAME from rdb$relation_constraints WHERE RDB$CONSTRAINT_TYPE='CHECK' AND RDB$CONSTRAINT_NAME= '" + constraint + "' ;");

            while (rs.next()) {
                table = rs.getNString(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN CHECKS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return table;
    }

    public void BorrarChecks(String table, String Constraint) {
        try {
            con.close();
            Conectar(url);

            String query = "ALTER TABLE " + table + " DROP CONSTRAINT " + Constraint + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN CHECKS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> ListarUsuarios() {
        Statement st;
        ArrayList<String> users = new ArrayList<String>();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT RDB$USER FROM RDB$USER_PRIVILEGES group by 1;");

            while (rs.next()) {
                users.add(rs.getNString(1));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR EN USUARIO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return users;
    }

    public void crearModificarUsuario(String query) {
        try {

            Statement st;
            st = con.createStatement();
            st.execute(query);
            con.close();
            Conectar(url);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN USUARIO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void BorrarUser(String namep) {
        try {

            String name = namep.trim();
            String query = "DROP USER " + name + " ;";
            Statement st;
            st = con.createStatement();
            st.execute(query);
            con.close();
            Conectar(url);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN USUARIO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }



}
