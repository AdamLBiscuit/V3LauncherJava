package fr.arinonia.ordinalteam;

import fr.adam.saofrancelauncher.*;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;

public class Main extends JFrame {

    private static Main instance;
    private static SAOFrancePanel panel;
    private static SAOFrancePanelV2 panelV2;
    private static Accueil accueil;
    private static OptionsPanel optionsPanel;


    public Main() {
        this.setTitle("SAOFrance Launcher");
        this.setSize(1280, 720);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAccueil();

        WindowMover move = new WindowMover(this);
        this.addMouseListener(move);
        this.addMouseMotionListener(move);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/fr/adam/saofrancelauncher/ressources/");

        String java = System.getProperty("sun.arch.data.model");


        if (!SAOFranceUtils.SAO_DIR.exists()) {
            SAOFranceUtils.SAO_DIR.mkdir();
        }


        if (!SAOFrancePanel.ramFile.exists()) {
            try {
                SAOFrancePanel.ramFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!SAOFrancePanel.saverFile.exists()) {
            try {
                SAOFrancePanel.saverFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        instance = new Main();

        if (java.equalsIgnoreCase("32")) {
            JOptionPane.showMessageDialog(panel, "Vous avez actuellement une version de java 32 bits, veuillez la désinstaller puis télécharger la version 64 bits !");
            System.exit(0);
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static SAOFrancePanel getPanel() {
        return panel;
    }

    public void setPanel() {
        this.setContentPane(panel = new SAOFrancePanel(this));
    }

    public void setPanelV2() {
        this.setContentPane(panelV2 = new SAOFrancePanelV2(this));
    }

    public void setAccueil() {
        this.setContentPane(accueil = new Accueil(this));
    }

    public void setOptionsPanel(){
    	this.setContentPane(optionsPanel = new OptionsPanel(this));
	}
}
