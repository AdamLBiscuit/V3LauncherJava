package fr.arinonia.ordinalteam;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.adam.saofrancelauncher.SAOFrancePanel;
import fr.adam.saofrancelauncher.SAOFranceUtils;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

public class Main extends JFrame {

	private static Main instance;
	private static SAOFrancePanel panel;
	
	public Main() {
		this.setTitle("SAOFrance Launcher");
		this.setSize(1280 ,720);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(panel = new SAOFrancePanel());
		
		WindowMover move = new WindowMover(this);
        this.addMouseListener(move);
        this.addMouseMotionListener(move);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/adam/saofrancelauncher/ressources/");
		
		String java = System.getProperty("sun.arch.data.model");
		
		
		if(!SAOFranceUtils.SAO_DIR.exists()) {
			SAOFranceUtils.SAO_DIR.mkdir();
		}
		
		if(!getPanel().ramFile.exists()) {
			try {
				getPanel().ramFile.createNewFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!getPanel().saverFile.exists()) {
			try {
				getPanel().saverFile.createNewFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		instance = new Main();
		
		if(java.equalsIgnoreCase("32")) {
			JOptionPane.showMessageDialog(panel, "Vous avez actuellement une version de java 32 bits, veuillez la désinstaller puis télécharger la version 64 bits !");
			System.exit(0);
		}
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static SAOFrancePanel getPanel() {
		return panel;
	}
}
