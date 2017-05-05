package com.mgbatchelor.astar;

import java.util.Comparator;

public class CoordinateComparator implements Comparator<Coordinate> {

	private Coordinate target;

	public CoordinateComparator(Coordinate target) {
		this.target = target;
	}

	@Override
	public int compare(Coordinate o1, Coordinate o2) {
		int o1Cost = o1.fCost(target);
		int o2Cost = o2.fCost(target);
		if (o1Cost == o2Cost)
			return 0;
		return o1Cost < o2Cost ? -1 : 1;
	}

}
