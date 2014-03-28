package hu.ziyan.minesweeper.model;

import hu.ziyan.minesweeper.view.JButtonField;

public class Minefield {
	public static final int DIFFICULTY_BEGINNER = 0;
	public static final int DIFFICULTY_INTERMEDIATE = 1;
	public static final int DIFFICULTY_ADVANCED = 2;
	public static final int DIFFICULTY_CUSTOM = 3;

	private int rows, columns, mines, fieldDifficulty;
	private int remainingFields;
	private JButtonField field[][];

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.columns;
	}
	
	public int getMines() {
		return this.mines;
	}
	
	public int getDifficulty() {
		return this.fieldDifficulty;
	}
	
	public int getRemainingFields() {
		return this.remainingFields;
	}
	
	public void decreaseRemainingFields() {
		--this.remainingFields;
	}
	
	public JButtonField[][] getButtonField() {
		return this.field;
	}
	
	public JButtonField getButtonField(int row, int column) {
		return this.field[row][column];
	}
	
	public void setField(JButtonField field[][]) {
		this.field = field;
	}
	
	public Minefield(int fieldDifficulty) {
		if (fieldDifficulty == DIFFICULTY_BEGINNER) {
			this.fieldDifficulty = DIFFICULTY_BEGINNER;
			this.rows = 9;
			this.columns = 9;
			this.mines = 9;
		} else if (fieldDifficulty == DIFFICULTY_INTERMEDIATE) {
			this.fieldDifficulty = DIFFICULTY_INTERMEDIATE;
			this.rows = 16;
			this.columns = 16;
			this.mines = 40;
		} else if (fieldDifficulty == DIFFICULTY_ADVANCED) {
			this.fieldDifficulty = DIFFICULTY_ADVANCED;
			this.rows = 16;
			this.columns = 30;
			this.mines = 99;
		}
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}

	public Minefield(int rows, int columns, int mines) {
		this.fieldDifficulty = DIFFICULTY_CUSTOM;
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.remainingFields = (this.columns * this.rows) - this.mines;
	}
}
