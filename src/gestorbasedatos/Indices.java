/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorbasedatos;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Gabriela Diaz R
 */
public class Indices extends javax.swing.JPanel {

    /**
     * Creates new form Indices
     */
    public Indices() {
        initComponents();
    }
    int indicet=0;
    int indicei=0;
    String tablename="";
    String indexname="";
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Indices");
        add(jLabel1);
        jLabel1.setBounds(80, 40, 130, 29);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestorbasedatos/Firebird_logo.png"))); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(20, 20, 50, 60);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Listar Indices");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(30, 90, 140, 40);

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 140, 140, 140);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Listar Tablas");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(210, 90, 140, 40);

        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        add(jScrollPane2);
        jScrollPane2.setBounds(210, 140, 140, 140);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Crear Indice");
        add(jLabel3);
        jLabel3.setBounds(410, 100, 100, 14);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Columna:");
        add(jLabel4);
        jLabel4.setBounds(410, 180, 90, 20);

        add(jComboBox1);
        jComboBox1.setBounds(410, 200, 240, 20);

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Unique");
        add(jCheckBox1);
        jCheckBox1.setBounds(490, 250, 100, 23);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Tipo:");
        add(jLabel5);
        jLabel5.setBounds(410, 230, 27, 14);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ASC", "DESC" }));
        add(jComboBox2);
        jComboBox2.setBounds(410, 250, 70, 20);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestorbasedatos/check.png"))); // NOI18N
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(610, 240, 40, 39);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombre:");
        add(jLabel6);
        jLabel6.setBounds(410, 130, 70, 14);
        add(jTextField1);
        jTextField1.setBounds(410, 150, 240, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Modificar Indice");
        add(jLabel7);
        jLabel7.setBounds(410, 350, 150, 30);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ACTIVE", "INACTIVE" }));
        add(jComboBox3);
        jComboBox3.setBounds(410, 410, 240, 20);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Estado:");
        add(jLabel8);
        jLabel8.setBounds(410, 390, 100, 14);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestorbasedatos/check.png"))); // NOI18N
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(410, 440, 40, 39);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ELIMINAR INDICE");
        add(jLabel9);
        jLabel9.setBounds(410, 560, 150, 17);

        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("Selecionar indice de la lista");
        add(jLabel10);
        jLabel10.setBounds(410, 590, 180, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestorbasedatos/trash.png"))); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(410, 620, 60, 60);

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("MOSTRAR DDL");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(40, 520, 290, 40);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        add(jScrollPane3);
        jScrollPane3.setBounds(40, 570, 290, 180);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        add(jScrollPane4);
        jScrollPane4.setBounds(40, 330, 290, 170);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Informacion");
        add(jLabel11);
        jLabel11.setBounds(40, 300, 80, 14);

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("BACK");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(540, 730, 110, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       DefaultListModel listModel = new DefaultListModel();

        ArrayList<String> tmp = new ArrayList<String>();
        tmp = GestorBaseDatos.conexion.ListarIndices();
        int x = 0;
        while (x < tmp.size()) {
            listModel.addElement(tmp.get(x));
            x++;
        }

        jList1.setModel(listModel);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        DefaultListModel listModel = new DefaultListModel();

        ArrayList<String> tmp = new ArrayList<String>();
        tmp = GestorBaseDatos.conexion.ListasTablas();
        int x = 0;
        while (x < tmp.size()) {
            listModel.addElement(tmp.get(x));
            x++;
        }

        jList2.setModel(listModel);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        indicei = 0;
        indexname = "";
     
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
              
            indicei = list.locationToIndex(evt.getPoint());
            indexname= jList1.getModel().getElementAt(indicei).toString();
            ArrayList<String> infogeneral= GestorBaseDatos.conexion.InformacionIndice(indexname);
            String columanreferencia= GestorBaseDatos.conexion.getColumnaIndice(infogeneral.get(0), indexname);
            String estado="";
            String tipo="";
            if(infogeneral.get(1).equals("0")){
                estado="ACTIVO";
            }
            else if(infogeneral.get(1).equals("1")){
                estado="INACTIVO";
            }
            else{
                estado= "NO DEFINIDO";
            }
            
            if(infogeneral.get(2).equals("0")){
                tipo="ASC";
            }
            else if(infogeneral.get(2).equals("1")){
                tipo="DESC";
            }
            else{
                tipo= "NO DEFINIDO";
            }
            
            String informacion="Nombre="+indexname+"\n Tabla= "+infogeneral.get(0)+"\n Columna= "+columanreferencia+"\n Estado= "+estado+"\n Tipo= "+tipo;
            
            jTextArea2.setText("");
            jTextArea2.setText(informacion);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
         indicet = 0;
         tablename = "";
     
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
               jComboBox1.removeAllItems();
             
            indicet = list.locationToIndex(evt.getPoint());
            tablename = jList2.getModel().getElementAt(indicet).toString();
             ArrayList<String> nameColumns= GestorBaseDatos.conexion.TablesColumnandType(tablename);
             for(int x=0; x< nameColumns.size();x++){
                 
                jComboBox1.addItem(nameColumns.get(x));
                
             }

        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
       String columna= jComboBox1.getSelectedItem().toString();
       String tipo =jComboBox2.getSelectedItem().toString();
       String nombreindex= jTextField1.getText();

       String query= "CREATE";
       if(jCheckBox1.isSelected()){
         query+=" UNIQUE";
       }
       query+=" "+tipo+" INDEX "+nombreindex+" ON "+tablename+" ( "+columna+" );";
       GestorBaseDatos.conexion.CrearIndice(query);
       jTextField1.setText("");
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
       String estado= jComboBox3.getSelectedItem().toString();
       GestorBaseDatos.conexion.ModificarIndice(indexname, estado);
       
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        GestorBaseDatos.conexion.BorrarIndice(indexname);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
         ArrayList<String> infogeneral= GestorBaseDatos.conexion.InformacionIndice(indexname);
            String columnareferencia= GestorBaseDatos.conexion.getColumnaIndice(infogeneral.get(0), indexname);
            String estado="";
            String tipo="";
            String Unico="";
          
           
            if(infogeneral.get(2).equals("0")){
                tipo="ASC";
            }
            else if(infogeneral.get(2).equals("1")){
                tipo="DESC";
            }
            else{
                tipo= "";
            }
            
            if(infogeneral.get(3).equals("1")){
                Unico="UNIQUE";
            }
            else{
                Unico= "";
            }
            
            String ddl="CREATE \u2002"+Unico.trim()+"\u2002 "+tipo.trim()+"\u2002 INDEX \u2002 "+indexname.trim()+"\n ON \u2002"+infogeneral.get(0).trim()+"(\u2002 "+columnareferencia.trim()+" );";
            jTextArea1.setText("");
            jTextArea1.setText(ddl);
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
       Principal p= new Principal();
       GestorBaseDatos.frame.setPanel(p);
    }//GEN-LAST:event_jButton7MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
