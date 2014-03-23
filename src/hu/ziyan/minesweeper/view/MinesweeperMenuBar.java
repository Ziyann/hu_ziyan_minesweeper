package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.view.dialogs.DifficultyDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MinesweeperMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 406089122200172021L;
	private static final String seperator = "seperator";
	private MinesweeperGUI gui;

	public MinesweeperMenuBar(MinesweeperGUI gui) {
		super();
		this.gui = gui;

		// H�rom men�pontot gy�rtunk �ltal�nosan, a createMenuPoint met�dussal
		createMenuPoint(Labels.game, Labels.new_game, seperator,
				Labels.difficulty, seperator, Labels.exit_label);
		createMenuPoint(Labels.help, Labels.about);
	}

	private void createMenuPoint(String name, String... subnames) {
		// L�trehozunk egy menupontot az els� param�ter alapj�n
		JMenu menu = new JMenu(name);

		// A menupontot hozz�adjuk a BookShopMenuBar-hoz
		this.add(menu);

		// Az egyes menu itemeket a marad�k param�ter �rt�keivel hozzuk l�tre
		for (String subname : subnames) {
			if (subname == seperator) {
				menu.addSeparator();
			} else {
				JMenuItem menuItem = new JMenuItem(subname);
				menu.add(menuItem);
				menuItem.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.equals(Labels.difficulty)) {
            // Ha az �j �gyf�l felv�tel�t v�lasztott�k, akkor egy
            // AddCustomerDialog-ot ind�tunk
            new DifficultyDialog(gui, true);
		}

	}

}
