package hu.ziyan.minesweeper.model;


public class Minefield {
	private int rows, columns, mines;
	private int remainingFields;
	private int flagsPlaced;
	private Field field[][];

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.columns;
	}
	
	public int getMines() {
		return this.mines;
	}
	
	public int getRemainingFields() {
		return this.remainingFields;
	}
	
	public boolean isHiddenAndEmpty(int row, int column) {
		if(row >= 0 && row < rows && column >= 0 && column < columns) {
			return field[row][column].isHiddenAndEmpty();
		} else {
			return false;
		}
	}
	
	public boolean isFlagged(int row, int column) {
		return field[row][column].isFlagged();
	}
	
	
	public void placeFlag(int row, int column) {
		if(!isFlagged(row, column)) {
			field[row][column].placeFlag();
			++flagsPlaced;
		}
	}
	
	public void removeFlag(int row, int column) {
		field[row][column].removeFlag();
		--flagsPlaced;
	}
	
	public int getFlagsNumber() {
		return this.flagsPlaced;
	}
	
	public void reveal(int row, int column) {
		if(field[row][column].getIsHidden()) {
			this.remainingFields--;
			field[row][column].setHidden(false);
		}
		if(isFlagged(row, column)) {
			removeFlag(row, column);
		}
	}
	
	public void setField(Field field[][]) {
		this.field = field;
		this.remainingFields = (this.columns * this.rows) - this.mines;
		this.flagsPlaced = 0;
	}

	public Minefield(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}

	public boolean isMine(int row, int column) {
		return field[row][column].isMine();
	}

	public int getNearbyMinesNumber(int row, int column) {
		return field[row][column].getNearbyMinesNumber();
	}
}
