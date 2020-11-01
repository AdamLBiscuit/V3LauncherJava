package fr.adam.saofrancelauncher.panel;

import fr.arinonia.ordinalteam.Main;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;

public class QuitPanel extends JPanel implements SwingerEventListener {
    private final Image background = Swinger.getResource("news/backgroundQuit.png");
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("News/decoHold.png"), Swinger.getResource("News/decoHold.png"), Swinger.getResource("News/decoHold.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("Connexion/News.png"), Swinger.getResource("News/NewsHold.png"), Swinger.getResource("Connexion/News.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("News/Play.png"), Swinger.getResource("News/PlayHold.png"), Swinger.getResource("News/Play.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("News/Option.png"), Swinger.getResource("News/OptionHold.png"), Swinger.getResource("News/Option.png"));
    private final STexturedButton croix = new STexturedButton(Swinger.getResource("Connexion/croix.png"), Swinger.getResource("Connexion/croix.png"), Swinger.getResource("Connexion/croix.png"));
    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("Connexion/rond.png"), Swinger.getResource("Connexion/rond.png"), Swinger.getResource("Connexion/rond.png"));
    public Main main;

    public QuitPanel(Main main){
        this.main = main;
        setLayout(null);

        playButton.addEventListener(this);
        playButton.setBounds(695, 480, Swinger.getResource("Connexion/rond.png").getWidth(), Swinger.getResource("Connexion/rond.png").getHeight());
        add(playButton);

        croix.addEventListener(this);
        croix.setBounds(935, 480, Swinger.getResource("Connexion/croix.png").getWidth(), Swinger.getResource("Connexion/croix.png").getHeight());
        add(croix);

        homeButton.addEventListener(this);
        homeButton.setBounds(489, 97, Swinger.getResource("News/NewsHold.png").getWidth(), Swinger.getResource("News/NewsHold.png").getHeight());
        add(homeButton);

        playv3.addEventListener(this);
        playv3.setBounds(489, 164, Swinger.getResource("News/Play.png").getWidth(), Swinger.getResource("News/Play.png").getHeight());
        add(playv3);

        optionPane.addEventListener(this);
        optionPane.setBounds(1167, 97, Swinger.getResource("News/Option.png").getWidth(), Swinger.getResource("News/Option.png").getHeight());
        add(optionPane);

        quitButton.addEventListener(this);
        quitButton.setBounds(489, 231, Swinger.getResource("News/deco.png").getWidth(), Swinger.getResource("News/deco.png").getHeight());
        add(quitButton);

    }

    @Override
    public void onEvent(SwingerEvent e) {

        if (e.getSource() == quitButton) {
            this.main.setQuit();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
        if (e.getSource() == homeButton ||e.getSource() == croix) {
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
            this.main.setOption();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }

        if(e.getSource() == playButton){
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);
    }
}
