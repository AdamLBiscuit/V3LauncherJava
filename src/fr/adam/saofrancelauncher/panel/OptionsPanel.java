package fr.adam.saofrancelauncher.panel;

import com.sun.management.OperatingSystemMXBean;
import fr.arinonia.ordinalteam.Main;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.*;
import java.lang.management.ManagementFactory;

import static fr.adam.saofrancelauncher.utils.SAOFranceUtils.*;

public class OptionsPanel extends JPanel implements SwingerEventListener {

    private final Image background = Swinger.getResource("news/backgroundOption.png");
    private final STexturedButton path = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));
    private final STexturedButton clearLite = new STexturedButton(Swinger.getResource("options.png"), Swinger.getResource("options2.png"), Swinger.getResource("options.png"));

    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("News/deco.png"), Swinger.getResource("News/decoHold.png"), Swinger.getResource("News/deco.png"));
    private final STexturedButton homeButton = new STexturedButton(Swinger.getResource("Connexion/News.png"), Swinger.getResource("News/NewsHold.png"), Swinger.getResource("Connexion/News.png"));
    private final STexturedButton playv3 = new STexturedButton(Swinger.getResource("News/Play.png"), Swinger.getResource("News/PlayHold.png"), Swinger.getResource("News/Play.png"));
    private final STexturedButton optionPane = new STexturedButton(Swinger.getResource("News/OptionHold.png"), Swinger.getResource("News/OptionHold.png"), Swinger.getResource("News/OptionHold.png"));
    private final SColoredBar progressBar = new SColoredBar(new Color(255, 0, 0, 85));
    private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 9, 2);

    private JLabel label = new JLabel("Ram : " + getRam() + "Go");

    public Main main;

    public OptionsPanel(Main main) {
        this.main = main;
        setLayout(null);


        slider.setValue(Integer.valueOf(getRam()));
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setBounds(700, 325, 300, 20);
        slider.addChangeListener((ChangeEvent event) -> {
            System.out.println(slider.getValue());
            setRam(String.valueOf(slider.getValue()));
            label.setText("Ram : " + slider.getValue() + "Go");

        });
        add(slider);

        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(Color.GRAY);
        label.setBounds(562, 240, 580, 50);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

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

        if (e.getSource() == quitButton) {
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
            };
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Swinger.drawFullsizedImage(g, this, background);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public static String getRam() {
        int ram = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(SAO_DIR, "ram.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                ram = Integer.parseInt(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = Integer.toString(ram);
        return str;
    }

    public static void setRam(String val) {
        File ramfile = new File(SAO_DIR, "ram.txt");
        if (!ramfile.exists()) {
            try {
                ramfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter rama = new FileWriter(new File(SAO_DIR, "ram.txt"));
            rama.write(val);
            rama.close();
        } catch (Exception localException) {}
    }


}

