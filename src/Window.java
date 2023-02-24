package src;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private final Analyser analyser;
	
	public Window() {
		super("Determinant Comparison");
		analyser = new Analyser();
		
		this.setResizable(false);
		this.setContentPane(analyser);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	
}
