package main.UI;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.db.EntryManager;
/**
 *
 * @author Nicholai
 */
public class ConfigGui extends javax.swing.JFrame {
    
    private TrayIcon trayIcon;
    private EntryManager em;
    
    public ConfigGui() {
        initTray();
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        motionLabel1 = new org.free.Mitchko.MotionLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        motionLabel1.setBackground(new java.awt.Color(255, 255, 255));
        motionLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/UI/Icons/loading.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motionLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motionLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.free.Mitchko.MotionLabel motionLabel1;
    // End of variables declaration//GEN-END:variables
    // <editor-fold defaultstate="collapsed" desc="System Tray Code">   
    private void initTray() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        PopupMenu popup = new PopupMenu();
        try {
            trayIcon
                    = new TrayIcon(ImageIO.read(ConfigGui.class.getResourceAsStream("Icons/icon-16.png")), "tray icon");
        } catch (IOException ex) {
            Logger.getLogger(ConfigGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        SystemTray tray = SystemTray.getSystemTray();
        MenuItem open = new MenuItem("Show");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        };
        open.addActionListener(listener);
        popup.add(open);
        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (java.awt.AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
    // </editor-fold>
    
    public void killTray(){
        SystemTray.getSystemTray().remove(trayIcon);
    }
    
    public void setEntryManager(EntryManager e){
        this.em = e;
    }
}
