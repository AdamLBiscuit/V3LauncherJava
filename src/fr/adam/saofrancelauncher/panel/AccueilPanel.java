package fr.adam.saofrancelauncher.panel;

import fr.adam.saofrancelauncher.utils.ImageCache;
import fr.adam.saofrancelauncher.utils.SAOFranceUtils;
import fr.arinonia.ordinalteam.Main;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AccueilPanel extends JPanel implements SwingerEventListener {

    public static File ramFile = new File(SAOFranceUtils.SAO_DIR, "ram.txt");
    //private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("News/Option.png"), Swinger.getResource("News/Option.png"), Swinger.getResource("News/Option.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("News/deco.png"), Swinger.getResource("News/decoHold.png"), Swinger.getResource("News/deco.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("News/NewsHold.png"), Swinger.getResource("News/NewsHold.png"), Swinger.getResource("News/NewsHold.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("News/Play.png"), Swinger.getResource("News/PlayHold.png"), Swinger.getResource("News/Play.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("News/Option.png"), Swinger.getResource("News/OptionHold.png"), Swinger.getResource("News/Option.png"));
    private final Image background = Swinger.getResource("News/wallpaper.png");
    //private final Image background = Swinger.getResource("Nouveautes.png");
    private final RamSelector ram = new RamSelector(ramFile);
    private final JTextArea news;
    private final JLabel newsTitle;
    private final SColoredBar progressBar = new SColoredBar(new Color(255, 0, 0, 85));
    public Main main;
    Image loadingIcon;
    float alpha = 0;


    public AccueilPanel(Main main) {

        this.main = main;

        news = new JTextArea(news());

        news.setLineWrap(true);
        news.setWrapStyleWord(false);

        news.setEditable(false);
        news.setFont(new Font("sansserif", Font.BOLD, 18));
        news.setForeground(Color.DARK_GRAY);
        news.setBounds(872, 320, 200, 300);
        news.setOpaque(false);

        news.setVisible(true);
        add(news);

        newsTitle = new JLabel(newsTitle());

        newsTitle.setFont(new Font("sansserif", Font.BOLD, 25));
        newsTitle.setForeground(Color.DARK_GRAY);
        newsTitle.setBounds(872, 278, 200, 40);
        newsTitle.setOpaque(false);

        newsTitle.setVisible(true);
        add(newsTitle);

        setLayout(null);

        /*ramButton.addEventListener(this);
        ramButton.setBounds(1167, 97, Swinger.getResource("News/Option.png").getWidth(), Swinger.getResource("News/Option.png").getHeight());
        add(ramButton);*/

        quitButton.addEventListener(this);
        quitButton.setBounds(489, 231, Swinger.getResource("News/deco.png").getWidth(), Swinger.getResource("News/deco.png").getHeight());
        add(quitButton);

        homeButton.addEventListener(this);
        homeButton.setBounds(489, 97, Swinger.getResource("News/NewsHold.png").getWidth(), Swinger.getResource("News/NewsHold.png").getHeight());
        add(homeButton);

        playv3.addEventListener(this);
        playv3.setBounds(489, 164, Swinger.getResource("News/Play.png").getWidth(), Swinger.getResource("News/Play.png").getHeight());
        add(playv3);

        optionPane.addEventListener(this);
        optionPane.setBounds(1167, 97, Swinger.getResource("News/Option.png").getWidth(), Swinger.getResource("News/Option.png").getHeight());
        add(optionPane);

        URL img = getClass().getResource("/fr/adam/saofrancelauncher/ressources/loader.gif");
        this.loadingIcon = new ImageIcon(img).getImage();

        progressBar.setBounds(0, 705, 1280, 15);
        add(progressBar);
        progressBar.setVisible(false);

    }

    public void setBarVisible(boolean enable) {
        progressBar.setVisible(enable);
    }

    public void enableAll(boolean enable) {
        news.setVisible(enable);
        newsTitle.setVisible(enable);
        quitButton.setVisible(enable);
        //ramButton.setVisible(enable);
        homeButton.setVisible(enable);
        playv3.setVisible(enable);
        optionPane.setVisible(enable);
    }

    public void onEvent(SwingerEvent e) {
        if (e.getSource() == quitButton) {
            System.exit(0);
        }

        //if (e.getSource() == ramButton) {
        //    ram.display();
        //    ram.save();
        //}

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
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);

        String url = "https://launcher.saofrance-mc.net/test.jpg";
        BufferedImage img = ImageCache.getImage(url);

        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            if (img != ImageCache.DEFAULT_IMAGE) {

                long fadeTime = 1000;
                long downloadedTime = ImageCache.getDownloadedTime(url);
                long fadeDate = downloadedTime + fadeTime;

                double percent = Math.max(0, Math.min(100, ((downloadedTime - System.currentTimeMillis()) * 100 / (downloadedTime - fadeDate))));

                float alpha = (float) (percent * 0.01);//draw half transparent
                this.alpha = alpha;

                Composite oldComposite = g2d.getComposite();
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                //g.drawImage(Swinger.getResource("header.png"), 0, 0, 1280, 50, this);
                //g.drawImage(Swinger.getResource("News/TITRE.png"), 872, 279, Swinger.getResource("News/TITRE.png").getWidth(), Swinger.getResource("News/TITRE.png").getHeight(), this);
                g.drawImage(Swinger.getResource("News/Rectangle1.png"), 562, 91, Swinger.getResource("News/Rectangle1.png").getWidth(), Swinger.getResource("News/Rectangle1.png").getHeight(), this);
                g.drawImage(Swinger.getResource("News/Rectangle1-1.png"), 562, 241, Swinger.getResource("News/Rectangle1-1.png").getWidth(), Swinger.getResource("News/Rectangle1-1.png").getHeight(), this);
                g.drawImage(Swinger.getResource("News/Nouveautes.png"), 747, 136, Swinger.getResource("News/Nouveautes.png").getWidth(), Swinger.getResource("News/Nouveautes.png").getHeight(), this);
                g.drawImage(Swinger.getResource("News/theme_logo.png"), 50, 31, Swinger.getResource("News/theme_logo.png").getWidth(), Swinger.getResource("News/theme_logo.png").getHeight(), this);
                g.drawImage(Swinger.getResource("Connexion/Rectangle3.png"), 541, 102, Swinger.getResource("Connexion/Rectangle3.png").getWidth(), Swinger.getResource("Connexion/Rectangle3.png").getHeight(), this);


                g2d.drawImage(
                        img,
                        582, 280, 283, 159, this
                );

                g2d.setComposite(oldComposite);
                g.setColor(new Color(0, 0, 0, (float) (1 - (percent * 0.01))));
                g.fillRect(0, 0, getWidth(), getHeight());

                g2d.setComposite(ac);

            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            Composite oldComposite = g2d.getComposite();
            AlphaComposite nC = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - this.alpha);
            g2d.setComposite(nC);

            g.drawImage(this.loadingIcon, getWidth() / 2 - 180, getHeight() / 2 - 100, 180 * 2, 200, this);
            g2d.setComposite(oldComposite);
        }
        enableAll(img != ImageCache.DEFAULT_IMAGE);

        this.repaint();
    }

    private String news() {
        try {
            URL url = new URL("https://launcher.saofrance-mc.net/news/news.txt");
            Scanner scanner = new Scanner(url.openStream());
            return scanner.nextLine();

        } catch (IOException ex) {
            return "Impossible de récupérer les news";
        }
    }

    private String newsTitle() {

        try {
            URL url = new URL("https://launcher.saofrance-mc.net/news/newsTitle.txt");
            Scanner scanner = new Scanner(url.openStream());

            return scanner.nextLine();

        } catch (IOException ex) {
            return "Impossible de récupérer les news";
        }
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }
}
