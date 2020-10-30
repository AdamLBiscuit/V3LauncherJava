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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Accueil extends JPanel implements SwingerEventListener {

    private static final BufferedImage NEWS_PIC = getImage();
    public static File saverFile = new File(SAOFranceUtils.SAO_DIR, "launcher.properties");
    public static Saver saver = new Saver(saverFile);
    public static File ramFile = new File(SAOFranceUtils.SAO_DIR, "ram.txt");
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("settings_button.png"), Swinger.getResource("settings_button_hover.png"), Swinger.getResource("settings_button.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit2.png"), Swinger.getResource("quit.png"), Swinger.getResource("quit2.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("menu.png"), Swinger.getResource("menu2.png"), Swinger.getResource("menu.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("v3normal.png"), Swinger.getResource("v3normal2.png"), Swinger.getResource("v3normal.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));
    private final Image background = Swinger.getResource("backgroundaccueil.jpg");
    private final RamSelector ram = new RamSelector(ramFile);
    private final JTextArea news;
    private final JLabel newsTitle;
    public Main main;
    Image loadingIcon;
    float alpha = 0;


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
        quitButton.setBounds(1230, 660, 51, 51);
        add(quitButton);


        ramButton.addEventListener(this);
        ramButton.setBounds(1175, 660, 55, 55);
        add(ramButton);

        homeButton.addEventListener(this);
        homeButton.setBounds(10, 9, 187, 31);
        add(homeButton);

        playv3.addEventListener(this);
        playv3.setBounds(550, 9, 187, 31);
        add(playv3);

        optionPane.addEventListener(this);
        optionPane.setBounds(1087, 9, 187, 31);
        add(optionPane);

        URL img = getClass().getResource("/fr/adam/saofrancelauncher/ressources/loader.gif");
        this.loadingIcon = new ImageIcon(img).getImage();

    }

    public static BufferedImage getImage() {
        BufferedImage image = null;
        try {
            URL url = new URL("https://launcher.saofrance-mc.net/test.png");
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void enableAll(boolean enable) {
        news.setVisible(enable);
        newsTitle.setVisible(enable);
        quitButton.setVisible(enable);
        ramButton.setVisible(enable);
        homeButton.setVisible(enable);
        playv3.setVisible(enable);
        optionPane.setVisible(enable);
    }

    public void onEvent(SwingerEvent e) {
        if (e.getSource() == quitButton) {
            System.exit(0);
        }

        if (e.getSource() == ramButton) {
            ram.display();
            ram.save();
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

        String url = "https://launcher.saofrance-mc.net/test.png";
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
                g.drawImage(Swinger.getResource("header.png"), 0, 0, 1280, 50, this);

                g2d.drawImage(
                        img,
                        600, 300, 600, 337, this
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
}
