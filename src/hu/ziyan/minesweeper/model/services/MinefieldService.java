package hu.ziyan.minesweeper.model.services;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Minefield;
import hu.ziyan.minesweeper.view.JButtonField;

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
		JButtonField[][] field = new JButtonField[minefield.getRows()][minefield.getColumns()];
		for (int row = 0; row < minefield.getRows(); ++row) {
			for (int column = 0; column < minefield.getColumns(); ++column) {
				field[row][column] = new JButtonField(this.controller, row, column);
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

	public void revealField(int row, int column) {
		if (minefield.getButtonField(row, column).isEnabled() && minefield.getButtonField(row, column).isFocusable()) {
			minefield.reveal(row, column);
		}
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
			if (pos.row > 0 && minefield.getButtonField(pos.row - 1, pos.column).isEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column));
				controller.revealField(pos.row - 1, pos.column);
			} else if (pos.row > 0) {
				controller.revealField(pos.row - 1, pos.column);
			}
			if (pos.row > 0 && pos.column > 0 && minefield.getButtonField(pos.row - 1, pos.column - 1).isEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column - 1));
				controller.revealField(pos.row - 1, pos.column - 1);
			} else if (pos.row > 0 && pos.column > 0) {
				controller.revealField(pos.row - 1, pos.column - 1);
			}
			if (pos.column > 0 && minefield.getButtonField(pos.row, pos.column - 1).isEmpty()) {
				positions.add(new Position(pos.row, pos.column - 1));
				controller.revealField(pos.row, pos.column - 1);
			} else if (pos.column > 0) {
				controller.revealField(pos.row, pos.column - 1);
			}
			if (pos.row < minefield.getRows() - 1 && pos.column > 0
					&& minefield.getButtonField(pos.row + 1, pos.column - 1).isEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column - 1));
				controller.revealField(pos.row + 1, pos.column - 1);
			} else if (pos.row < minefield.getRows() - 1 && pos.column > 0) {
				controller.revealField(pos.row + 1, pos.column - 1);
			}
			if (pos.row < minefield.getRows() - 1 && minefield.getButtonField(pos.row + 1, pos.column).isEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column));
				controller.revealField(pos.row + 1, pos.column);
			} else if (pos.row < minefield.getRows() - 1) {
				controller.revealField(pos.row + 1, pos.column);
			}
			if (pos.row < minefield.getRows() - 1 && pos.column < minefield.getColumns() - 1
					&& minefield.getButtonField(pos.row + 1, pos.column + 1).isEmpty()) {
				positions.add(new Position(pos.row + 1, pos.column + 1));
				controller.revealField(pos.row + 1, pos.column + 1);
			} else if (pos.row < minefield.getRows() - 1 && pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row + 1, pos.column + 1);
			}
			if (pos.column < minefield.getColumns() - 1 && minefield.getButtonField(pos.row, pos.column + 1).isEmpty()) {
				positions.add(new Position(pos.row, pos.column + 1));
				controller.revealField(pos.row, pos.column + 1);
			} else if (pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row, pos.column + 1);
			}
			if (pos.row > 0 && pos.column < minefield.getColumns() - 1
					&& minefield.getButtonField(pos.row - 1, pos.column + 1).isEmpty()) {
				positions.add(new Position(pos.row - 1, pos.column + 1));
				controller.revealField(pos.row - 1, pos.column + 1);
			} else if (pos.row > 0 && pos.column < minefield.getColumns() - 1) {
				controller.revealField(pos.row - 1, pos.column + 1);
			}
		}
	}

	public void revealMinefield() {
		for (int i = 0; i < minefield.getRows(); i++) {
			for (int j = 0; j < minefield.getColumns(); j++) {
				JButtonField field = minefield.getButtonField(i, j);
				if (field.isFlagged() && field.isMine()) {
					field.showFlaggedMine();
				} else if (field.isMine()) {
					field.setText("<html><font color=red>X</font></html>");
				}
			}
		}
	}
}
