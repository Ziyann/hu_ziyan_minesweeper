package hu.ziyan.minesweeper.model;

import hu.ziyan.minesweeper.controller.MinesweeperController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinefieldImpl implements Minefield {
	private final int rows, columns, mines;
	private int remainingFields;
	private int flagsPlaced = 0;
	private Field[][] field;
	private MinesweeperController controller;

	public MinefieldImpl(final int rows, final int columns, final int mines, final MinesweeperController controller) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.remainingFields = this.columns * this.rows - this.mines;
		this.controller = controller;
		generateRandomField();
	}

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

	public boolean isMine(final int row, final int column) {
		return field[row][column].isMine();
	}

	public int getNearbyMinesNumber(final int row, final int column) {
		return field[row][column].getNearbyMinesNumber();
	}

	public void generateRandomField() {
		/*
		 * Make 2D array
		 */
		field = new Field[getRows()][getColumns()];
		for (int row = 0; row < getRows(); ++row) {
			for (int column = 0; column < getColumns(); ++column) {
				field[row][column] = new Field();
			}
		}

		/*
		 * Place mines
		 */
		final Random rand = new Random();
		for (int i = 0; i < getMines(); i++) {
			while (true) {
				final int randomRow = rand.nextInt(getRows());
				final int randomColumn = rand.nextInt(getColumns());
				if (!field[randomRow][randomColumn].isMine()) {
					field[randomRow][randomColumn].makeMine();
					break;
				}
			}
		}

		/*
		 * Place numbers indicating nearby mines
		 */
		for (int row = 0; row < getRows(); row++) {
			for (int column = 0; column < getColumns(); column++) {
				if (!field[row][column].isMine()) { // skip mines
					int nearbyMines = 0;
					if (row < getRows() - 1 && column < getColumns() - 1 && field[row + 1][column + 1].isMine()) {
						++nearbyMines;
					}
					if (row < getRows() - 1 && field[row + 1][column].isMine()) {
						++nearbyMines;
					}
					if (column > 0 && row < getRows() - 1 && field[row + 1][column - 1].isMine()) {
						++nearbyMines;
					}
					if (row > 0 && column > 0 && field[row - 1][column - 1].isMine()) {
						++nearbyMines;
					}
					if (row > 0 && column < getColumns() - 1 && field[row - 1][column + 1].isMine()) {
						++nearbyMines;
					}
					if (row > 0 && field[row - 1][column].isMine()) {
						++nearbyMines;
					}
					if (column < getColumns() - 1 && field[row][column + 1].isMine()) {
						++nearbyMines;
					}
					if (column > 0 && field[row][column - 1].isMine()) {
						++nearbyMines;
					}
					field[row][column].setNearbyMines(nearbyMines);
				}
			}
		}
	}

	private static class Position {
		public int row, column;

		public Position(final int row, final int column) {
			this.row = row;
			this.column = column;
		}
	}

	public void revealNearbyEmptyFields(final int row, final int column) {
		final List<Position> positions = new ArrayList<Position>();

		positions.add(new Position(row, column));

		for (int i = 0; i < positions.size(); i++) {
			final Position pos = positions.get(i);

			/*
			 * add nearby hidden empty fields to queue
			 */
			if (isHiddenAndEmpty(pos.row - 1, pos.column + 1)) { // top-right
				positions.add(new Position(pos.row - 1, pos.column + 1));
			}
			if (isHiddenAndEmpty(pos.row - 1, pos.column)) { // top
				positions.add(new Position(pos.row - 1, pos.column));
			}
			if (isHiddenAndEmpty(pos.row - 1, pos.column - 1)) { // top-left
				positions.add(new Position(pos.row - 1, pos.column - 1));
			}
			if (isHiddenAndEmpty(pos.row, pos.column - 1)) { // left
				positions.add(new Position(pos.row, pos.column - 1));
			}
			if (isHiddenAndEmpty(pos.row, pos.column + 1)) { // right
				positions.add(new Position(pos.row, pos.column + 1));
			}
			if (isHiddenAndEmpty(pos.row + 1, pos.column + 1)) { // bottom-right
				positions.add(new Position(pos.row + 1, pos.column + 1));
			}
			if (isHiddenAndEmpty(pos.row + 1, pos.column)) { // bottom
				positions.add(new Position(pos.row + 1, pos.column));
			}
			if (isHiddenAndEmpty(pos.row + 1, pos.column - 1)) { // bottom-left
				positions.add(new Position(pos.row + 1, pos.column - 1));
			}

			/*
			 * As the current position is always empty, we can reveal every
			 * field around it
			 */
			controller.revealPosition(pos.row - 1, pos.column + 1); // top-right
			controller.revealPosition(pos.row - 1, pos.column); // top
			controller.revealPosition(pos.row - 1, pos.column - 1); // top-left
			controller.revealPosition(pos.row, pos.column - 1); // left
			controller.revealPosition(pos.row, pos.column + 1); // right
			controller.revealPosition(pos.row + 1, pos.column + 1); // bottom-right
			controller.revealPosition(pos.row + 1, pos.column); // bottom
			controller.revealPosition(pos.row + 1, pos.column - 1); // bottom-left
		}
	}

	public boolean isHidden(int row, int column) {
		return field[row][column].isHidden();
	}
}
