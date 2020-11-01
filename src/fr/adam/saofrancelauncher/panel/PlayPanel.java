package fr.adam.saofrancelauncher.panel;

import fr.adam.saofrancelauncher.utils.SAOFranceUtils;
import fr.arinonia.ordinalteam.Main;
import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;

public class PlayPanel extends JPanel implements SwingerEventListener {

    public static File saverFile = new File(SAOFranceUtils.SAO_DIR, "launcher.properties");
    public static Saver saver = new Saver(saverFile);
    public static File ramFile = new File(SAOFranceUtils.SAO_DIR, "ram.txt");
    //private final Image background = Swinger.getResource("connexion.png");
    private final Image background = Swinger.getResource("Connexion/backgroundLogin.png");
    private final JTextField usernameField = new JTextField(saver.get("username"));
    private final JPasswordField passwordField = new JPasswordField(saver.get("password"));
    private final STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"), Swinger.getResource("discord.png"), Swinger.getResource("discord.png"));
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("settings_button.png"), Swinger.getResource("settings_button_hover.png"), Swinger.getResource("settings_button.png"));
    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("Connexion/rond.png"), Swinger.getResource("Connexion/rond.png"), Swinger.getResource("Connexion/rond.png"));
    private final STexturedButton playButton2 = new STexturedButton(Swinger.getResource("jouer1lite.png"), Swinger.getResource("jouer2lite.png"), Swinger.getResource("jouer1lite.png"));

    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("News/deco.png"), Swinger.getResource("News/decoHold.png"), Swinger.getResource("News/deco.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("Connexion/News.png"), Swinger.getResource("News/NewsHold.png"), Swinger.getResource("Connexion/News.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("News/PlayHold.png"), Swinger.getResource("News/PlayHold.png"), Swinger.getResource("News/PlayHold.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("News/Option.png"), Swinger.getResource("News/OptionHold.png"), Swinger.getResource("News/Option.png"));
    private final STexturedButton croix = new STexturedButton(Swinger.getResource("Connexion/croix.png"), Swinger.getResource("Connexion/croix.png"), Swinger.getResource("Connexion/croix.png"));

    private final SColoredBar progressBar = new SColoredBar(new Color(255, 0, 0, 85));
    private final RamSelector ram = new RamSelector(ramFile);
    private final JLabel status = new JLabel("En attente d'une action");
    public Main main;


    public PlayPanel(Main main) {
        this.main = main;

        setLayout(null);

        usernameField.setFont(new Font("sansserif", Font.BOLD, 18));
        usernameField.setForeground(new Color(105, 105, 105));
        usernameField.setOpaque(false);
        usernameField.setBounds(808, 323, Swinger.getResource("Connexion/Rectangle2.png").getWidth() - 10, Swinger.getResource("Connexion/Rectangle2.png").getHeight());
        usernameField.setBorder(null);
        add(usernameField);

        passwordField.setFont(new Font("sansserif", Font.BOLD, 18));
        passwordField.setForeground(new Color(105, 105, 105));
        passwordField.setOpaque(false);
        passwordField.setBounds(808, 396, Swinger.getResource("Connexion/Rectangle2.png").getWidth() - 10, Swinger.getResource("Connexion/Rectangle2.png").getHeight());
        passwordField.setBorder(null);
        add(passwordField);

        discordButton.setBounds(1200, 650, 75, 50);
        add(discordButton);
        discordButton.setVisible(false);

        ramButton.addEventListener(this);
        ramButton.setBounds(1175, 660, 55, 55);
        add(ramButton);

        playButton2.addEventListener(this);
        playButton2.setBounds(240, 540, 187, 31);
        add(playButton2);

        progressBar.setBounds(0, 705, 1280, 15);
        add(progressBar);
        progressBar.setVisible(true);

        status.setFont(new Font("sansserif", Font.BOLD, 22));
        status.setForeground(Color.GRAY);
        status.setBounds(562, 200, 580, 50);
        status.setHorizontalAlignment(JLabel.CENTER);


        add(status);
        status.setVisible(true);

        playButton.addEventListener(this);
        playButton.setBounds(695, 560, Swinger.getResource("Connexion/rond.png").getWidth(), Swinger.getResource("Connexion/rond.png").getHeight());
        add(playButton);

        croix.addEventListener(this);
        croix.setBounds(935, 560, Swinger.getResource("Connexion/croix.png").getWidth(), Swinger.getResource("Connexion/croix.png").getHeight());
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
    public void setBarVisible(boolean enable) {
        progressBar.setVisible(enable);
    }

    @Override
    public void onEvent(SwingerEvent e) {

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

        if (e.getSource() == playButton) {
            playButton.setEnabled(false);
            Thread thread = new Thread() {
                public void run() {
                    setFieldEnabled(false);
                    try {
                        SAOFranceUtils.auth(usernameField.getText(), passwordField.getText());
                    } catch (AuthenticationException e2) {
                        e2.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant l'auth à mojang", "Erreur", 0, null);
                        playButton.setEnabled(true);
                        return;
                    }

                    if(BarAPI.getNumberOfFileToDownload() !=0) {
                        setStatus("download");
                    } else {
                        status.setText("Vérifications des fichers en cours...");
                    }
                    ram.save();
                    saver.set("username", usernameField.getText());
                    saver.set("password", passwordField.getText());

                    try {
                        SAOFranceUtils.update();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant l'update", "Erreur", 0, null);
                        return;
                    }

                    try {
                        SAOFranceUtils.launch(usernameField.getText());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant le lancement du jeu", "Erreur", 0, null);
                        return;
                    }
                }

            };
            thread.start();
        }

        if (e.getSource() == playButton2) {
            playButton.setEnabled(false);
            playButton2.setEnabled(false);
            Thread thread = new Thread() {
                public void run() {
                    setFieldEnabled(false);
                    try {
                        SAOFranceUtils.auth(usernameField.getText(), passwordField.getText());
                    } catch (AuthenticationException e2) {
                        e2.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant l'auth à mojang", "Erreur", 0, null);
                        playButton.setEnabled(true);
                        return;
                    }

                    status.setText("Vérifications des fichers en cours...");
                    ram.save();
                    saver.set("username", usernameField.getText());
                    saver.set("password", passwordField.getText());

                    try {
                        SAOFranceUtils.update2();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant l'update", "Erreur", 0, null);
                        return;
                    }

                    try {
                        SAOFranceUtils.launch2(usernameField.getText());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        setFieldEnabled(true);
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue durant le lancement du jeu", "Erreur", 0, null);
                        return;
                    }
                }

            };
            thread.start();
        }

        if (e.getSource() == quitButton ||e.getSource() == croix) {
            this.main.setQuit();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
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
            this.main.setOption();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);
        //g.drawImage(Swinger.getResource("header.png"), 0, 0, 1280, 50, this);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public RamSelector getProps() {
        return ram;
    }

    private void setFieldEnabled(boolean enabled) {
        passwordField.setEnabled(enabled);
        usernameField.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }

    public void setStatus(String p) {
        status.setText(p);
    }
}
