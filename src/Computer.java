package src;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Computer extends JFrame {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	
	private final MatrixComputer computer;
	
	public Computer() {
		super("Matrix");
		this.computer = new MatrixComputer();
		
		this.setResizable(false);
		this.setContentPane(this.computer);
		this.pack();
		this.setLocation(0, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	
}
