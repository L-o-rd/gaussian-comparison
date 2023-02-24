package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Analyser extends JPanel {
	
	public static final int NSAMPLES = 7;
	public static final int YSAMPLES = 8;
	public static final int XSAMPLES[] = {0, 5, 6, 7, 8, 9, 10, 11};
	
	public static final int X_UNIT = (Window.WIDTH - 160) / NSAMPLES;
	public static final int Y_UNIT = 70;
	
	private JButton button;
	private float samples[], gsamples[];
	
	public Analyser()  {
		super();
		this.setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		this.setBackground(Color.WHITE);
		this.setBorder(null);
		
		this.samples = new float[NSAMPLES];
		this.gsamples = new float[NSAMPLES];
		Arrays.fill(this.samples, -1.f);
		
		this.button = new JButton("Resample");
		this.button.addActionListener(event -> sample());
		this.add(this.button);
	}
	
	public void sample() {
		long start, end;
		for(int i = 0; i < NSAMPLES; ++i) {
			final int n = XSAMPLES[i + 1];
			final float[] matrix = Matrix.create(n);
			
			start = System.nanoTime();
			Matrix.RecursiveDeterminant(matrix, n);
			end = System.nanoTime();
			this.samples[i] = (end - start) / 1000000.f;
			
			start = System.nanoTime();
			Matrix.GaussJordanEliminationDeterminant(matrix, n);
			end = System.nanoTime();
			this.gsamples[i] = (end - start) / 1000000.f;
		}
		
		this.repaint();
	}
	
	private static final float SLOPE = 1.43f;
	private static final float INTERCEPT = -1.42f;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawString("Recursive Algorithm", Window.WIDTH - 250, 53);
		g.drawString("Gauss-Jordan Algorithm", Window.WIDTH - 250, 73);
		
		g.setColor(Color.RED);
		g.drawLine(Window.WIDTH - 80, 50, Window.WIDTH - 25, 50);
		
		g.setColor(Color.BLUE);
		g.drawLine(Window.WIDTH - 80, 70, Window.WIDTH - 25, 70);
		
		g.setColor(Color.BLACK);
		g.drawString("Time taken (ms)", 65, 40);
		
		g.drawLine(50, 10, 45, 20);
		g.drawLine(50, 15, 50, Window.HEIGHT - 10); 									/* Y AXIS */
		g.drawLine(50, 10, 55, 20);
		
		for(int i = 1; i <= YSAMPLES; ++i) {
			final int ycoord = Window.HEIGHT - 50 - i * Y_UNIT;
			g.drawLine(40, ycoord, 60, ycoord);
			g.drawString(String.format("%.2f ms", SLOPE * i + INTERCEPT), 65, ycoord + 5);
		}
		
		g.drawString("Matrix size (n x n)", Window.WIDTH - 150, Window.HEIGHT - 65);
		
		g.drawLine(Window.WIDTH - 10, Window.HEIGHT - 50, Window.WIDTH - 17, Window.HEIGHT - 57);
		g.drawLine(10, Window.HEIGHT - 50, Window.WIDTH - 15, Window.HEIGHT - 50); 					/* X AXIS */
		g.drawLine(Window.WIDTH - 10, Window.HEIGHT - 50, Window.WIDTH - 17, Window.HEIGHT - 43);
		
		for(int i = 1; i <= NSAMPLES; ++i) {
			g.drawLine(50 + i * X_UNIT, Window.HEIGHT - 60, 50 + i * X_UNIT, Window.HEIGHT - 40);
			g.drawString(String.format("n = %d", XSAMPLES[i]), i * X_UNIT + 47, Window.HEIGHT - 25);
		}
		
		if(this.samples != null && this.samples[0] > 0.f) {
			Graphics2D g2D = (Graphics2D) g;
			
			Path2D.Float recursive = new Path2D.Float();
			Path2D.Float gaussian = new Path2D.Float();
			
			recursive.moveTo(50.f + 0.65f * X_UNIT, Window.HEIGHT - 115.f);
			recursive.curveTo(50.f + 0.65f * X_UNIT, Window.HEIGHT - 115.f, 50.f + 0.75f * X_UNIT, Window.HEIGHT - 115.f, 50.f + X_UNIT, Window.HEIGHT - 50.f - ((-INTERCEPT + this.samples[0]) / SLOPE) * Y_UNIT);
			
			gaussian.moveTo(50.f + 0.65f * X_UNIT, Window.HEIGHT - 115.f);
			gaussian.curveTo(50.f + 0.65f * X_UNIT, Window.HEIGHT - 115.f, 50.f + 0.75f * X_UNIT, Window.HEIGHT - 115.f, 50.f + X_UNIT, Window.HEIGHT - 50.f - ((-INTERCEPT + this.gsamples[0]) / SLOPE) * Y_UNIT);
			
			for(int i = 0; i < NSAMPLES - 1; ++i) {
				final float ycoord = Window.HEIGHT - 50.f - ((-INTERCEPT + this.samples[i]) / SLOPE) * Y_UNIT;
				final float nexty = Window.HEIGHT - 50.f - ((-INTERCEPT + this.samples[i + 1]) / SLOPE) * Y_UNIT;
				recursive.moveTo(50.f + (i + 1) * X_UNIT, ycoord);
				recursive.curveTo(50.f + (i + 1.f) * X_UNIT, ycoord, 50.f + (i + 1.5f) * X_UNIT, ycoord, 50.f + (i + 2.f) * X_UNIT, nexty);
				
				final float gycoord = Window.HEIGHT - 50.f - ((-INTERCEPT + this.gsamples[i]) / SLOPE) * Y_UNIT;
				final float gnexty = Window.HEIGHT - 50.f - ((-INTERCEPT + this.gsamples[i + 1]) / SLOPE) * Y_UNIT;
				
				gaussian.moveTo(50.f + (i + 1) * X_UNIT, gycoord);
				gaussian.curveTo(50.f + (i + 1.f) * X_UNIT, gycoord, 50.f + (i + 1.5f) * X_UNIT, gycoord, 50.f + (i + 2.f) * X_UNIT, gnexty);
			}
			
			g2D.setColor(Color.RED);
			g2D.draw(recursive);
			
			g2D.setColor(Color.BLUE);
			g2D.draw(gaussian);
		}
	}
	
}
