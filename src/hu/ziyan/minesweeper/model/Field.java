package hu.ziyan.minesweeper.model;

public class Field {
	private boolean isMine = false;
	private boolean isFlagged = false;
	private boolean isHidden = true;
	private int nearbyMinesNumber;

	public boolean isMine() {
		return this.isMine;
	}

	public void makeMine() {
		this.isMine = true;
	}

	public void setNearbyMines(final int nearbyMines) {
		this.nearbyMinesNumber = nearbyMines;
	}

	public int getNearbyMinesNumber() {
		return this.nearbyMinesNumber;
	}

	public boolean isFlagged() {
		return this.isFlagged;
	}

	void placeFlag() {
		this.isFlagged = true;
	}

	void removeFlag() {
		this.isFlagged = false;
	}

	public void setHidden(final boolean hidden) {
		this.isHidden = hidden;
	}

	public boolean isHidden() {
		return this.isHidden;
	}

	public boolean isHiddenAndEmpty() {
		return this.nearbyMinesNumber == 0 && !this.isMine && this.isHidden;
	}
}
