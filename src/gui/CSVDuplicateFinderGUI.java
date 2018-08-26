package gui;

import java.awt.Dimension;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class CSVDuplicateFinderGUI extends JFrame {
	
	private final static String FRAME_TITLE = "CSV Duplicate Finder";
	
	public static void main(String[] args) {
		new CSVDuplicateFinderGUI();
	}
	
	CSVDuplicateFinderGUI() {
		setupFrame();
		initGui();
		showFrame();
	}
	
	private void initGui() {
		// TODO add stuff
	}
	
	private void setupFrame() {
		setTitle(FRAME_TITLE);
		setJMenuBar(getJMenuBar());
		setLocation(100, 100);
		setPreferredSize(new Dimension(800, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void showFrame() {
		pack();
		setVisible(true);
	}

}
