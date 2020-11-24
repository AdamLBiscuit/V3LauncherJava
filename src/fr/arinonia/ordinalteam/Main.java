package fr.arinonia.ordinalteam;

import fr.adam.saofrancelauncher.panel.PlayPanel;
import fr.adam.saofrancelauncher.panel.AccueilPanel;
import fr.adam.saofrancelauncher.panel.OptionsPanel;
import fr.adam.saofrancelauncher.panel.QuitPanel;
import fr.adam.saofrancelauncher.utils.SAOFranceUtils;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.animation.QueryLoopAction;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends JFrame {

    private static Main instance;
    private static PlayPanel panel;
    private static AccueilPanel accueilPanel;
    private static OptionsPanel optionsPanel;
    private static QuitPanel quitPanel;
    public static File checkbox = new File(SAOFranceUtils.SAO_DIR, "checkbox.txt");


    public Main() {
        this.setTitle("SAOFrance Launcher");
        this.setSize(1280, 720);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setOption();
        setQuit();
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


        if (!PlayPanel.ramFile.exists()) {
            try {
                PlayPanel.ramFile.createNewFile();
                FileWriter w = new FileWriter(new File(SAOFranceUtils.SAO_DIR, "ram.txt"));
                w.write("2");
                w.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!checkbox.exists()){
            try {
                checkbox.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!PlayPanel.saverFile.exists()) {
            try {
                PlayPanel.saverFile.createNewFile();
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

    public static PlayPanel getPlayPanel() {
        return panel;
    }

    public static AccueilPanel getAccueilPanel() {
        return accueilPanel;
    }

    public static OptionsPanel getOptionsPanel() {
        return optionsPanel;
    }

    public static QuitPanel getQuitPanel() { return quitPanel;}

    public void setPlay() {
        this.setContentPane(panel = new PlayPanel(this));
    }

    public void setAccueil() {
        this.setContentPane(accueilPanel = new AccueilPanel(this));
    }

    public void setQuit(){
        this.setContentPane(quitPanel = new QuitPanel(this));
    }

    public void setOption(){
        this.setContentPane(optionsPanel = new OptionsPanel(this));
    }

}
