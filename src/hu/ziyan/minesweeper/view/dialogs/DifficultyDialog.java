package hu.ziyan.minesweeper.view.dialogs;

import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.MinesweeperGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class DifficultyDialog extends JDialog implements ActionListener {
	
	private MinesweeperGUI gui;
	
	public DifficultyDialog(MinesweeperGUI gui, boolean modal) {
		super(gui.getWindow(), modal);
		this.gui = gui;
		this.setTitle(Labels.difficulty);
		this.setLocationRelativeTo(gui.getWindow());
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
