package com.mgbatchelor.astar;

public class DataGrid {

	private int width;
	private int height;

	private Coordinate[][] data;

	public DataGrid(int width, int height) {
		this.width = width;
		this.height = height;
		data = new Coordinate[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Coordinate getPoint(int x, int y) {
		if (x < 0 || y < 0 || x >= data.length || y >= data[x].length) {
			return null;
		}
		Coordinate coord = null;
		if (data.length > x && data[x].length > y)
			coord = data[x][y];
		if (coord == null) {
			coord = new Coordinate(x, y);
			data[x][y] = coord;
		}
		return coord;
	}

	public int size() {
		return data.length;
	}

	public Coordinate[][] getRaw() {
		return data;
	}

	public void reset() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[j].length; j++) {
				data[i][j].setValue(Coordinate.EMPTY);
			}
		}

	}

}
