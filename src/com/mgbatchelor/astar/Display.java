package com.mgbatchelor.astar;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display {

	private DataGrid data;
	private JPanel container;
	private static final int WIDTH = 70;
	private static final int HEIGHT = 30;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Display();
			}
		});
	}

	public Display() {
		packAndDisplay();
		initialize();
		new Thread(new Runnable() {
			public void run() {
				while (true)
					renderGrid();
			};
		}).start();
	}

	private void packAndDisplay() {
		JFrame frame = new JFrame("A* Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = new JPanel();
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setLayout(null);
		frame.setContentPane(container);
		frame.setSize(1400, 670);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	private void initialize() {
		this.data = new DataGrid(WIDTH, HEIGHT);
		addToolbar();
		initializeButtons();
	}

	private void reset() {
		this.data.reset();
	}

	private void randomize() {
		int randoms = 100;
		Random rand = new Random();
		for (int i = 0; i < randoms; i++) {
			data.getPoint(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)).setAsOOB();
		}
	}

	private void addToolbar() {
		final Button resetBtn = new Button("Reset");

		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						reset();
					}
				});
			}
		});

		resetBtn.setSize(100, 36);
		resetBtn.setLocation(10, 8);
		container.add(resetBtn);

		final Button randomizeBtn = new Button("Randomize");

		randomizeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						randomize();
					}
				});
			}
		});

		randomizeBtn.setSize(100, 36);
		randomizeBtn.setLocation(120, 8);
		container.add(randomizeBtn);

		final Button runAstar = new Button("Run A*");

		runAstar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						runAstar();
					}
				});
			}
		});

		runAstar.setSize(100, 36);
		runAstar.setLocation(230, 8);
		container.add(runAstar);
	}

	private void runAstar() {
		Random rand = new Random();
		int startX = rand.nextInt(WIDTH);
		int startY = rand.nextInt(HEIGHT);
		int endX = rand.nextInt(WIDTH);
		int endY = rand.nextInt(HEIGHT);

		Astar astar = new Astar(data, startX, startY, endX, endY);

		new Thread() {
			public void run() {
				Coordinate end = astar.findBestPath().getParent();
				while (end != null) {
					data.getPoint(end.getX(), end.getY()).setAsPath();
					end = end.getParent();
				}
			};
		}.start();
	}

	public void initializeButtons() {
		if (this.data == null || this.container == null)
			return;
		int x = 0;
		int y = 50;
		int size = 20;

		for (int i = 0; i < this.data.getHeight(); i++) {
			for (int j = 0; j < this.data.getWidth(); j++) {
				Container display = this.data.getPoint(j, i).getDisplay();
				if (display == null) {
					display = new JPanel();
					display.setBackground(Color.WHITE);
					this.data.getPoint(j, i).setDisplay(display);
					display.setSize(size, size);
					display.setLocation(x, y);
					this.container.add(display);
				}
				x += size;
			}
			y += size;
			x = 0;
		}

	}

	public void renderGrid() {
		if (this.data == null || this.container == null)
			return;
		for (int i = 0; i < this.data.getHeight(); i++) {
			for (int j = 0; j < this.data.getWidth(); j++) {
				Coordinate coord = this.data.getPoint(j, i);
				Container display = coord.getDisplay();
				String value = coord.getValue();
				if (display == null)
					value = "CLEAR";
				switch (value) {
				case Coordinate.START:
					if (display.getBackground() != Color.GREEN)
						display.setBackground(Color.GREEN);
					break;
				case Coordinate.END:
					if (display.getBackground() != Color.BLUE)
						display.setBackground(Color.BLUE);
					break;
				case Coordinate.OOB:
					if (display.getBackground() != Color.RED)
						display.setBackground(Color.RED);
					break;
				case Coordinate.PATH:
					if (display.getBackground() != Color.BLACK)
						display.setBackground(Color.BLACK);
					break;
				case Coordinate.OPTION:
					if (display.getBackground() != Color.CYAN)
						display.setBackground(Color.CYAN);
					break;
				default:
					if (display != null && display.getBackground() != Color.WHITE)
						display.setBackground(Color.WHITE);
					break;
				}
			}
		}
	}

}
