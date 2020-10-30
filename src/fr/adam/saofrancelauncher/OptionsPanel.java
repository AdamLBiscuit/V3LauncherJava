package fr.adam.saofrancelauncher;

import fr.arinonia.ordinalteam.Main;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel implements SwingerEventListener {

    private final Image background = Swinger.getResource("optionwall.jpg");
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("menu.png"), Swinger.getResource("menu2.png"), Swinger.getResource("menu.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("v3normal.png"), Swinger.getResource("v3normal2.png"), Swinger.getResource("v3normal.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));

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
            this.main.setPanel();
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
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);
        g.drawImage(Swinger.getResource("header.png"), 0, 0, 1280, 50, this);
    }
}
