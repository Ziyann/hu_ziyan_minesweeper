package hu.ziyan.minesweeper.model.services;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Field;
import hu.ziyan.minesweeper.model.Minefield;

import java.util.ArrayList;
import java.util.Random;

public class MinefieldService {
	private Minefield minefield;
	private MinesweeperController controller;

	public MinefieldService(MinesweeperController controller, Minefield minefield) {
		this.controller = controller;
		this.minefield = minefield;
	}

	public void makeField() {
		/*
		 * Make 2D array
		 */
		Field[][] field = new Field[minefield.getRows()][minefield.getColumns()];
		for (int row = 0; row < minefield.getRows(); ++row) {
			for (int column = 0; column < minefield.getColumns(); ++column) {
				field[row][column] = new Field();
			}
		}

		/*
		 * Place mines
		 */
		for (int i = 0; i < minefield.getMines(); ++i) {
			Random veletlen = new Random();
			while (true) {
				int randomRow = veletlen.nextInt(minefield.getRows());
				int randomColumn = veletlen.nextInt(minefield.getColumns());
				if (field[randomRow][randomColumn].isMine() == false) {
					field[randomRow][randomColumn].makeMine();
					break;
				}
			}
		}

		/*
		 * Place numbers indicating nearby mines
		 */
		for (int row = 0; row < minefield.getRows(); row++) {
			for (int column = 0; column < minefield.getColumns(); column++) {
				if (field[row][column].isMine()) { // skip mines
					continue;
				}
				int nearbyMines = 0;
				if (row < minefield.getRows() - 1 && column < minefield.getColumns() - 1
						&& field[row + 1][column + 1].isMine()) {
					++nearbyMines;
				}
				if (row < minefield.getRows() - 1 && field[row + 1][column].isMine()) {
					++nearbyMines;
				}
				if (column > 0 && row < minefield.getRows() - 1 && field[row + 1][column - 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && column > 0 && field[row - 1][column - 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && column < minefield.getColumns() - 1 && field[row - 1][column + 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && field[row - 1][column].isMine()) {
					++nearbyMines;
				}
				if (column < minefield.getColumns() - 1 && field[row][column + 1].isMine()) {
					++nearbyMines;
				}
				if (column > 0 && field[row][column - 1].isMine()) {
					++nearbyMines;
				}
				field[row][column].setNearbyMines(nearbyMines);
			}
		}

		minefield.setField(field);
	}

	public void revealNearbyEmptyFields(int row, int column) {
		class Position {
			public int row, column;

			Position(int row, int column) {
				this.row = row;
				this.column = column;
			}
		}
		ArrayList<Position> positions = new ArrayList<Position>();

		positions.add(new Position(row, column));

		for (int i = 0; i < positions.size(); i++) {
			Position pos = positions.get(i);

			/*
			 * add nearby empty fields to queue
			 */
			if (pos.row > 0 && minefield.getField(pos.row - 1, pos.column).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column));
				controller.revealField(pos.row - 1, pos.column);
			} else if (pos.row > 0) {
				controller.revealField(pos.row - 1, pos.column);
			}
			if (pos.row > 0 && pos.column > 0 && minefield.getField(pos.row - 1, pos.column - 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column - 1));
				controller.revealField(pos.row - 1, pos.column - 1);
			} else if (pos.row > 0 && pos.column > 0) {
				controller.revealField(pos.row - 1, pos.column - 1);
			}
			if (pos.column > 0 && minefield.getField(pos.row, pos.column - 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row, pos.column - 1));
				controller.revealField(pos.row, pos.column - 1);
			} else if (pos.column > 0) {
				controller.revealField(pos.row, pos.column - 1);
			}
			if (pos.row < minefield.getRows() - 1 && pos.column > 0
					&& minefield.getField(pos.row + 1, pos.column - 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column - 1));
				controller.revealField(pos.row + 1, pos.column - 1);
			} else if (pos.row < minefield.getRows() - 1 && pos.column > 0) {
				controller.revealField(pos.row + 1, pos.column - 1);
			}
			if (pos.row < minefield.getRows() - 1 && minefield.getField(pos.row + 1, pos.column).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column));
				controller.revealField(pos.row + 1, pos.column);
			} else if (pos.row < minefield.getRows() - 1) {
				controller.revealField(pos.row + 1, pos.column);
			}
			if (pos.row < minefield.getRows() - 1 && pos.column < minefield.getColumns() - 1
					&& minefield.getField(pos.row + 1, pos.column + 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column + 1));
				controller.revealField(pos.row + 1, pos.column + 1);
			} else if (pos.row < minefield.getRows() - 1 && pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row + 1, pos.column + 1);
			}
			if (pos.column < minefield.getColumns() - 1 && minefield.getField(pos.row, pos.column + 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row, pos.column + 1));
				controller.revealField(pos.row, pos.column + 1);
			} else if (pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row, pos.column + 1);
			}
			if (pos.row > 0 && pos.column < minefield.getColumns() - 1
					&& minefield.getField(pos.row - 1, pos.column + 1).isHiddenAndEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column + 1));
				controller.revealField(pos.row - 1, pos.column + 1);
			} else if (pos.row > 0 && pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row - 1, pos.column + 1);
			}
		}
	}
}
