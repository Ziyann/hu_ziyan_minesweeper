package hu.ziyan.minesweeper.model;

interface Minefield {
	int getRows();

	int getColumns();

	int getMines();

	int getRemainingFields();

	boolean isHiddenAndEmpty(int row, int column);

	boolean isFlagged(int row, int column);

	void placeFlag(int row, int column);

	void removeFlag(int row, int column);

	int getFlagsNumber();

	void reveal(int row, int column);

	boolean isMine(int row, int column);

	int getNearbyMinesNumber(int row, int column);
}
