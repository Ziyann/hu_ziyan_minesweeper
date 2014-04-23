package hu.ziyan.minesweeper.model;

public class MinefieldImpl implements Minefield {
	private final int rows, columns, mines;
	private int remainingFields;
	private int flagsPlaced;
	private Field[][] field;

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

	public boolean isHiddenAndEmpty(final int row, final int column) {
		boolean isHiddenAndEmpty = false;
		if (row >= 0 && row < rows && column >= 0 && column < columns) {
			isHiddenAndEmpty = field[row][column].isHiddenAndEmpty();
		}
		return isHiddenAndEmpty;
	}

	public boolean isFlagged(final int row, final int column) {
		return field[row][column].isFlagged();
	}

	public void placeFlag(final int row, final int column) {
		if (!isFlagged(row, column)) {
			field[row][column].placeFlag();
			++flagsPlaced;
		}
	}

	public void removeFlag(final int row, final int column) {
		field[row][column].removeFlag();
		--flagsPlaced;
	}

	public int getFlagsNumber() {
		return this.flagsPlaced;
	}

	public void reveal(final int row, final int column) {
		if (field[row][column].isHidden()) {
			this.remainingFields--;
			field[row][column].setHidden(false);
		}
		if (isFlagged(row, column)) {
			removeFlag(row, column);
		}
	}

	public void setField(final Field[][] field) {
		this.field = field;
		this.remainingFields = this.columns * this.rows - this.mines;
		this.flagsPlaced = 0;
	}

	public MinefieldImpl(final int rows, final int columns, final int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.remainingFields = this.columns * this.rows - this.mines;
	}

	public boolean isMine(final int row, final int column) {
		return field[row][column].isMine();
	}

	public int getNearbyMinesNumber(final int row, final int column) {
		return field[row][column].getNearbyMinesNumber();
	}
}
