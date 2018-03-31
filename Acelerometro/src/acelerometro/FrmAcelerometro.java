/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acelerometro;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author reno1
 */
public class FrmAcelerometro extends javax.swing.JFrame {

    private CommPortIdentifier idPort;
    private SerialPort puertoSerial;
    public OutputStream salida;
    public InputStream entrada;
    private String nombre;
    public int dato, nBytes;
    StringBuilder palabra;
    String salto ;
    int x , contador;
   Timer temporizador = new Timer ( 500 , new ActionListener() 
{
    public void actionPerformed( ActionEvent e)
    {
      try 
        {
            salida.write(1);
        } catch( IOException ex ) {}
        byte[] bufferLectura = new byte[16];
      
        try 
        {
         while( entrada.available() > 0 ) 
        {
            nBytes = entrada.read( bufferLectura );
            String texto=new String(bufferLectura).trim();
            if(texto.substring(0 , 1).equals("x") && texto.indexOf("y") > texto.indexOf("x") && texto.indexOf("z") > texto.indexOf("y") && texto.indexOf("T") > texto.indexOf("z"))
            {
                txtx.setText(texto.substring(1 , texto.indexOf("y")));
                txty.setText(texto.substring(texto.indexOf("y")+1 , texto.indexOf("z")));
                txtz.setText(texto.substring(texto.indexOf("z")+1 , texto.indexOf("T")));
            }
        }
        System.out.print( "*"+ nBytes + "*\n" );
          
        } catch( IOException ex ) {}
        
        
    }
});

    public FrmAcelerometro() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btniniciar = new javax.swing.JButton();
        txtx = new javax.swing.JTextField();
        txty = new javax.swing.JTextField();
        txtz = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnparar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btniniciar.setText("INICIAR");
        btniniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniniciarActionPerformed(evt);
            }
        });

        txtx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtxActionPerformed(evt);
            }
        });

        txty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtyActionPerformed(evt);
            }
        });

        jLabel1.setText("x");

        jLabel2.setText("y");

        jLabel3.setText("z");

        btnparar.setText("parar");
        btnparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpararActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btniniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtz)
                    .addComponent(txty)
                    .addComponent(txtx))
                .addGap(41, 41, 41)
                .addComponent(btnparar)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btniniciar)
                    .addComponent(btnparar))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtyActionPerformed

    private void btniniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciarActionPerformed
try{
            nombre= "COM4";//Este es el nombre del puerto del arduino debe ser cambiado seg?n corresponda
            idPort = CommPortIdentifier.getPortIdentifier(nombre);
            puertoSerial=(SerialPort) idPort.open("Comunicacion Serial", 2000);
            entrada = puertoSerial.getInputStream();
            salida=puertoSerial.getOutputStream();
            System.out.println("Puerto " + nombre + " iniciado ...");
     
            try {//los valores que se encuentran a continuaci?n son los par?metros de la comunicaci?n serial, deben ser los mismos en el arduino
                puertoSerial.setSerialPortParams( 9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE );
            } catch( UnsupportedCommOperationException e ) {}
    
        } catch (Exception e) {
            System.out.println("Error en iniciarPuerto() \n"+e);
    }      
temporizador.start();
 // TODO add your handling code here:
    }//GEN-LAST:event_btniniciarActionPerformed

    private void btnpararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpararActionPerformed
    temporizador.stop();    // TODO add your handling code here:
    }//GEN-LAST:event_btnpararActionPerformed

    private void txtxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAcelerometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAcelerometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAcelerometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAcelerometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAcelerometro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btniniciar;
    private javax.swing.JButton btnparar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtx;
    private javax.swing.JTextField txty;
    private javax.swing.JTextField txtz;
    // End of variables declaration//GEN-END:variables
}
