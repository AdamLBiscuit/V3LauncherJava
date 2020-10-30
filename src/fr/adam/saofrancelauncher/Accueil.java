package fr.adam.saofrancelauncher;

import fr.arinonia.ordinalteam.Main;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class Accueil extends JPanel implements SwingerEventListener {

    public static File saverFile = new File(SAOFranceUtils.SAO_DIR, "launcher.properties");
    public static Saver saver = new Saver(saverFile);
    public static File ramFile = new File(SAOFranceUtils.SAO_DIR, "ram.txt");
    private final Image background = Swinger.getResource("backgroundaccueil.jpg");
    private final STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"), Swinger.getResource("discord.png"), Swinger.getResource("discord.png"));
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("settings_button.png"), Swinger.getResource("settings_button_hover.png"), Swinger.getResource("settings_button.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit2.png"), Swinger.getResource("quit.png"), Swinger.getResource("quit2.png"));
    private final STexturedButton menu = new STexturedButton(Swinger.getResource("header.png"), Swinger.getResource("header.png"), Swinger.getResource("header.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("menu.png"), Swinger.getResource("menu2.png"), Swinger.getResource("menu.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("v3normal.png"), Swinger.getResource("v3normal2.png"), Swinger.getResource("v3normal.png"));
    private final STexturedButton imgNews = new STexturedButton(getImage(), getImage(), getImage());
    private final RamSelector ram = new RamSelector(ramFile);
    public Main main;
    private final JTextArea news;
    private final JLabel newsTitle;


    public Accueil(Main main) {
        this.main = main;

        news = new JTextArea(news());

        news.setLineWrap(true);
        news.setWrapStyleWord(false);

        news.setEditable(false);
        news.setFont(new Font("sansserif", Font.BOLD, 22));
        news.setForeground(Color.lightGray);
        news.setBounds(35, 298, 370, 395);
        news.setOpaque(false);

        news.setVisible(true);
        add(news);

        newsTitle = new JLabel(newsTitle());

        newsTitle.setFont(new Font("sansserif", Font.BOLD, 30));
        newsTitle.setForeground(Color.lightGray);
        newsTitle.setBounds(35, 228, 370, 40);
        newsTitle.setAlignmentX(JLabel.CENTER);
        newsTitle.setOpaque(false);
        newsTitle.setHorizontalAlignment(JLabel.CENTER);

        newsTitle.setVisible(true);
        add(newsTitle);

        setLayout(null);

        quitButton.addEventListener(this);
        quitButton.setBounds(1230, 0, 51, 51);
        add(quitButton);


        discordButton.setBounds(1200, 650, 75, 50);
        add(discordButton);
        discordButton.setVisible(false);

        ramButton.addEventListener(this);
        ramButton.setBounds(1175, 0, 55, 55);
        add(ramButton);


        homeButton.addEventListener(this);
        homeButton.setBounds(10, 9, 187, 31);
        add(homeButton);

        imgNews.setBounds(600, 300, 600,337);
        add(imgNews);

        playv3.addEventListener(this);
        playv3.setBounds(550, 9, 187, 31);
        add(playv3);

        menu.setBounds(0, 0, 1280, 50);
        add(menu);

    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == quitButton) {
            System.exit(0);
        }

        if (e.getSource() == ramButton) {
            ram.display();
            ram.save();
        }

        if (e.getSource() == discordButton) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/CPEaz8z"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);

    }

    public RamSelector getProps() {
        return ram;
    }

    private void setFieldEnabled(boolean enabled) {
        quitButton.setEnabled(enabled);
        ramButton.setEnabled(enabled);
    }

    private String news() {
        try {
            URL url = new URL("https://launcher.saofrance-mc.net/news/news.txt");
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                return line;
            }
            return "Impossible de récupérer les news";
        } catch (IOException ex) {
            return "Impossible de récupérer les news";
        }
    }

    private String newsTitle() {


        try {
            URL url = new URL("https://launcher.saofrance-mc.net/news/newsTitle.txt");
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                return line;
            }
            return "Impossible de récupérer les news";
        } catch (IOException ex) {
            return "Impossible de récupérer les news";
        }
    }

    public BufferedImage getImage(){
        BufferedImage image = null;
        try {
            URL url = new URL("https://launcher.saofrance-mc.net/test.png");
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}