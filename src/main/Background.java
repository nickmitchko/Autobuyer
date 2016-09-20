package main;

import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.UI.ConfigGui;
import main.db.EntryManager;
import org.json.JSONException;

/**
 *
 * @author Nicholai
 */
public class Background {

    private static boolean UITEST = false;
    private static ConfigGui GUI;
    private static Driver main;
    private static EntryManager em;
    
    public Background() throws Exception{
        // 1. Start System Tray
        em = new EntryManager();
        makeSystemTray();
        // 2. Load Driver & Database
        if(!UITEST){
            try {
                main = new Driver("config.ini", em);
                main.start();
            } catch (IOException | JSONException iOException) {
                iOException.printStackTrace();
                GUI.killTray();
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            UITEST = Boolean.parseBoolean(args[0]);
        } catch (Exception e){
            
        }
        try {
            new Background();
        } catch (Exception ex) {
            Logger.getLogger(Background.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Also loads the Frame which contains the system tray
    private void makeSystemTray(){
        //<editor-fold defaultstate="collapsed" desc="System Look">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                    break;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI = new ConfigGui();
                GUI.setEntryManager(em);
                ConfigGui.setDefaultLookAndFeelDecorated(true);
                if(GUI.isUndecorated()){
                    com.sun.awt.AWTUtilities.setWindowShape(GUI, new RoundRectangle2D.Double(0.0, 0.0, (double) GUI.getWidth(), (double)GUI.getHeight(), 10, 10));
                }
                GUI.setVisible(UITEST);
            }
        });
    }
}
