package hu.ziyan.minesweeper.model.services;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Field;
import hu.ziyan.minesweeper.model.MinefieldImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinefieldService {
	private MinefieldImpl minefield;
	private MinesweeperController controller;

	public MinefieldService(MinesweeperController controller, MinefieldImpl minefield) {
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
			Random rand = new Random();
			while (true) {
				int randomRow = rand.nextInt(minefield.getRows());
				int randomColumn = rand.nextInt(minefield.getColumns());
				if (!field[randomRow][randomColumn].isMine()) {
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

			public Position(int row, int column) {
				this.row = row;
				this.column = column;
			}
		}
		List<Position> positions = new ArrayList<Position>();

		positions.add(new Position(row, column));

		for (int i = 0; i < positions.size(); i++) {
			Position pos = positions.get(i);

			/*
			 * add nearby hidden empty fields to queue
			 */
			if (minefield.isHiddenAndEmpty(pos.row - 1, pos.column + 1)) { // top-right
				positions.add(new Position(pos.row - 1, pos.column + 1));
			}
			if (minefield.isHiddenAndEmpty(pos.row - 1, pos.column)) { // top
				positions.add(new Position(pos.row - 1, pos.column));
			}
			if (minefield.isHiddenAndEmpty(pos.row - 1, pos.column - 1)) { // top-left
				positions.add(new Position(pos.row - 1, pos.column - 1));
			}
			if (minefield.isHiddenAndEmpty(pos.row, pos.column - 1)) { // left
				positions.add(new Position(pos.row, pos.column - 1));
			}
			if (minefield.isHiddenAndEmpty(pos.row, pos.column + 1)) { // right
				positions.add(new Position(pos.row, pos.column + 1));
			}
			if (minefield.isHiddenAndEmpty(pos.row + 1, pos.column + 1)) { // bottom-right
				positions.add(new Position(pos.row + 1, pos.column + 1));
			}
			if (minefield.isHiddenAndEmpty(pos.row + 1, pos.column)) { // bottom
				positions.add(new Position(pos.row + 1, pos.column));
			}
			if (minefield.isHiddenAndEmpty(pos.row + 1, pos.column - 1)) { // bottom-left
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
}
