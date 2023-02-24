package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MatrixComputer extends JPanel {
	
	private final JButton sizes[], compute, gcompute, clear, random;
	private final JTextField input[], output;
	private final JPanel panel;
	
	private int length = 8;
	private float determinant = 0.f;
	
	public MatrixComputer() {
		super();
		this.setPreferredSize(new Dimension(Computer.WIDTH, (int)(Computer.HEIGHT * 0.55f)));
		this.requestFocus();
		this.setBackground(Color.WHITE);
		this.setBorder(null);
		
		this.sizes = new JButton[8];
		for(int i = 0; i < 8; ++i) this.sizes[i] = new JButton(String.format("%d x %d", i + 1, i + 1));
		
		for(final JButton button : this.sizes) {
			this.add(button);
			button.addActionListener(event -> pressed(event));
		}
		
		this.panel = new JPanel();
		this.panel.setBackground(Color.WHITE);
		this.panel.setPreferredSize(new Dimension((int)(0.85f * Computer.WIDTH), (int)(0.75f * Computer.HEIGHT)));
		this.add(this.panel);
		
		this.input = new JTextField[64];
		for(int i = 0; i < 64; ++i) this.input[i] = new JTextField(5);
		
		for(final JTextField field : this.input) {
			this.panel.add(field);
			field.setFont(field.getFont().deriveFont((float)16.f));
		}
		
		this.output = new JTextField("Determinant: ", 30);
		this.output.setBorder(null);
		this.output.setEditable(false);
		this.output.setBackground(Color.WHITE);
		this.output.setFont(this.output.getFont().deriveFont((float)20.f));
		this.panel.add(this.output);
		
		this.compute = new JButton("Compute Recursive");
		this.compute.addActionListener(event -> computeRecursive(event));
		this.panel.add(this.compute);
		
		this.gcompute = new JButton("Compute Gauss - Jordan");
		this.gcompute.addActionListener(event -> computeGaussJordan(event));
		this.panel.add(this.gcompute);
		
		this.clear = new JButton("Clear");
		this.clear.addActionListener(event -> clear());
		this.panel.add(this.clear);
		
		this.random = new JButton("Random");
		this.random.addActionListener(event -> randomize());
		this.panel.add(this.random);
	}
	
	private void randomize() {
		for(final JTextField field : this.input) {
			field.setText(String.format("%.2f", Math.random() * Matrix.MAX_VALUE));
		}
	}
	
	private void clear() {
		for(final JTextField field : this.input) {
			field.setText("");
		}
	}
	
	private void computeGaussJordan(ActionEvent event) {
		final int half = this.length >> 1;
		float matrix[] = new float[this.length * this.length];
		switch(this.length & 1) {
			case 1: {
				for(int i = 3 - half, k = 0; i <= 3 + half; ++i) {
					for(int j = 3 - half; j <= 3 + half; ++j) {
						String txt = this.input[j + (i << 3)].getText();
						try {
							matrix[k++] = Float.parseFloat(txt);
						} catch(Exception e) {
							matrix[k - 1] = 0.f;
						}
					}
				}
				break;
			}
			case 0: {
				for(int i = 4 - half, k = 0; i < 4 + half; ++i) {
					for(int j = 4 - half; j < 4 + half; ++j) {
						String txt = this.input[j + (i << 3)].getText();
						try {
							matrix[k++] = Float.parseFloat(txt);
						} catch(Exception e) {
							matrix[k - 1] = 0.f;
						}
					}
				}
				break;
			}
		}
		
		this.determinant = Matrix.GaussJordanEliminationDeterminant(matrix, this.length);
		this.output.setText(String.format("Determinant: %-5.2f", this.determinant));
		
		matrix = Matrix.GaussJordanElimination(matrix, this.length);
		
		switch(this.length & 1) {
			case 1: {
				for(int i = 3 - half, k = 0; i <= 3 + half; ++i) {
					for(int j = 3 - half; j <= 3 + half; ++j) {
						this.input[j + (i << 3)].setText(String.format("%.2f", matrix[k++]));
					}
				}
				break;
			}
			case 0: {
				for(int i = 4 - half, k = 0; i < 4 + half; ++i) {
					for(int j = 4 - half; j < 4 + half; ++j) {
						this.input[j + (i << 3)].setText(String.format("%.2f", matrix[k++]));
					}
				}
				break;
			}
		}
	}
	
	private void computeRecursive(ActionEvent event) {
		final int half = this.length >> 1;
		final float matrix[] = new float[this.length * this.length];
		
		switch(this.length & 1) {
			case 1: {
				for(int i = 3 - half, k = 0; i <= 3 + half; ++i) {
					for(int j = 3 - half; j <= 3 + half; ++j) {
						String txt = this.input[j + (i << 3)].getText();
						try {
							matrix[k++] = Float.parseFloat(txt);
						} catch(Exception e) {
							matrix[k - 1] = 0.f;
						}
					}
				}
				break;
			}
			case 0: {
				for(int i = 4 - half, k = 0; i < 4 + half; ++i) {
					for(int j = 4 - half; j < 4 + half; ++j) {
						String txt = this.input[j + (i << 3)].getText();
						try {
							matrix[k++] = Float.parseFloat(txt);
						} catch(Exception e) {
							matrix[k - 1] = 0.f;
						}
					}
				}
				break;
			}
		}
		
		this.determinant = Matrix.RecursiveDeterminant(matrix, this.length);
		this.output.setText(String.format("Determinant: %-5.2f", this.determinant));
	}
	
	private void pressed(ActionEvent event) {
		this.length = (((JButton) event.getSource()).getText().charAt(0) - '0');
		final int half = this.length >> 1;
		switch(this.length & 1) {
			case 1: {
				for(int i = 0; i < 8; ++i) {
					for(int j = 0; j < 8; ++j) {
						if(i < 3 - half || i > 3 + half || j < 3 - half || j > 3 + half) {
							this.input[j + (i << 3)].setVisible(false);
							continue;
						}
						
						this.input[j + (i << 3)].setVisible(true);
					}
				}
				return;
			}
			case 0: {
				for(int i = 0; i < 8; ++i) {
					for(int j = 0; j < 8; ++j) {
						if(i < 4 - half || i >= 4 + half || j < 4 - half || j >= 4 + half) {
							this.input[j + (i << 3)].setVisible(false);
							continue;
						}
						
						this.input[j + (i << 3)].setVisible(true);
					}
				}
				return;
			}
		}
	}
	
}
