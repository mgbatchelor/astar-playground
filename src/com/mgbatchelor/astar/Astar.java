package com.mgbatchelor.astar;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar {

	private Coordinate start;
	private Coordinate end;
	private DataGrid grid;

	private List<Coordinate> visited;
	private Queue<Coordinate> potential;

	public Astar(DataGrid grid, int startX, int startY, int endX, int endY) {
		this.grid = grid;

		this.grid.getPoint(startX, startY).setAsStart();
		this.grid.getPoint(endX, endY).setAsEnd();

		this.start = new Coordinate(startX, startY);
		this.end = new Coordinate(endX, endY);
		visited = new ArrayList<>();
		potential = new PriorityQueue<>(new CoordinateComparator(end));
		potential.add(this.start);
	}

	public Coordinate findBestPath() {
		Coordinate bestPath = null;
		while (potential.size() > 0 && (bestPath = potential.remove()) != null) {
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//			}

			if (visited.contains(bestPath))
				continue;

			visited.add(bestPath);
			if (bestPath.equals(end))
				return bestPath;

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Coordinate newCoord = this.grid.getPoint(bestPath.getX() + i, bestPath.getY() + j);
					if (newCoord != null) {
						if(visited.contains(newCoord)) {
						} else if (!newCoord.canVisit()) {
							visited.add(newCoord);
						} else {
							if (newCoord.canTraverse())
								newCoord.setAsOption();
							visited.remove(newCoord);
							potential.add(newCoord);
							newCoord.setParent(bestPath);
						}
					}
				}
			}
		}
		return start;
	}

}
