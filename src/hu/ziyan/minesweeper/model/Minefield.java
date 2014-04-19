package hu.ziyan.minesweeper.model;


public class Minefield {
	public static final int DIFFICULTY_BEGINNER = 0;
	public static final int DIFFICULTY_INTERMEDIATE = 1;
	public static final int DIFFICULTY_ADVANCED = 2;
	public static final int DIFFICULTY_CUSTOM = 3;

	private int rows, columns, mines;
	private int remainingFields;
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
	
	public Field getField(int row, int column) {
		return field[row][column];
	}
	
	public boolean isFlagged(int row, int column) {
		return field[row][column].isFlagged();
	}
	
	
	public void placeFlag(int row, int column) {
		field[row][column].placeFlag();
	}
	
	public void removeFlag(int row, int column) {
		field[row][column].removeFlag();
	}
	
	public void reveal(int row, int column) {
		if(field[row][column].getIsHidden()) {
			this.remainingFields--;
			field[row][column].setHidden(false);
		}
	}
	
	public void setField(Field field[][]) {
		this.field = field;
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}
	
	public Minefield(int fieldDifficulty) {
		if (fieldDifficulty == DIFFICULTY_BEGINNER) {
			this.rows = 9;
			this.columns = 9;
			this.mines = 10;
		} else if (fieldDifficulty == DIFFICULTY_INTERMEDIATE) {
			this.rows = 16;
			this.columns = 16;
			this.mines = 40;
		} else if (fieldDifficulty == DIFFICULTY_ADVANCED) {
			this.rows = 16;
			this.columns = 30;
			this.mines = 99;
		}
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}

	public Minefield(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}
}
