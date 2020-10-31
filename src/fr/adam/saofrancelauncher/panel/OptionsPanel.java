package fr.adam.saofrancelauncher.panel;

import fr.arinonia.ordinalteam.Main;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static fr.adam.saofrancelauncher.utils.SAOFranceUtils.SAO_INFOS;
import static fr.adam.saofrancelauncher.utils.SAOFranceUtils.SAO_INFOS2;

public class OptionsPanel extends JPanel implements SwingerEventListener {

    private final Image background = Swinger.getResource("optionwall.jpg");
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("menu.png"), Swinger.getResource("menu2.png"), Swinger.getResource("menu.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("v3normal.png"), Swinger.getResource("v3normal2.png"), Swinger.getResource("v3normal.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));
    private final STexturedButton path = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));
    private final STexturedButton clearLite = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));
    private final SColoredBar progressBar = new SColoredBar(new Color(255, 0, 0, 85));

    public Main main;

    public OptionsPanel(Main main) {
        this.main = main;
        setLayout(null);
        homeButton.addEventListener(this);
        homeButton.setBounds(10, 9, 187, 31);
        add(homeButton);
        homeButton.setVisible(true);


        playv3.addEventListener(this);
        playv3.setBounds(550, 9, 187, 31);
        add(playv3);
        playv3.setVisible(true);

        optionPane.addEventListener(this);
        optionPane.setBounds(1087, 9, 187, 31);
        add(optionPane);
        optionPane.setVisible(true);

        path.addEventListener(this);
        path.setBounds(500, 500, 187, 31);
        add(path);
        path.setVisible(true);

        clearLite.addEventListener(this);
        clearLite.setBounds(500, 600, 187, 31);
        add(clearLite);
        clearLite.setVisible(true);

        progressBar.setBounds(0, 705, 1280, 15);
        add(progressBar);
        progressBar.setVisible(false);

    }

    public void setBarVisible(boolean enable) {
        progressBar.setVisible(enable);
    }

    @Override
    public void onEvent(SwingerEvent e) {

        if (e.getSource() == homeButton) {
            this.main.setAccueil();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
        if (e.getSource() == playv3) {
            this.main.setPlay();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
        if (e.getSource() == optionPane) {
            this.main.setOptionsPanel();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
        if (e.getSource() == path) {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + SAO_INFOS.getGameDir().getAbsolutePath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == clearLite) {
            File file = new File(SAO_INFOS2.getGameDir().getAbsolutePath());
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            ;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);
        g.drawImage(Swinger.getResource("header.png"), 0, 0, 1280, 50, this);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }
}
