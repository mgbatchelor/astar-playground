package com.mgbatchelor.astar;

import java.awt.Container;

public class Coordinate {
	private int x;
	private int y;

	public static final String START = "S";
	public static final String END = "E";
	public static final String PATH = "O";
	public static final String OOB = "X";
	public static final String OPTION = "?";
	public static final String EMPTY = " ";

	private String value = EMPTY;
	private Coordinate parent;
	private Container display;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Coordinate getParent() {
		return parent;
	}

	public void setParent(Coordinate parent) {
		setAsOption();
		this.parent = parent;
	}

	public Container getDisplay() {
		return display;
	}

	public void setDisplay(Container display) {
		this.display = display;
	}

	public int fCost(Coordinate target) {
//		System.out.println(gCost() + " " + hCost(target));
		return gCost() + hCost(target);
	}

	public int gCost() {
		int gCost = 0;
		if (parent != null) {
			gCost = parent.gCost();
			if (Math.abs(parent.x - this.x) + Math.abs(parent.y - this.y) >= 1) {
				gCost += 10;
			} else {
				gCost += 14;
			}
		}
		return gCost;
	}

	public int hCost(Coordinate coord) {
		if (coord == null)
			return 0;
		return (Math.abs(coord.x - this.x) + Math.abs(coord.y - this.y)) * 10;
	}

	public boolean canTraverse() {
		return this.value != null && !this.value.equals(START);
	}

	public boolean canVisit() {
		return this.value != null && !this.value.equals(OOB);
	}

	public void setValue(String value) {
		if (!value.equals(EMPTY) && (this.value.equals(START) || this.value.equals(END)))
			return;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setAsStart() {
		setValue(START);
	}

	public void setAsEnd() {
		setValue(END);
	}

	public void setAsOOB() {
		setValue(OOB);
	}

	public void setAsPath() {
		setValue(PATH);
	}

	public void setAsOption() {
		setValue(OPTION);
	}

	public String toString() {
		return "X: " + this.x + " Y: " + this.y + " V: " + this.value;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 31 * x * y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().getCanonicalName().equals(this.getClass().getCanonicalName()))
			return false;
		Coordinate coord = (Coordinate) obj;
		return this.x == coord.x && this.y == coord.y;
	}
}