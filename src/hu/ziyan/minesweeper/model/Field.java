package hu.ziyan.minesweeper.model;

public class Field {
	private boolean isMine = false;
	private boolean isFlagged = false;
	private boolean isHidden = true;
	private int nearbyMines;

	public Field() {
	}

	public boolean isMine() {
		return this.isMine;
	}

	public void makeMine() {
		this.isMine = true;
	}

	public void setNearbyMines(int nearbyMines) {
		this.nearbyMines = nearbyMines;
	}

	public int getNearbyMinesNumber() {
		return this.nearbyMines;
	}

	public boolean isFlagged() {
		return this.isFlagged;
	}

	public void placeFlag() {
		this.isFlagged = true;
	}

	public void removeFlag() {
		this.isFlagged = false;
	}

	public void setHidden(boolean hidden) {
		this.isHidden = hidden;
	}

	public boolean getIsHidden() {
		return this.isHidden;
	}

	public boolean isHiddenAndEmpty() {
		if (this.nearbyMines == 0 && !this.isMine && this.isHidden) {
			return true;
		} else {
			return false;
		}
	}
}
