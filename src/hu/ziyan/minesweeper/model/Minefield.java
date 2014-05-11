package hu.ziyan.minesweeper.model;

public interface Minefield {
	int getRows();

	int getColumns();

	int getMines();

	int getRemainingFields();

	boolean isHiddenAndEmpty(final int row, final int column);
	
	boolean isHidden(final int row, final int column);

	boolean isFlagged(final int row, final int column);

	void placeFlag(final int row, final int column);

	void removeFlag(final int row, final int column);

	int getFlagsNumber();

	void reveal(final int row, final int column);
	
	void revealNearbyEmptyFields(final int row, final int column);

	boolean isMine(final int row, final int column);

	int getNearbyMinesNumber(final int row, final int column);
}
