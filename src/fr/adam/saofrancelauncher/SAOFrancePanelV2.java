package fr.adam.saofrancelauncher;

import fr.arinonia.ordinalteam.Main;
import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;

public class SAOFrancePanelV2 extends JPanel implements SwingerEventListener {

    private final Image background = Swinger.getResource("back.png");

    public static File saverFile = new File(SAOFranceUtils.SAO_DIR, "launcher.properties");
    public static Saver saver = new Saver(saverFile);
    public static File ramFile = new File(SAOFranceUtils.SAO_DIR, "ram.txt");
    private final JTextField usernameField = new JTextField(saver.get("username"));
    private final JPasswordField passwordField = new JPasswordField(saver.get("password"));
    private final STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"), Swinger.getResource("discord.png"), Swinger.getResource("discord.png"));
    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("settings_button.png"), Swinger.getResource("settings_button_hover.png"), Swinger.getResource("settings_button.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit2.png"), Swinger.getResource("quit.png"), Swinger.getResource("quit2.png"));
    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("jouer1.png"), Swinger.getResource("jouer2.png"), Swinger.getResource("jouer1.png"));
    private final STexturedButton playButton2 = new STexturedButton(Swinger.getResource("jouer1lite.png"), Swinger.getResource("jouer2lite.png"), Swinger.getResource("jouer1lite.png"));
    private final STexturedButton playButton3 = new STexturedButton(Swinger.getResource("jouer1lite.png"), Swinger.getResource("jouer2lite.png"), Swinger.getResource("jouer1lite.png"));
    private final SColoredBar progressBar = new SColoredBar(new Color(0, 60, 180, 255));
    private final RamSelector ram = new RamSelector(ramFile);
    private final JLabel status = new JLabel("En attente d'une action");
    public Main main;


    public SAOFrancePanelV2(Main main) {
        this.main = main;

        setLayout(null);
        usernameField.setFont(new Font("sansserif", Font.BOLD, 18));
        usernameField.setForeground(new Color(105, 105, 105));
        usernameField.setOpaque(false);
        usernameField.setBounds(226, 344, 228, 42);
        usernameField.setBorder(null);
        add(usernameField);

        passwordField.setFont(new Font("sansserif", Font.BOLD, 14));
        passwordField.setForeground(new Color(105, 105, 105));
        passwordField.setOpaque(false);
        passwordField.setBounds(226, 422, 228, 42);
        passwordField.setBorder(null);
        add(passwordField);


        discordButton.setBounds(1200, 650, 75, 50);
        add(discordButton);
        discordButton.setVisible(false);

        ramButton.addEventListener(this);
        ramButton.setBounds(1175, 0, 55, 55);
        add(ramButton);

        quitButton.addEventListener(this);
        quitButton.setBounds(1230, 0, 51, 51);
        add(quitButton);

        playButton.addEventListener(this);
        playButton.setBounds(210, 490, 249, 42);
        add(playButton);

        playButton2.addEventListener(this);
        playButton2.setBounds(240, 540, 187, 31);
        add(playButton2);

        playButton3.addEventListener(this);
        playButton3.setBounds(270, 250, 187, 31);
        add(playButton3);

        progressBar.setBounds(0, 705, 1280, 15);
        add(progressBar);
        progressBar.setVisible(true);


        status.setFont(new Font("sansserif", Font.BOLD, 22));
        status.setForeground(Color.GRAY);
        status.setBounds(0, 650, 1280, 50);
        status.setHorizontalAlignment(JLabel.CENTER);

        add(status);
        status.setVisible(true);

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

        if (e.getSource() == playButton3) {
            this.main.setPanel();
            this.main.invalidate();
            this.main.validate();
            this.main.repaint();
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

                    status.setText("Vérifications des fichers en cours...");
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Swinger.drawFullsizedImage(g, this, background);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public RamSelector getProps() {
        return ram;
    }

    private void setFieldEnabled(boolean enabled) {
        quitButton.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        usernameField.setEnabled(enabled);
        ramButton.setEnabled(enabled);
    }

    public void setStatus(String p) {
        status.setText(p);
    }


}
